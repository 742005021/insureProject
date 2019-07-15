package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface Peoplesurvey_TaskMapper {

    @Insert("INSERT peoplesurvey_task VALUES("+
            "#{m.task_id},#{m.explains},#{m.emp_id},#{m.accident_type},#{m.disability_level},"+
            "#{m.file},#{m.is_true},#{m.massage} )"
    )
    public int insert(@Param("m") Map<String,Object> map);

    @Select("SELECT * FROM peoplesurvey_task")
    public Map<String,Object> getAll();
}
