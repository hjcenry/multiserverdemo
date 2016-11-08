package com.kidbear.plane.gm.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login() {

	}

	@RequestMapping(value = "/loginHandle", method = RequestMethod.POST)
	@ResponseBody
	public String loginHandle(HttpServletRequest request) {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		Admin admin = new Admin();
		admin.setName(name);
		admin.setPwd(pwd);
		boolean flag = adminService.login(admin);
		if (flag) {
			request.getSession().setAttribute("admin", admin);
			return "success";
		}
		return "failed";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("admin");
		try {
			response.sendRedirect("login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
