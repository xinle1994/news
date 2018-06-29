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

@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class InitDataBaseTest {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	NewsDAO newsDAO;
	
	@Autowired
	LoginTicketDAO loginTicketDAO;
	
	@Autowired
	CommentDAO commentDAO;
	
	@Autowired
	MessageDAO messageDAO;
	
	
	
	
	
	
	@Test
	public void test2() {
//		commentDAO.updateStatus(11, 1, StatusType.INVALID_TYPE);
		List<Message> list = messageDAO.getConversationList(1, 0, 20);
		for (Message message : list) {
			System.out.println(message);
		}
		
	}
	
	@Test
	public void test1() {
//		commentDAO.updateStatus(11, 1, StatusType.INVALID_TYPE);
		int count = commentDAO.getCommentCount(11, 1);
		System.out.println(count);
		
	}
	
	@Test
	public void contextLoads() {
		
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(3);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(0);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);

            // 给每个资讯插入3个评论
            for(int j = 0; j < 3; ++j) {
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("这里是一个评论啊！" + String.valueOf(j));
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                commentDAO.addComment(comment);
            }

            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i+1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 2);

        }
		
//		 Random random = new Random();
//	        for (int i = 0; i < 11; ++i) {
//	            User user = new User();
//	            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//	            user.setName(String.format("USER%d", i));
//	            user.setPassword("");
//	            user.setSalt("");
//	            userDAO.addUser(user);
//
//	            News news = new News();
//	            news.setCommentCount(i);
//	            Date date = new Date();
//	            date.setTime(date.getTime() + 1000*3600*5*i);
//	            news.setCreatedDate(date);
//	            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
//	            news.setLikeCount(i+1);
//	            news.setUserId(i+1);
//	            news.setTitle(String.format("TITLE{%d}", i));
//	            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
//	            newsDAO.addNews(news);
//
//	            user.setPassword("newpassword");
//	            userDAO.updatePassword(user);
//	            
//	            
//	            LoginTicket loginTicket = new LoginTicket();
//	            loginTicket.setStatus(0);
//	            loginTicket.setUserId(i + 1);
//	            loginTicket.setExpired(date);
//	            loginTicket.setTicket("TICKET" + (i + 1));
//	        
//	            loginTicketDAO.addTicket(loginTicket);
	        
	        
//	        }
		
//		News news = new News();
//		 Random random = new Random();
//		 Date date = new Date();
//		 news.setCreatedDate(date);
//         news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
//         news.setLikeCount(2);
//         news.setUserId(2);
//         news.setTitle(String.format("######TEST---TITLE{%d}", 2));
//         news.setLink(String.format("http://www.nowcoder.com/%d.html", 2));
//         int count = newsDAO.addNews(news);
//         System.out.println("共改变：" + count);
//         System.out.println("插入后的id为" + news.getId());
		
		

  }
	
	
	
	
	
}
