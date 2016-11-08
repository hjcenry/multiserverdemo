package com.kidbear.plane.gm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidbear.plane.gm.admin.Admin;

public class SecurityFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(SecurityFilter.class);
	private static List<String> extensive = new ArrayList<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		extensive.add("login");
		extensive.add("notify");
		extensive.add("route");
		extensive.add("core");
		extensive.add("patch");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		// 从session里取的用户信息
		Admin admin = (Admin) session.getAttribute("admin");
		String uri = ((HttpServletRequest) request).getRequestURI();
		// 判断如果没有取到用户信息,就跳转到登陆页面
		if (admin != null || checkExclusive(uri)) {
			chain.doFilter(request, response);
		} else {
			// 跳转到登陆页面
			res.sendRedirect(req.getContextPath() + "/admin/login");
		}
	}

	/**
	 * @Title: checkExclusive
	 * @Description: 检查是否包含过滤的字段
	 * @param uri
	 * @return
	 * @return boolean
	 * @throws
	 */
	private boolean checkExclusive(String uri) {
		uri = uri.replaceAll("36", "");
		for (String tmp : extensive) {
			if (uri.contains(tmp)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {
	}

}
