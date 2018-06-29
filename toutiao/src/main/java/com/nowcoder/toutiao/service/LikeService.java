package com.nowcoder.toutiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.toutiao.util.JedisAdapter;
import com.nowcoder.toutiao.util.RedisKeyUtil;

@Service
public class LikeService {
	
	@Autowired
	JedisAdapter jedisAdapter;
	
	
	
	/**
	 * 判断 某一用户对 一个 元素 是否喜欢（资讯 或者 评论）
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return  喜欢 返回1 不喜欢返回-1 否则返回 0
	 */
	public int getLikeOrDisLike(int userId, int entityType, int entityId) {
		
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}else if (jedisAdapter.sismember(disLikeKey, String.valueOf(userId))) {
			return -1;
		}
		return 0;
		
	}
	
	
	/**
	 *  
   *         返回 like 的人数
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public long like(int userId, int entityType, int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		 jedisAdapter.sadd(likeKey, String.valueOf(userId));
		 String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		jedisAdapter.srem(disLikeKey,  String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
	
	
	/**
	 *  
   *         返回 like 的人数
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	public long disLike(int userId, int entityType, int entityId) {
		 String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
		jedisAdapter.sadd(disLikeKey,  String.valueOf(userId));
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		 jedisAdapter.srem(likeKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
	
	
	

}
