package com.vinay.auth.filter;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;
import net.oauth.signature.OAuthSignatureMethod;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {
	
	private final Logger LOGGER = Logger.getLogger(StatelessAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if(!(httpRequest.getRequestURI().contains("/h2-console") || httpRequest.getRequestURI().contains("test") || "/favicon.ico".equals(httpRequest.getRequestURI())) && !validate(httpRequest)) {
			httpResponse.sendError(org.apache.http.HttpStatus.SC_FORBIDDEN, "Could not authenticate the request.");
		}
		chain.doFilter(request, response);
	}

	private boolean validate(HttpServletRequest request) {
		OAuthConsumer cons = new OAuthConsumer(null, "code-challenge--vinay-gupta-129744", "kw6HU6cw6F1TVNYt", null);
		OAuthAccessor acc = new OAuthAccessor(cons);
		try {
			OAuthSignatureMethod method = OAuthSignatureMethod.newMethod("HMAC-SHA1", acc);
			OAuthMessage msg = OAuthServlet.getMessage(request, null);
			method.validate(msg);
		} catch (OAuthException | IOException | URISyntaxException e) {
			LOGGER.error("Could not authenticate the request due to: " + e.getMessage(), e);
			return false;
		}
		return true;
	}
}
