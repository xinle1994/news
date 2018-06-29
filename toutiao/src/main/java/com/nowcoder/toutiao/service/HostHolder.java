package com.nowcoder.toutiao.service;

import org.springframework.stereotype.Component;

import com.nowcoder.model.User;

/**
 * 表示当前用户
 * @author Administrator
 *
 */
@Component
public class HostHolder {
	
	private ThreadLocal<User> users = new ThreadLocal<>();
	
	
	public User getUser() {
		return users.get();
	}

	
	public void setUser(User user) {
		users.set(user);
	}
	
	public void clear() {
		users.remove();
	}
}
