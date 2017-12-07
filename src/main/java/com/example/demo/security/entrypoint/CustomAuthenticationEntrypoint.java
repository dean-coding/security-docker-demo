package com.example.demo.security.entrypoint;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author fuhw
 * @date 2017年11月30日
 */
public class CustomAuthenticationEntrypoint extends LoginUrlAuthenticationEntryPoint {

	public CustomAuthenticationEntrypoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	/**
	 * 配置请求路径返回不同的登录页路径
	 */
	private Map<String, String> authEntrypointMap = Collections.emptyMap();

	/**
	 * AntPathRequestMatcher可以实现路径的匹配工作
	 */
	private PathMatcher pathMatcher = new AntPathMatcher();


	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String reqURI = request.getRequestURI().replaceAll(request.getContextPath(), "");

		for (String url : this.authEntrypointMap.keySet()) {
			if (this.pathMatcher.match(url, reqURI)) {
				return this.authEntrypointMap.get(url);
			}
		}
		return super.determineUrlToUseForThisRequest(request, response, exception);
	}

	public Map<String, String> getAuthEntrypointMap() {
		return authEntrypointMap;
	}

	public void setAuthEntrypointMap(Map<String, String> authEntrypointMap) {
		this.authEntrypointMap = authEntrypointMap;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}
	
	
	
}
