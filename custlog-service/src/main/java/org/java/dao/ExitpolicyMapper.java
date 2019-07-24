package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface ExitpolicyMapper {

    @Insert("insert exitpolicy values(null,#{map.policyid},#{map.custid},#{map.custname},#{map.custno},#{map.custcardno},#{map.cardimg},#{map.policyimg},0)")
    int createExitpolicy(@Param("map") Map<String,Object> map);
}
