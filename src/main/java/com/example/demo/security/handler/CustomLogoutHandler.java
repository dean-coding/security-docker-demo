package com.example.demo.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * @author fuhw
 * @date 2017年11月30日 
 */
public class CustomLogoutHandler extends SecurityContextLogoutHandler {

	/** 
	 *(non-Javadoc)
	 * @see org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		super.logout(request, response, authentication);
		try {
			response.sendRedirect("/login/consumer");
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
