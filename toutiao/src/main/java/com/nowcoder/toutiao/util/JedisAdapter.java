package com.nowcoder.toutiao.util;

import java.util.List;

import javax.swing.JInternalFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.nowcoder.toutiao.controller.LoginController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



/**
 *  implements InitializingBean,初始化
 * @author Administrator
 *
 */
@Service
public class JedisAdapter implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	
	
	private JedisPool jedisPool = null;

	/**
	 * 在 spring IOC 容器启动时，装配 jedispool
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		jedisPool = new JedisPool("localhost", 6379);
	}
	
	/**
	 * 获取 jedis 对象
	 * @return
	 */
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	
	
	
	
	public <T> T getObject(String key, Class<T> clazz) {
		String value = get(key);
		if (value != null) {
			return JSON.parseObject(value, clazz);
		}
		return null;
	}
	
	
	/**
	 * 将 object 类型的对象，先转为 json ，在存储在 redis 中
	 * @param key
	 * @param object
	 */
	public void setObject(String key, Object object) {
		set(key, JSON.toJSONString(object));
	}
	
	

	/**
	 * redis 存储 ，其中 key 键  ：： value为string 类型的 json 串
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("异常" + e.getMessage());
		}finally {
			
		}
	}
	
	
	
	/**
	 * 从 redis 中获取数据
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("异常！" + e.getMessage());
			return null;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return  Integer reply, specifically: 1 if the new element was added 0 if the element was
   *         already a member of the set
   *         
   *         插入成功 返回 1 ；
   *         若集合中原先就存在钙元素，则插入失败，返回 0；
	 */
	public long sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.sadd(key, value);
			
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public long srem(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.srem(key, value);
			
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	/**
	 * 判断当前登录状态的 的用户是否 给 该条资讯点过赞， 
	 * 若点过赞 ， 则显示 高亮
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.sismember(key, value);
			
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return false;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	
	/**
	 * 返回 点赞 的人数
	 * @param key
	 * @return
	 */
	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.scard(key);
			
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return -1;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	

	/**
	 * 插入 事件队列 同时 返回 插入后队列的 长度
	 * @return 插入后队列的 长度
	 */
	public long lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("异常！" + e.getMessage());
			return 0;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 阻塞 方式 ，从 redis 中的 list 中，取数据（从左边入， 右边取）
	 * b: block 阻塞
	 * r: right 右边
	 * pop: 弹出
	 * 
	 * 返回值是一个 只有两个元素的 list【key,value】
	 * 
	 * @param i
	 * @param key
	 */
	public List<String> brpop(int timeout, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error("异常" + e.getMessage());
			return null;
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
		
	}
	
	
	// public static void print(int index, Object object) {
	// System.out.println(String.format("%d,%s", index,object.toString()));
	// }
	//
	// public static void main(String[] args) {
	// Jedis jedis = new Jedis();
	// jedis.flushAll();
	// jedis.set("a", "a111");
	// print(0, jedis.get("a"));
	//
	// jedis.set("b", "222");
	//// jedis.incr("b");
	// jedis.incrBy("b", 20);
	// System.out.println(jedis.get("b"));
	//
	//
	// /**
	// * 列表操作
	// *
	// */
	//
	// String listName = "listA";
	// for (int i = 0; i < 10; i++) {
	// jedis.lpush(listName, "a"+i);
	// }
	//
	// print(3, jedis.lrange(listName, 0, 12));
	// print(3, jedis.lpop(listName));
	//
	// listName = "listB";
	// for (int i = 0; i < 10; i++) {
	// jedis.rpush(listName, "a"+i);
	// }
	//
	// print(3, jedis.lrange(listName, 0, 12));
	// print(3, jedis.lpop(listName));
	// print(3, jedis.lrange(listName, 0, 12));
	// jedis.linsert(listName, LIST_POSITION.AFTER, "a4", "ahhahha");
	// print(3, jedis.lrange(listName, 0, 12));
	//
	// String userkey = "user12";
	// jedis.hset(userkey, "name", "xiaoming");
	// jedis.hset(userkey, "age", "12");
	// jedis.hset(userkey, "phone", "123456789");
	//
	// print(3, jedis.hget(userkey, "name"));
	// print(3, jedis.hgetAll(userkey));
	//
	// print(3, jedis.hkeys(userkey));
	// print(3, jedis.hvals(userkey));
	// print(3, jedis.hexists(userkey, "email"));
	// print(3, jedis.hexists(userkey, "age"));
	//
	// jedis.hsetnx(userkey, "schello", "ada");
	// jedis.hsetnx(userkey, "name", "ad111a");
	// print(3, jedis.hgetAll(userkey));
	//
	// System.out.println("------------------------");
	// String rankKey = "rankKey";
	// jedis.zadd(rankKey, 15, "jim");
	// jedis.zadd(rankKey, 25, "jim");
	// jedis.zadd(rankKey, 50, "tom");
	// jedis.zadd(rankKey, 5, "jery");
	// jedis.zadd(rankKey, 60, "ss");
	// jedis.zadd(rankKey, 90, "lgx");
	// System.out.println(jedis.zcard(rankKey));
	// System.out.println(jedis.zcount(rankKey, 60, 100));
	// System.out.println(jedis.zscore(rankKey, "jim"));
	
	// }
}
