package com.nowcoder.toutiao.controller;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.service.CommentService;
import com.nowcoder.toutiao.service.HostHolder;
import com.nowcoder.toutiao.service.LikeService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.util.ToutiaoUtil;

/**
 *
 */



@Controller
public class NewsController {
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	
	@Autowired
	LikeService likeService;
	
	   @Autowired
	    UserDAO userDAO;
	   
	   @Autowired
	   UserService userService;
	   
	   @Autowired
	   NewsService newsService;
	   
	   @Autowired
	   HostHolder hostHolder;
	   
	   @Autowired
	   CommentService commentService;

    @RequestMapping(path = {"/uploadimage/"},method= {RequestMethod.POST})
    public String Uploadimage(@RequestParam("file") MultipartFile file){
    	try {
    		String fileUrl = newsService.saveImage(file);
    		if (fileUrl == null) {
    			return ToutiaoUtil.getJSONString(1, "文件上传失败");
			}
    		System.out.println(fileUrl);
			return "success";
		} catch (Exception e) {
			logger.error("文件上传失败" + e.getMessage());
			return ToutiaoUtil.getJSONString(1, "文件上传失败");
		}
    	
       

    }
    
    
    

    
    /**
     * 跳转到 新闻 页面 详情
     * @param image
     * @param title
     * @param link
     * @return
     */
    @RequestMapping(path = {"/news/{newsId}"})
    public String newsDetail(Model model,@PathVariable("newsId") int newsId){
    	News news = newsService.getNewsByNewsID(newsId);
    	if (news == null) {
			return "error";
		}
    	//获取 评论信息 --- 待完成
    	List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
    	List<ViewObject> commentVOs = new ArrayList<>();
    	for (Comment comment : comments) {
			ViewObject vo = new ViewObject();
			vo.set("comment", comment);
			vo.set("user", userService.getUser(comment.getUserId()));
			commentVOs.add(vo);
		}
    	
    	//封装 评论信息实体（其中 加上评论者 也就是用户的 信息）
    	model.addAttribute("comments", commentVOs);
    	
    	// 获取信息本体
    	model.addAttribute("news", news);
    	
    	//获取信息的发布者
    	model.addAttribute("owner", userDAO.selectById(news.getUserId()));
    	
    	
    	User user = hostHolder.getUser();
    	if (user != null && user.getId() != 0) {
			int likeOrDisLike = likeService.getLikeOrDisLike(user.getId(), EntityType.ENTITY_NEWS, news.getId());
			model.addAttribute("likeOrDisLike", likeOrDisLike);
		}
    	
    	
		return "newsPage";
    }
    
    
     
    
    
   /**
    * 跳转到 添加页面
    * @param image
    * @param title
    * @param link
    * @return
    */
    @RequestMapping(path = {"/addPage"},method= {RequestMethod.POST,RequestMethod.GET})
    public String addPage() {
    	
    	return "addPage";
    }
    
    
    
    /**
     * 编写头条新闻
     * @param image
     * @param title
     * @param link
     * @return
     */
    @RequestMapping(path = {"/addNews"},method= {RequestMethod.POST,RequestMethod.GET})
    public String addNews(@RequestParam("title") String title,
    		@RequestParam("link") String link,
    		@RequestParam("file") MultipartFile file){
    	System.out.println("NewsController.addNews()");
    	
    	
    	try {
    		String fileUrl = newsService.saveImage(file);
    		if (fileUrl == null) {
    			return ToutiaoUtil.getJSONString(1, "发帖失败，文件上传失败");
			}
    		System.out.println(fileUrl);
    		String image = fileUrl;
        	News news =  new News();
        	news.setCreatedDate(new Date());
        	news.setCommentCount(0);
        	news.setImage(image);
        	news.setLikeCount(0);
        	news.setTitle(title);
        	news.setLink(link);
        	news.setUserId(hostHolder.getUser().getId());
        	newsService.addNews(news);
			
		} catch (Exception e) {
			logger.error("文件上传失败" + e.getMessage());
			return ToutiaoUtil.getJSONString(1, "发帖失败，文件上传失败");
		}
    	
    	
    
    	return "redirect:/index";
       

    }
    
    
    
    
    
    /**
     * 文件的 获取 ， 把文件的二进制 流 写入到 response 中
     * @param imageName
     * @param response
     */
    @RequestMapping(path = {"/image"},method= {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
    		HttpServletResponse response){
    	
    	response.setContentType("image/jpeg ");
    	try {
    		
			StreamUtils.copy(new FileInputStream(
					new File(ToutiaoUtil.IMAGE_DIR + imageName)), response.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("读取文件错误" + e.getMessage());
		}

    }

    
    
    
    
    @RequestMapping(path = {"/upload"},method= {RequestMethod.GET})
    public String Upload(){
    	return "upload";

    }

 
}
