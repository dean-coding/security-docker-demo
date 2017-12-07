package com.example.demo.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.security.UserTypeEnum;

import lombok.Getter;
import lombok.ToString;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Getter
@ToString
public class ConsumerAuthToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 4448124448805577072L;

	private final String usertoken;
	
	private final UserTypeEnum usertype;
	
	public ConsumerAuthToken(String usertoken, UserTypeEnum usertype, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.usertoken = usertoken;
		this.usertype = usertype;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.usertoken;
	}

}
