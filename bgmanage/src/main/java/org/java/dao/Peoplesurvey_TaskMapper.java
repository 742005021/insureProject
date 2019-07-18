package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Peoplesurvey_TaskMapper {

    @Insert("INSERT peoplesurvey_task VALUES("+
            "#{m.task_id},#{m.explains},#{m.emp_id},#{m.accident_type},#{m.disability_level},"+
            "#{m.file},#{m.is_true},#{m.massage},#{m.statu} )"
    )
    public int insert(@Param("m") Map<String,Object> map);

    @Insert("INSERT peoplesurvey_task VALUES("+
            "#{task_id},null,#{emp_id},null,null,"+
            "null,null,null,0 )"
    )
    public int insert2(@Param("task_id") String task_id,@Param("emp_id") int emp_id);

    @Select("SELECT * FROM peoplesurvey_task")
    public Map<String,Object> getAll();

    @Select("SELECT * FROM peoplesurvey_task t,case_entrust e WHERE t.task_id=e.entrust_id " +
            "AND emp_id=#{emp_id} "+
            "AND statu=#{statu} ")
    List<Map<String,Object>> getTasks(@Param("emp_id") Integer emp_id, @Param("statu") Integer statu);
}
