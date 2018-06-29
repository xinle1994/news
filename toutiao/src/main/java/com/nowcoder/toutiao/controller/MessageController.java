package com.nowcoder.toutiao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.toutiao.dao.MessageDAO;
import com.nowcoder.toutiao.service.HostHolder;
import com.nowcoder.toutiao.service.MessageService;
import com.nowcoder.toutiao.service.UserService;

@Controller
public class MessageController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	

	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HostHolder hostHolder;
	
	
	@RequestMapping(value= {"/msg/list"},method= {RequestMethod.GET})
	public String conversationList(Model model) {
		try {
			/**
			 * conversationList :还没有 封装 消息数量  等等其他 信息的 会话列表  集合对象
			 */
			List<Message> conversationList = messageService.getConversationList(hostHolder.getUser().getId(), 0, 20);
			
			/**
			 * letter :封装 用户 等等其他 信息 后  的 message 集合对象
			 */
			List<ViewObject> conversations = new ArrayList<>();
			
			for (Message  message : conversationList) {
				User user = hostHolder.getUser().getId() == message.getFromId() ? userService.getUser(message.getToId()) : userService.getUser(message.getFromId());
				if (user == null) {
					continue;
				}
				ViewObject vo =  new ViewObject();
				int unreadCount = messageService.getConversationUnReadCount(hostHolder.getUser().getId(), message.getConversationId());
				vo.set("user", user);
				vo.set("message", message);
				vo.set("unreadCount", unreadCount);
				conversations.add(vo);
			}
			model.addAttribute("conversations", conversations);
			
			return "letter";
		} catch (Exception e) {
			logger.error("获取会话列表失败！" + e.getMessage());
		}
		return "error";
	}
	
	
	@RequestMapping(value= {"/msg/detail/{conversationId}"},method= {RequestMethod.GET})
	public String conversationDetail(Model model
			,@PathVariable("conversationId") String conversationId) {
		try {
			/**
			 * conversationList :还没有 封装 用户 等等其他 信息的 message 集合对象
			 */
			List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
			
			/**
			 * messages :封装 用户 等等其他 信息 后  的 message 集合对象
			 */
			List<ViewObject> messages = new ArrayList<>();
			for (Message  message : conversationList) {
				User user = userService.getUser(message.getFromId());
				if (user == null) {
					continue;
				}
				ViewObject vo =  new ViewObject();
				vo.set("user", user);
				vo.set("message", message);
				messages.add(vo);
			}
			model.addAttribute("messages", messages);
			
			return "letterDetail";
		} catch (Exception e) {
			logger.error("获取详细信息失败！" + e.getMessage());
		}
		return "error";
	}
	
	
	@RequestMapping(value= {"/addMessage"})
	@ResponseBody
	public String addMessage(@RequestParam("content") String content,
			@RequestParam("fromId") int fromId,
			@RequestParam("toId") int toId) {
		Message message = new Message();
		message.setFromId(fromId);
		message.setToId(toId);
		message.setCreatedDate(new Date());
		message.setHasRead(0);
		message.setContent(content);
		message.setConversationId(fromId < toId ? String.format("%d_%d", fromId,toId) : String.format("%d_%d", toId,fromId));
		messageService.addMessage(message);
		return "hahahhaha";
		
	}
}
