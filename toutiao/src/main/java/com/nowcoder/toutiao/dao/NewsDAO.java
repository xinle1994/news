package com.nowcoder.toutiao.dao;

import java.util.*;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nowcoder.model.News;
import com.nowcoder.model.User;

@Mapper
public interface NewsDAO {

	 String TABLE_NAME = "news";
	 String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
	 String SELECT_FIELDS = " id, " + INSERT_FIELDS;

	    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
	            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
//	    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	    int addNews(News news);

	    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
	                                       @Param("limit") int limit);

	    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
		News getNewsByNewsID(int id);

	    @Select({"select user_id from ", TABLE_NAME, " where id=#{id}"})
		int getOwnerId(News news);
	    
	    @Update({"update ", TABLE_NAME, " set comment_count=#{count} where id=#{id}"})
	    void updateCommentCount(@Param("id")int id, @Param("count")int count);

	    @Update({"update ", TABLE_NAME, " set like_count=#{likeCount} where id=#{id}"})
		void updateLikeCount(@Param("id")int id, @Param("likeCount")int likeCount);

	    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME," ORDER BY id DESC"})
		List<News> getAll();

	    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id=#{userId}  ORDER BY id DESC"})
		List<News> getAllByUserID(@Param("userId") int userId);

	    @Select({"select count(id) from ", TABLE_NAME})
		long getAllCount();
}
