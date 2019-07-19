package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Sitesurvey_TaskMapper {

    @Insert("INSERT sitesurvey_task VALUES ("+
            "#{m.task_id},#{m.explains},#{m.emp_id},#{m.accident_type},#{m.address},"+
            "#{m.file},#{m.is_ture},#{m.massage},#{m.statu} ) "
    )
    int insert(@Param("m") Map<String,Object> map);

    @Insert("INSERT sitesurvey_task VALUES ("+
            "#{task_id},#{explains},#{emp_id},null,null,"+
            "null,null,null,0 ) "
    )
    int insert2(@Param("task_id") String task_id,@Param("emp_id") int emp_id,@Param("explains") String explains);

    @Select("SELECT * FROM sitesurvey_task")
    public Map<String,Object> getAll();

    @Select("SELECT * FROM sitesurvey_task t,case_entrust e WHERE t.task_id=e.entrust_id " +
            "AND emp_id=#{emp_id} "+
            "AND statu=#{statu} ")
    List<Map<String,Object>> getTasks(@Param("emp_id") Integer emp_id, @Param("statu") Integer statu);

    @Select("SELECT * FROM sitesurvey_task WHERE task_id=#{task_id}")
    Map<String,Object> getTaskById(@Param("task_id") String task_id);

    @Update("DELETE FROM sitesurvey_task set accident_type=#{m.accident_type},address=#{m.address},file=#{m.file},is_ture=#{m.isture},massage=#{m.massage},statu=1 " +
            "WHERE task_id = #{m.task_id}")
    int update(@Param("m") Map<String,Object> map);
}
