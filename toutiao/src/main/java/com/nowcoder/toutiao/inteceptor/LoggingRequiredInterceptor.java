package com.nowcoder.toutiao.inteceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.toutiao.controller.HomeController;
import com.nowcoder.toutiao.controller.LoginController;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.service.HostHolder;

@Component
public class LoggingRequiredInterceptor implements HandlerInterceptor {

	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	
	@Autowired
	private UserDAO userDAO ;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	HomeController homeController;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//用于收尾工作
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 此时 controller 中的方法结束了，但是在 视图 渲染之前，
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (hostHolder.getUser() == null) { //表示当前 为 未登录状态
			System.out.println("null");
//			request.getRequestDispatcher("/index").forward(request, response);
			response.sendRedirect("/index");
//			homeController.index(null);
			return false;
		}else {
			System.out.println("yes");
		}
		
		return true;
	}

}
