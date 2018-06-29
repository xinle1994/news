package com.nowcoder.toutiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.util.ToutiaoUtil;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 */
	public Map<String, Object> login(String username,String password) {
		Map<String, Object> map =  new HashMap<>();
		if (StringUtils.isBlank(username)) {
			map.put("msgname", "用户名不能为空！");
			return map;
		}
		
		if (StringUtils.isBlank(password)) {
			map.put("msgpwd", "密码不能为空！");
			return map;
		}
		
		User user = userDAO.selectByName(username);
		if (user == null) {
			map.put("msgname", "用户名不存在！");
			return map;
		}
		//验证密码
		System.out.println("password:  " + password);
		System.out.println("user.getSalt(): " + user.getSalt());
		System.out.println("ToutiaoUtil.MD5(password + user.getSalt()):  " +  ToutiaoUtil.MD5(password + user.getSalt()));
		System.out.println("user.getPassword():  " + user.getPassword());
		if (! ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword()) ) {
			map.put("msgpwd", "密码错误！!!");
			return map;
		}
		
		// 用户名 与 密码 均已验证正确
		// 下发 token
		map.put("userId", user.getId());
		
		String ticket = addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	
	
	/**
	 * 添加 LoginTicket
	 * @param userId
	 * @return
	 */
	private String addLoginTicket(int userId) {
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setUserId(userId);
		Date date = new Date();
		date.setTime(date.getTime() + 1000*3600*24);
		loginTicket.setExpired(date);
		loginTicket.setStatus(0);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(loginTicket);
		return loginTicket.getTicket();
		
	}
	
	
	/**
	 * 注册
	 * @param username
	 * @param password
	 * @return
	 */
	public Map<String, Object> register(String username,String password) {
		Map<String, Object> map =  new HashMap<>();
		if (StringUtils.isBlank(username)) {
			map.put("msgname", "用户名不能为空！");
			return map;
		}
		
		if (StringUtils.isBlank(password)) {
			map.put("msgpwd", "密码不能为空！");
			return map;
		}
		
		User user = userDAO.selectByName(username);
		if (user != null) {
			map.put("msgname", "用户名已存在！");
			return map;
		}
		user = new User();
		user.setName(username);
		user.setSalt(UUID.randomUUID().toString().substring(0, 5));
		user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
    	user.setPassword(ToutiaoUtil.MD5(password  + user.getSalt()));
		
		userDAO.addUser(user);
		
		
		//登陆
		String ticket = addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
		
		
	
	}
	
	public User getUser(int id) {
		return userDAO.selectById(id);
	}



	public void logout(String ticket) {
		// TODO Auto-generated method stub
		loginTicketDAO.updateStatus(ticket, 1);
	}
	
	
}
