package com.nowcoder.toutiao.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    
    //使用 具名 参数 的 SQL 语句 
    
    
    
    
    //删除评论 （实际上 在数据库中 并非是真正的删除，而是 改变状态）
    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    void updateStatus2(@Param("id") int id, @Param("status") int status);

    
    /**
     * 该方法存疑
     * @param entityId
     * @param entityType
     * @param status
     */
    //删除评论 （实际上 在数据库中 并非是真正的删除，而是 改变状态）
    @Update({"update ", TABLE_NAME, " set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("status") int status);

    
    
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId} and status = 0 order by id desc "})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId} and status = 0"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select * from ", TABLE_NAME, " where id=#{id}"})
	Comment getCommentById(@Param("id")int id);
}
