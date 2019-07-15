package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface Case_ResultMapper {

    @Insert("INSERT INTO case_result values (#{m.cr_id},#{m.is_success},#{m.fail_massage},#{m.time})")
    public int insert(Map<String,Object> map);

    @Select("SELECT * FROM case_result")
    public Map<String,Object> getAll();
}
