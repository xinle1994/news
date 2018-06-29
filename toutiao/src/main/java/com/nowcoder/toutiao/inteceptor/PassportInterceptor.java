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
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.service.HostHolder;

@Component
public class PassportInterceptor implements HandlerInterceptor {

	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	
	@Autowired
	private UserDAO userDAO ;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		
		//用于收尾工作
		hostHolder.clear();
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// 此时 controller 中的方法结束了，但是在 视图 渲染之前，
		// 把查到的 user 信息从 threadlocal 中取出，放到 modelandview中，便于到页面获取
		if (modelAndView != null && hostHolder.getUser() != null) {
			modelAndView.addObject("user", hostHolder.getUser());
		}
		
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("ticket")) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		
		if (ticket != null) {
			LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
			if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
				return true;
			}
			
			User user = userDAO.selectById(loginTicket.getUserId());
			hostHolder.setUser(user);
		}
		
		return true;
	}

}
