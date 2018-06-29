package com.nowcoder.toutiao.async;

import java.util.HashMap;
import java.util.Map;


/**
 * 保存 发生事件 时 ，现场 的一些数据
 * 
 *  代表 某一个 事件
 * @author Administrator
 *
 */
public class EventModel {
	
	public EventModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	public EventModel(EventType type) {
		this.type = type;
	}
	
	
	
	

	/**
	 * 触发的事件类型
	 */
	private EventType type;
	
	/**
	 * 触发者
	 */
    private int actorId;
    
    /**
     * 代表被触发对象 id
     */
    private int entityId;
    
    /**
     * 代表被触发对象 类型
     */
    private int entityType;
    
    /**
     * 被触发的 对象 的 拥有者 是什么
     */
    private int entityOwnerId;
    
    /**
     * 保存触发事件时，需要传递 以及 保存的参数（扩展信息）
     */
    private Map<String, String> exts = new HashMap<>();

	public EventType getType() {
		return type;
	}

	public EventModel setType(EventType type) {
		this.type = type;
		return this;
	}

	public int getActorId() {
		return actorId;
	}

	public EventModel setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}

	public int getEntityId() {
		return entityId;
	}

	public EventModel setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	public int getEntityType() {
		return entityType;
	}

	public EventModel setEntityType(int entityType) {
		this.entityType = entityType;
		return this;
	}

	public int getEntityOwnerId() {
		return entityOwnerId;
	}

	public EventModel setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
		return this;
	}

	public Map<String, String> getExts() {
		return exts;
	}

	public EventModel setExts(Map<String, String> exts) {
		this.exts = exts;
		return this;
	}
    
    
	
	public String getExt(String key) {
		return exts.get(key);
	}
    
	public EventModel setExt(String key, String value) {
		exts.put(key, value);
		return this;
	}


	

    

}
