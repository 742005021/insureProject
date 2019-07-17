package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface Eventsurvey_TaskMapper {
    @Insert("INSERT eventsurvey_task VALUES(" +
            "#{m.task_id},#{m.explains},#{m.emp_id},#{m.accident_type},#{m.event_things}," +
            "#{m.file},#{m.is_true},#{m.massage} )"
    )
    public int insert(@Param("m") Map<String,Object> map);

    @Insert("INSERT eventsurvey_task VALUES(" +
            "#{task_id},null,#{emp_id},null,null," +
            "null,null,null )")
    public int insert2(@Param("task_id") String task_id,@Param("emp_id") int emp_id);

    @Select("SELECT * FROM eventsurvey_task")
    public Map<String,Object> getAll();
}
