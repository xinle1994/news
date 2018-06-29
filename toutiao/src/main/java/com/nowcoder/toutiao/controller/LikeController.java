package com.nowcoder.toutiao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.News;
import com.nowcoder.toutiao.async.EventModel;
import com.nowcoder.toutiao.async.EventProducer;
import com.nowcoder.toutiao.async.EventType;
import com.nowcoder.toutiao.service.HostHolder;
import com.nowcoder.toutiao.service.LikeService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.util.ToutiaoUtil;

@Controller
public class LikeController {

	@Autowired
	LikeService likeService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	EventProducer eventProducer;
	
	
	@RequestMapping(value= {"/likeNews/{entityId}"},
			method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String like(Model model,
			           @PathVariable("entityId") int entityId) throws JsonProcessingException {
		long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, entityId);
		newsService.updateLikeCount(entityId, (int) likeCount);
//		return ToutiaoUtil.getJSONString(0, (int) likeCount);
		
		
		News news = newsService.getNewsByNewsID(entityId);
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("likeCount", likeCount);
		 ObjectMapper objectMapper = new ObjectMapper();
		 
		 
		 eventProducer.fireEvent(new EventModel(EventType.LIKE)
				                               .setActorId(hostHolder.getUser().getId())
				                               .setEntityId(entityId)
				                               .setEntityType(EntityType.ENTITY_NEWS)
				                               .setEntityOwnerId(news.getUserId()));
		 
	     return objectMapper.writeValueAsString(result);

		
	}
	
	
	
	@RequestMapping(value= {"/disLikeNews/{entityId}"},
			method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String disLike(Model model,
			           @PathVariable("entityId") int entityId) throws JsonProcessingException {
		long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, entityId);
		newsService.updateLikeCount(entityId, (int) likeCount);
//		return ToutiaoUtil.getJSONString(0, (int) likeCount);
		
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("likeCount", likeCount);
		 ObjectMapper objectMapper = new ObjectMapper();
	     return objectMapper.writeValueAsString(result);
	}
	
}
