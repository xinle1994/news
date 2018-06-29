package com.nowcoder.toutiao.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nowcoder.model.News;
import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.util.ToutiaoUtil;

@Service
public class NewsService {

	@Autowired
	private NewsDAO newsDAO;
	
	public List<News> getLatestNews(int userId, int offset,int limit){
		return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
	}

	
	/**
	 * 返回 保存 图片的  本地地址
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String saveImage(MultipartFile file) throws Exception{
		int doPos = file.getOriginalFilename().lastIndexOf(".");
		if (doPos < 0) {
			return null;
		}
		String fileExt = file.getOriginalFilename().substring(doPos + 1).toLowerCase();
		boolean flag = ToutiaoUtil.isFileAllowed(fileExt);
		if (!flag) {
			return null;
		}
		//随机地名字
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
		
		Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		
		return ToutiaoUtil.TOUTIOA_DOMAIN + "image?name=" + fileName;
	}


	public void addNews(News news) {
		// TODO Auto-generated method stub
		newsDAO.addNews(news);
	}


	public News getNewsByNewsID(int newsId) {
		return newsDAO.getNewsByNewsID(newsId);
	}


	public int getOwnerId(News news) {
		return newsDAO.getOwnerId(news);
	}

	public void updateCommentCount(int entityId, int count) {
		newsDAO.updateCommentCount(entityId, count);
	}
	
	public void updateLikeCount(int id, int likeCount) {
		newsDAO.updateLikeCount(id, likeCount);
	}


	public List<News> getAll() {
		// TODO Auto-generated method stub
		return newsDAO.getAll();
	}
	
	public List<News> getAllByUserID(int userId) {
		// TODO Auto-generated method stub
		return newsDAO.getAllByUserID(userId);
	}


	public long getAllCount() {
		// TODO Auto-generated method stub
		return newsDAO.getAllCount();
	}


	


}
