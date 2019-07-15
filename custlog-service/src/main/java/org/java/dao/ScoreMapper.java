package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ScoreMapper {

    @Insert("insert scoreinfo values(#{m.custid},#{m.time},#{m.val},#{m.remark})")
    int addInfo(@Param("m")Map<String,Object> m);

    @Select("select * from scoreinfo where custid=#{custid} ")
    List<Map<String,Object>> showScore(@Param("custid")String custid);

}
