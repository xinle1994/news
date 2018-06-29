package com.nowcoder.toutiao.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.toutiao.controller.NewsController;
import com.nowcoder.toutiao.util.JedisAdapter;
import com.nowcoder.toutiao.util.RedisKeyUtil;


/**
 * 实现 InitializingBean 接口 是为了初始化 bean 是的一些操作
 * 实现 ApplicationContextAware 接口 是为了获得所有 实现了eventhandler 接口的类
 * 
 * @author Administrator
 *
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{

	private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
	private ApplicationContext applicationContext;
	/**
	 * 将 每一种 事件 类型 ，对应的 一个 或者多个 eventhandler 组织起来，便于管理
	 * 
	 * 通过 EventType 可找出 一个或者 多个 EventHandler组成的 EventHandler处理链
	 */
	private Map<EventType, List<EventHandler>> config = new HashMap<>();
	
	
	
	@Autowired
	JedisAdapter jedisAdapter;

	
	
	
	/**
	 * 初始化 bean 的所需要做的事情
	 * 
	 * 找出 IOC 容器中所有 实现了 eventhandelr 接口的 类
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
		if (beans != null) {
			
			/**
			 * 遍历找出 每一个 handler 所支持的 eventtype； 然后将所有的 handler 的一个或多个 eventtype 统一组织管理；
			 * 随后，可通过 eventtype 反向找出 一个 或者 多个 handler
			 * 
			 */
			for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
				List<EventType> eventTypes = entry.getValue().getSupportEventType();
				for (EventType eventType : eventTypes) {
					if (!config.containsKey(eventType)) {
						config.put(eventType, new ArrayList<EventHandler>());
					}
					config.get(eventType).add(entry.getValue());
				}
			}
		}
		
		
		
		// eventproducer 将 事件对象 放入到的是 jedis 中的list， 该list 是一个阻塞队列
		// eventconsumer 需要从 该list 中 取出 event事件 对象
		
		
		/**
		 * 开启线程，从阻塞队列中获取 事件对象
		 */
		Thread thread =  new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					String key = RedisKeyUtil.getEventQueueKey();
					List<String> brpop = jedisAdapter.brpop(0,key);
					for (String message : brpop) {
						if (message.equals(key)) {
							continue;
						}
						EventModel eventModel = JSONObject.parseObject(message,EventModel.class);
						if (! config.containsKey(eventModel.getType())) {
							logger.error("不能识别的事件对象");
							continue;
						}
						for (EventHandler handler : config.get(eventModel.getType())) {
							handler.doHandle(eventModel);
						}
					}
				}
			}
		});
		thread.start();
		
	}



	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
