package com.example.demo.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.security.token.ManagerAuthRequestToken;

public class ManagerAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "usertoken";
	public static final String DEFAULT_PATTERN = "/login/manager";

	private String usertokenParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private boolean postOnly = true;

	public ManagerAuthenticationFilter() {
		super(new AntPathRequestMatcher(DEFAULT_PATTERN, "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String usertoken = request.getParameter(usertokenParameter);

		if (usertoken == null) {
			throw new AuthenticationServiceException("code usertoken is required");
		}

		usertoken = usertoken.trim();

		ManagerAuthRequestToken authRequest = new ManagerAuthRequestToken(usertoken);

		// Allow subclasses to set the "details" property
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
