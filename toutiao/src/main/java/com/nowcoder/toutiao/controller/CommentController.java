package com.nowcoder.toutiao.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.toutiao.service.CommentService;
import com.nowcoder.toutiao.service.HostHolder;
import com.nowcoder.toutiao.service.NewsService;

@Controller
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	NewsService newsService;
	
	  
	   @Autowired
	   HostHolder hostHolder;
	   
	
	
	
    /**
     * 添加评论 的 方法  (对资讯进行 评论)
     * @param newsId
     * @return
     */
    @RequestMapping(path = {"/addComment"},method= {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
    		                 @RequestParam("content") String content ){
    	
    	try {
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setCreatedDate(new Date());
			comment.setEntityId(newsId);
			comment.setEntityType(EntityType.ENTITY_NEWS);
			comment.setStatus(0);
			comment.setUserId(hostHolder.getUser().getId());
			commentService.addComment(comment);
			//更新 news 中的 评论数量
			int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
			newsService.updateCommentCount(comment.getEntityId(), count);
			
			
			//----------------------如何异步化
			
		} catch (Exception e) {
			
		}
		return "redirect:/news/" + String.valueOf(newsId);
    	
    }
    
	
	
	
	@RequestMapping("/deleteComment/{commentId}/{newsId}")
	public String deleteComment(@PathVariable("commentId") int commentId,
			@PathVariable("newsId") int newsId) {
		try {
			commentService.deleteComment(commentId);
			Comment comment = commentService.getCommentById(commentId);
			//更新 news 中的评论个数
			int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
			newsService.updateCommentCount(comment.getEntityId(), count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/news/"+ newsId;
	}

}
