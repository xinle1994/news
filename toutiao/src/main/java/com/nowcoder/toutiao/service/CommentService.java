package com.nowcoder.toutiao.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.model.Comment;
import com.nowcoder.model.StatusType;
import com.nowcoder.toutiao.dao.CommentDAO;

@Service
public class CommentService {

	@Autowired
	CommentDAO commentDAO;
	
	public List<Comment> getCommentByEntity(int entityId, int entityType){
		return commentDAO.selectByEntity(entityId, entityType);
	}
	
	public int getCommentCount(int entityId, int entityType){
		return commentDAO.getCommentCount(entityId, entityType);
	}
	
	public int addComment(Comment comment) {
		return commentDAO.addComment(comment);
	}
	
	/**
	 * 通过 id 获取 评论对象
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	public Comment getCommentById(int id){
		return commentDAO.getCommentById(id);
	}
	
	
	
	/**
	 * 删除评论（该方法存疑）
	 * @param entityId
	 * @param entityType
	 * @param status
	 */
	 public void deleteComment2( int entityId,  int entityType) {
		 commentDAO.updateStatus(entityId, entityType, StatusType.INVALID_TYPE);
	 }
	
	 /**
		 * 删除 评论
		 * @param entityId
		 * @param entityType
		 * @param status
		 */
	   public void deleteComment( int id) {
			 commentDAO.updateStatus2(id, StatusType.INVALID_TYPE);
		 }
	 
}
