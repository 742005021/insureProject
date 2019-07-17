package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    int insert(@Param("m") Map<String,Object> map);

    @Insert("INSERT sitesurvey_task VALUES ("+
            "#{task_id},null,#{emp_id},null,null,"+
            "null,null,null ) "
    )
    int insert2(@Param("task_id") String task_id,@Param("emp_id") int emp_id);

    @Select("SELECT * FROM sitesurvey_task")
    public Map<String,Object> getAll();
}
