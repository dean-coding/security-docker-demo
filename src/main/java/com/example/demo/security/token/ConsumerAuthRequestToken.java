package com.example.demo.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Getter;
import lombok.ToString;

/**
 * @author fuhw
 * @date 2017年11月29日
 */
@Getter
@ToString
public class ConsumerAuthRequestToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -6261734823413986918L;
	private final String username;
	private final String password;

	/**
	 * @param authorities
	 */
	public ConsumerAuthRequestToken(String username, String password) {
		super(null);
		this.username = username;
		this.password = password;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return this.password;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.Authentication#getPrincipal()
	 */
	@Override
	public Object getPrincipal() {
		return this.username;
	}

}
