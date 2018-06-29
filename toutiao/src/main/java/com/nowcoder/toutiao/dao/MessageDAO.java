package com.nowcoder.toutiao.dao;

import com.nowcoder.model.Comment;
import com.nowcoder.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    /**
     * 列出 会话 列表 （多组 不同的 会话）， 并把 每组会话中的 最新的 消息 返回， 同时 会话组 列表 也是按最新的 消息 回复时间 排序
     * @param 
     * @param 
     * @return
     */
    @Select({"select ", INSERT_FIELDS, " ,count(id) as messageCount from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    
  
    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    
    
    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId}"})
    int getConversationTotalCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    
    /**
     * 查询 某一个 会话 中的 全部消息 （message）
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);
}