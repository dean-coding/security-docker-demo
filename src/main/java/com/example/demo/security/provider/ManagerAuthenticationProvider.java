package com.example.demo.security.provider;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.example.demo.dao.RoleTypeEnum;
import com.example.demo.security.UserTypeEnum;
import com.example.demo.security.token.ManagerAuthRequestToken;
import com.example.demo.security.token.ManagerAuthToken;

import lombok.extern.slf4j.Slf4j;

/**
 * 管理人员授权provider
 * @author fuhw
 * @date 2017年11月29日
 */
@Slf4j
public class ManagerAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private final UserDetailsService userDetailService;

	public ManagerAuthenticationProvider(UserDetailsService userDetailService) {
		super();
		this.userDetailService = userDetailService;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Assert.isInstanceOf(AdminAuthToken.class,
		// authentication,"AdminAuthenticationProvider only Supports
		// AdminAuthToken");
		String username = (String) authentication.getPrincipal();
		UserDetails userDetail = userDetailService.loadUserByUsername(username);
		Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();
		if(CollectionUtils.isEmpty(authorities) || !authorities.contains(new SimpleGrantedAuthority(RoleTypeEnum.ROLE_SUPER_ADMIN.toString()))) {
			throw new BadCredentialsException("you haven't enough authorities for [ADMIN]");
		}
		log.info("the authenticate is successd: userDetail = {}",userDetail);
		return new ManagerAuthToken(userDetail.getUsername(), UserTypeEnum.MANAGER,
				userDetail.getAuthorities());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ManagerAuthRequestToken.class.isAssignableFrom(authentication);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(userDetailService, "the userDetailService of AdminAuthenticationProvider must be not null");
	}

}
