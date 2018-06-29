package com.nowcoder.toutiao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.Message;
import com.nowcoder.model.News;
import com.nowcoder.model.StatusType;
import com.nowcoder.model.User;
import com.nowcoder.toutiao.dao.CommentDAO;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.MessageDAO;
import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.service.CommentService;
import com.nowcoder.toutiao.util.JedisAdapter;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTest {

	@Autowired
	JedisAdapter jedisAdapter;

	
	@Test
	public void test2() {
		Jedis jedis = jedisAdapter.getJedis();
	
		jedis.lpush("asd", "AAA");
		jedis.lpush("asd", "BBB");
		jedis.lpush("asd", "CCC");
		jedis.lpush("asd", "DDD");
		
		System.out.println(jedis.brpop(3,"asd"));
		System.out.println(jedis.brpop(3,"asd"));
		System.out.println(jedis.brpop(3,"asd"));
		
		
	}
	
	
	
	
	@Test
	public void test() {
		User user = new User();
		user.setHeadUrl("headurl");
		user.setName("lgx");
		user.setPassword("password");
		user.setSalt("salt");
		jedisAdapter.setObject("userTest", user);
		
		/*
		 * {\"headUrl\":\"headurl\",\"id\":0,\"name\":\"lgx\",\"password\":\"password\",\"salt\":\"salt\"}
		 * */
		
		User user2 = jedisAdapter.getObject("userTest", User.class);
		System.out.println(user2);
		System.out.println(user);
		System.out.println(user2 == user);
		
	}
	

	
	
	
	
	
}
