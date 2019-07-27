package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface MessageMapper {

    @Select("select * from message where custid=#{custid} order by mdate desc")
    List<Map<String,Object>> getMessage(@Param("custid")String custid);

    @Update("update message set isread=1 where msgid=#{mid}")
    int updateStatu(@Param("mid") int mid);

    @Insert("insert into message values(null,#{map.custid},#{map.title},#{map.content},#{map.date},default)")
    int addMessage(@Param("map")Map<String,Object> map);


}
