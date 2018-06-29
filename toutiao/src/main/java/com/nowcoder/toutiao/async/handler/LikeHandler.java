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

@Component
public class LikeHandler implements EventHandler{
	
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NewsService newsService;
	

	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
		System.out.println("LikeHandler.doHandle()");
		
		Message message = new Message();
		message.setFromId(1);
		message.setToId(model.getEntityOwnerId());
		message.setCreatedDate(new Date());
		message.setConversationId("1_" + model.getEntityOwnerId());
		
		User user = userService.getUser(model.getActorId());
		
		if (model.getEntityType() == EntityType.ENTITY_NEWS) {
			News news = newsService.getNewsByNewsID(model.getEntityId());
			message.setContent("用户 " + user.getName() +" 赞了你的资讯："
			   + "<a href='http://localhost:8080/news/" + news.getId() + "'>" + news.getTitle() + "</a>");
		}
		
		messageService.addMessage(message);
		
		
		
	}

	@Override
	public List<EventType> getSupportEventType() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LIKE);
	}

}
