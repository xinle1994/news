package com.nowcoder.toutiao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.toutiao.async.EventModel;
import com.nowcoder.toutiao.async.EventProducer;
import com.nowcoder.toutiao.async.EventType;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.ToutiaoUtil;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	EventProducer eventProducer;
	
	
	@Autowired
	UserService userService;
	
	  @RequestMapping(path = {"/regger"})
       public String index(Model model){
		return "regger";
    }
	
	  
	  
	  
	  
	  @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
	    public String logout(@CookieValue("ticket") String ticket) {
	        userService.logout(ticket);
	        return "redirect:/";
	    }

	    @RequestMapping(path = {"/reg/"})
//	    @ResponseBody
	    public String reg(Model model,@RequestParam("username") String username,
	    		@RequestParam("password") String password,
	    		@RequestParam(value="rember",defaultValue="0") int rememberme,
	    		HttpServletResponse response){
	    	try {
				Map<String, Object> map = userService.register(username, password);
				if (map.containsKey("ticket")) {
					Cookie cookie =  new Cookie("ticket", (String) map.get("ticket"));
					cookie.setPath("/");
					if (rememberme > 0) {
						cookie.setMaxAge(3600*24*5);
					}
					response.addCookie(cookie);
					return "redirect:/index";
//					return ToutiaoUtil.getJSONString(0, "注册成功！");
				}else {
					return ToutiaoUtil.getJSONString(1, map);
				}
			} catch (Exception e) {
				logger.error("注册异常： " + e.getMessage());
				return ToutiaoUtil.getJSONString(1, "注册异常！");
			}
	    	
	    	
	    }
	    
	    
	    @RequestMapping(path = {"/set"})
	    @ResponseBody
	    public String set(Model model){
	    	return "hahha";
	    }   
	    
	    @RequestMapping(path = {"/log/"})
	       public String log(Model model){
	    	model.addAttribute("LoginType", true);//首页登陆
			return "login";
	    }
	    
	    // 未登录状态查看帖子；登陆后，返回原先帖子的地址
	    @RequestMapping(path = {"/logNews/news/{newsId}"})
	       public String logNews(Model model,@PathVariable("newsId") int newsId){
	    	model.addAttribute("newsLink", "/news/" + newsId);
	    	model.addAttribute("LoginType", false);//非首页登陆
			return "login";
	    }
	    
	    
	    

	    @RequestMapping(path = {"/login/"})
//	    @ResponseBody
	    public String login(Model model,@RequestParam("username") String username,
	    		@RequestParam("password") String password,
	    		@RequestParam(value="rember",defaultValue="0") int rememberme,
	    		HttpServletResponse response,
	    		@RequestParam(value="newsLink",required= false) String newsLink){
	    	try {
				Map<String, Object> map = userService.login(username, password);
				if (map.containsKey("ticket")) {
					Cookie cookie =  new Cookie("ticket", (String) map.get("ticket"));
					cookie.setPath("/");
					if (rememberme > 0) {
						cookie.setMaxAge(3600*24*5);
					}
					response.addCookie(cookie);
					
					
					eventProducer.fireEvent(new EventModel(EventType.LOGIN).setActorId((int) map.get("userId"))
							                                 .setExt("username", username)
							                                 .setExt("email", "gxlee@seu.edu.cn")
							                                 );
					
					
					if (newsLink == null) {
						return "redirect:/index";
					}else {
						return "redirect:"+ newsLink;
					}
					
//					return ToutiaoUtil.getJSONString(0, "注册成功！");
				}else {
					return ToutiaoUtil.getJSONString(1, map);
				}
			} catch (Exception e) {
				logger.error("注册异常： " + e.getMessage());
				return ToutiaoUtil.getJSONString(1, "注册异常！");
			}
	    	
	    	
	    }


}
