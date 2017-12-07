package com.example.demo.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.security.token.ConsumerAuthRequestToken;

public class ConsumerAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String DEFAULT_PATTERN = "/login/consumer";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;

	public ConsumerAuthenticationFilter() {
		super(new AntPathRequestMatcher(DEFAULT_PATTERN, "POST"));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = request.getParameter(usernameParameter);
		String password = request.getParameter(passwordParameter);

		if (username == null) {
			throw new AuthenticationServiceException("code username is required");
		}

		if (password == null) {
			throw new AuthenticationServiceException("code password is required");
		}

		username = username.trim();

		ConsumerAuthRequestToken authRequest = new ConsumerAuthRequestToken(username, password);

		// Allow subclasses to set the "details" property
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
