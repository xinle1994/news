package com.nowcoder.toutiao.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.nowcoder.model.EntityType;
import com.nowcoder.model.Message;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import com.nowcoder.toutiao.async.EventHandler;
import com.nowcoder.toutiao.async.EventModel;
import com.nowcoder.toutiao.async.EventType;
import com.nowcoder.toutiao.service.MessageService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.MailSender;

@Component
public class LoginExceptionHandler implements EventHandler{
	
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	MailSender mailSender;
	

	@Override
	public void doHandle(EventModel model) {
		System.out.println("LoginExceptionHandler.doHandle()");
		//判断 是否 IP 地址 登录异常
		Message message = new Message();
		message.setFromId(1);
		message.setToId(model.getActorId());
		message.setCreatedDate(new Date());
		message.setConversationId("1_" + message.getToId());
		message.setContent("你刚刚登陆了。。");
		messageService.addMessage(message);
		
		 mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常");
		System.out.println("**************LoginExceptionHandler.doHandle()");
	}

	@Override
	public List<EventType> getSupportEventType() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LOGIN);
	}

}
