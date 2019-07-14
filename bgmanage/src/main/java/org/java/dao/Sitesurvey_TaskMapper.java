package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface Sitesurvey_TaskMapper {

    @Insert("INSERT sitesurvey_task VALUES ("+
            "#{m.task_id},#{m.explains},#{m.emp_id},#{m.accident_type},#{m.address},"+
            "#{m.file},#{m.is_ture},#{m.massage} ) "
    )
    public int insert(Map<String,Object> map);

    @Select("SELECT * FROM sitesurvey_task")
    public Map<String,Object> getAll();
}
