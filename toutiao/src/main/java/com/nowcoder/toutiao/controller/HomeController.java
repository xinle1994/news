package com.nowcoder.toutiao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.toutiao.service.HostHolder;
import com.nowcoder.toutiao.service.LikeService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.Mypage;

@Controller
public class HomeController {

	@Autowired
	NewsService newsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LikeService likeService;
	
	
	@Autowired
	HostHolder hostHolder;
	
	private List<ViewObject> getNews(int userId,int offset, int limit){
		List<News> newList = newsService.getLatestNews(userId, offset, limit);
		
		
		List<ViewObject> vos = new ArrayList<>();
		for (News news : newList) {
			ViewObject viewObject = new ViewObject();
			viewObject.set("news", news);
			viewObject.set("user", userService.getUser(news.getUserId()));
			
			User user = hostHolder.getUser();
			if (user != null && user.getId() != 0) {
				int likeOrDisLike = likeService.getLikeOrDisLike(user.getId(), EntityType.ENTITY_NEWS, news.getId());
				viewObject.set("likeOrDisLike", likeOrDisLike);
			}
			vos.add(viewObject);
		}
		return vos;
	}
	
	
	

	/**
	 * 查询员工数据(分页查询)
	 * @return
	 */
	
	/**
	 * //@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn
			,Model model){
		//这不是一个分页查询
		
		//为了开发简单 ，引入了 pagehelper 插件 来进行分页查询
		//在查询之前。只需要调用  PageHelper.startPage(pn, 5);
		//那后面的查询 就自动成为了 分页查询
		PageHelper.startPage(pn, 5);
		//那后面的查询 就自动成为了 分页查询
		List<Employee> emps = employeeService.getAll();
		
		//用 pageinfo 来包装查询后的结果， 只需将pageinfo 对象交给页面就行了
		
		//pageinfo 中封装了详细的分页信息，包括我们查询出来的数据  连续显示的页数 5；
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	 * 
	 * 
	 */
	
	
	
	
	    @RequestMapping(path = {"/index","/"})
	    public String index(Model model,@RequestParam(value="pn",defaultValue="0") Integer pn){
//	    	PageHelper.startPage(pn, 10);
//	    	List<News> allNews = newsService.getAll();
//	    	PageInfo page = new PageInfo<>(allNews, 5);
//	    	
//	    	List<News> newList = page.getList();
//	    	
//	    	List<ViewObject> vos = new ArrayList<>();
//			for (News news : newList) {
//				ViewObject viewObject = new ViewObject();
//				viewObject.set("news", news);
//				viewObject.set("user", userService.getUser(news.getUserId()));
//				viewObject.set("pageInfo", page);
//				User user = hostHolder.getUser();
//				if (user != null && user.getId() != 0) {
//					int likeOrDisLike = likeService.getLikeOrDisLike(user.getId(), EntityType.ENTITY_NEWS, news.getId());
//					viewObject.set("likeOrDisLike", likeOrDisLike);
//				}
//				vos.add(viewObject);
//			}
			List<ViewObject> vos = getNews(0, pn*10, 10);
			Mypage pageInfo = new Mypage<>(pn);
			
			pageInfo.setTotalItemNumber((long)newsService.getAllCount());
			
			System.out.println(pageInfo.getTotalItemNumber());
			
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageInfo", pageInfo);
			return "home";
	    }
	    
	    @RequestMapping(path = {"/user/{userId}"})
	    public String userIndex(Model model,@PathVariable("userId") int userId,@RequestParam(value="pn",defaultValue="0") Integer pn){
	    	
	    	
	    	List<ViewObject> vos = getNews(userId, pn*10, 10);
			Mypage pageInfo = new Mypage<>(pn);
			
			pageInfo.setTotalItemNumber(vos.size());
			
			
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageInfo", pageInfo);
			return "home";
	    }

  
//	public String index(HttpSession session) {
//		
//		
//		return "hhhhh";
//		
//	}
}
