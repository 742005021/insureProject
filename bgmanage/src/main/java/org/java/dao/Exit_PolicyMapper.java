package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Exit_PolicyMapper {

    @Select("select * from exitpolicy where exitstatu=0")
    List<Map<String,Object>> getExitPolicy();


}
