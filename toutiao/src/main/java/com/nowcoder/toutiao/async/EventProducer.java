package com.nowcoder.toutiao.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.toutiao.util.JedisAdapter;
import com.nowcoder.toutiao.util.RedisKeyUtil;

@Service
public class EventProducer {
	
	@Autowired
	JedisAdapter jedisAdapter;
	
	
	
	/**
	 * 先将 事件 对象 序列化 ，然后再 放到 redis（队列中） 中
	 * @param model
	 * @return
	 */
	public boolean fireEvent(EventModel model) {
		try {
			String json = JSONObject.toJSONString(model);
			String key = RedisKeyUtil.getEventQueueKey();
			jedisAdapter.lpush(key, json);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
