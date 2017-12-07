package com.example.demo.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.security.entrypoint.CustomAuthenticationEntrypoint;
import com.example.demo.security.filter.ConsumerAuthenticationFilter;
import com.example.demo.security.filter.ManagerAuthenticationFilter;
import com.example.demo.security.handler.CustomLogoutHandler;
import com.example.demo.security.provider.ConsumerAuthenticationProvider;
import com.example.demo.security.provider.ManagerAuthenticationProvider;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailService userDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new ManagerAuthenticationProvider(userDetailService));
		auth.authenticationProvider(new ConsumerAuthenticationProvider(userDetailService));
		auth.userDetailsService(userDetailService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.httpBasic()
				.and().logout().addLogoutHandler(new CustomLogoutHandler()).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.and().authorizeRequests()
					.antMatchers("/consumer/**").hasRole("USER")//tip:hasRole 自动拼接 ROLE_
					.antMatchers("/manager/**","/mananger**").hasRole("SUPER_ADMIN")
					.anyRequest().authenticated()
				.and().addFilterBefore(managerAuthenticationFilter(super.authenticationManagerBean()), BasicAuthenticationFilter.class)
					  .addFilterBefore(consumerAuthenticationFilter(super.authenticationManagerBean()), BasicAuthenticationFilter.class)
					  .exceptionHandling().authenticationEntryPoint(customAuthenticationEntrypoint())
				.and().csrf().disable();
		// @formatter:on

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "*.html", "/consumerlogin", "/managerlogin");
	}
	
	/**
	 * CustomAuthenticationEntrypoint - 1
	 */
	private CustomAuthenticationEntrypoint customAuthenticationEntrypoint() {
		CustomAuthenticationEntrypoint customAuthenticationEntrypoint = new CustomAuthenticationEntrypoint("/consumerlogin");
		Map<String, String> authEntrypointMap = new LinkedHashMap<>();
		authEntrypointMap.put("/consumer/**", "/consumerlogin");
		authEntrypointMap.put("/manager/**", "/managerlogin");
//		authEntrypointMap.put("/**", "/");
		customAuthenticationEntrypoint.setAuthEntrypointMap(authEntrypointMap);
		return customAuthenticationEntrypoint;
	}
	
	/**
	 * CustomAuthenticationEntrypoint - 2
	 */
//	private AuthenticationEntryPoint authenticationEntryPoint() {
//		BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
//		basicAuthenticationEntryPoint.setRealmName("Realm");
//
//		LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<RequestMatcher, AuthenticationEntryPoint>();
//		entryPoints.put(new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"),
//				new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//
//		entryPoints.put(new AntPathRequestMatcher("/consumer/**"),
//				new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//
//		entryPoints.put(new AntPathRequestMatcher("/manager/**"), new Http403ForbiddenEntryPoint());
//
//		DelegatingAuthenticationEntryPoint defaultEntryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
//		defaultEntryPoint.setDefaultEntryPoint(basicAuthenticationEntryPoint);
//
//		return defaultEntryPoint;
//	}

	/**filter*/
	private ManagerAuthenticationFilter managerAuthenticationFilter(AuthenticationManager manager) {
		ManagerAuthenticationFilter filter = new ManagerAuthenticationFilter();
		filter.setAuthenticationManager(manager);
		//tip：此处可以添加成功失败的处理
		filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/manager-index"));
		return filter;
	}

	private ConsumerAuthenticationFilter consumerAuthenticationFilter(AuthenticationManager manager) {
		ConsumerAuthenticationFilter filter = new ConsumerAuthenticationFilter();
		filter.setAuthenticationManager(manager);
		filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
		return filter;
	}
	
}
