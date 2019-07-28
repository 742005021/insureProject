package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Survey_ResultMapper {

    @Insert("INSERT INTO survey_result " +
            "(`id`, `policy_id`, `report_id`, `entrust_id`) " +
            "SELECT UUID(),policy_id,report_id,entrust_id FROM case_entrust " +
            "WHERE entrust_id= #{task_id}")
    int insert(@Param("task_id") String task_id);

    @Select("SELECT insured_name,cr_name,cr_phone,cr_time,s.statu,policy_id,cr_id,cr_policy_id,entrust_id FROM survey_result s,case_report c \n" +
            "WHERE s.report_id=c.cr_id AND s.statu= #{statu}")

    List<Map<String,Object>> getList(@Param("statu") Integer statu);

    @Select("SELECT s.task_id s_id,s.accident_type s_t,s.address s_a,s.file s_f,s.is_true s_i,s.massage s_m,\n" +
            "\tp.task_id p_id,p.accident_type p_t,p.disability_level p_l,p.file p_f,p.is_true p_i,p.massage p_m,\n" +
            "\te.task_id e_id,e.accident_type e_t,e.event_things e_e,e.file e_f,e.is_true e_i,e.massage e_m\n" +
            "FROM case_entrust c \n" +
            "LEFT JOIN sitesurvey_task s ON c.entrust_id=s.task_id\n" +
            "LEFT JOIN peoplesurvey_task p ON c.entrust_id=p.task_id\n" +
            "LEFT JOIN eventsurvey_task e ON c.entrust_id=e.task_id\n" +
            "WHERE c.entrust_id = #{task_id}")
    Map<String,Object> getResult(@Param("task_id") String task_id);//获取勘查结果

    @Update("UPDATE survey_result SET statu=1 WHERE report_id = #{report_id}") //修改审核状态
    int update(@Param("report_id") String report_id);
}
