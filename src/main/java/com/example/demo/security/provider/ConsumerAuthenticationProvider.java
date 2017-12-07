package com.example.demo.security.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import com.example.demo.security.UserTypeEnum;
import com.example.demo.security.token.ConsumerAuthRequestToken;
import com.example.demo.security.token.ConsumerAuthToken;

import lombok.extern.slf4j.Slf4j;

/**
 * 普通用户授权provider
 * 
 * @author fuhw
 * @date 2017年11月29日
 */
@Slf4j
public class ConsumerAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private final UserDetailsService userDetailService;

	public ConsumerAuthenticationProvider(UserDetailsService userDetailService) {
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
		String password = (String) authentication.getCredentials();
		UserDetails userDetail = userDetailService.loadUserByUsername(username);
		String pwdDb = userDetail.getPassword();
		if (!pwdDb.equals(password)) {
			log.error("the password is mismatched");
			throw new BadCredentialsException("the password is mismatched");
		}
		log.info("the authenticate is successd: userDetail = {}",userDetail);
//		authentication.setAuthenticated(true);
		return new ConsumerAuthToken(userDetail.getUsername(), UserTypeEnum.CONSUMER, userDetail.getAuthorities());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return ConsumerAuthRequestToken.class.isAssignableFrom(authentication);
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
