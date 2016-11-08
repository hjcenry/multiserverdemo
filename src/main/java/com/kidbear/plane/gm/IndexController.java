package com.kidbear.plane.gm;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class IndexController {
	public Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/index")
	public void index(HttpServletRequest request) {
		String host = request.getRemoteHost();
		logger.info("{} visit gm", host);
	}
}
