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
public class ManagerAuthRequestToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -6261734823413986918L;
	private final String usertoken;

	/**
	 * @param authorities
	 */
	public ManagerAuthRequestToken(String usertoken) {
		super(null);
		this.usertoken = usertoken;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	@Override
	public Object getCredentials() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.Authentication#getPrincipal()
	 */
	@Override
	public Object getPrincipal() {
		return usertoken;
	}

}
