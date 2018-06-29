package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.pagehelper.PageInfo;

/**
 * Created by rainday on 16/6/30.
 */
public class ViewObject {
	
	

	private int likeOrDisLike;
	
	
	public int getLikeOrDisLike() {
		return likeOrDisLike;
	}

	public void setLikeOrDisLike(int likeOrDisLike) {
		this.likeOrDisLike = likeOrDisLike;
	}

	private int unreadCount;
	
	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	private Message message;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	private Comment comment;
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	private News news;
	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;
	
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    	if (key == "news") {
			news = (News) value;
		}else if (key == "user") {
			user = (User) value;
		}else if (key == "comment") {
			comment =  (Comment) value;
		}else if (key == "message") {
			message =   (Message) value;
		}else if (key == "unreadCount") {
			unreadCount =   (int) value;
		}else if (key == "likeOrDisLike") {
			likeOrDisLike = (int) value;
		}
    	
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
