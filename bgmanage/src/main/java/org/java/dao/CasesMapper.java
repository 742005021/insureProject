package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface CasesMapper {
    @Insert("INSERT cases VALUES ("+
            "#{m.c_id},#{m.policy_id},#{m.insured_name},#{m.insured_sex},#{m.insured_crad_id}"+
            ",#{m.insured_age},#{m.insured_profession},#{m.accident_date},#{m.accident_cause},#{m.accident_address}"+
            ",#{m.situation},#{m.event_things},#{m.deathcertificate},#{m.disability_level},#{m.notice_address}"+
            ",#{m.post_code},#{m.payment},#{m.bank_name},#{m.bank_no},#{m.liable_emp}"+
            ",#{m.time}         )"
    )
    public int insert(@Param("m")Map<String,Object> map);

    @Insert("INSERT INTO cases" +
            "(  c_id,policy_id,insured_name,insured_sex,insured_crad_id," +
            "   insured_age,insured_profession,accident_date,accident_cause,accident_address," +
            "   situation,event_things,deathcertificate,disability_level,notice_address," +
            "   post_code,payment,bank_name,bank_no,liable_emp," +
            "   `time`)" +
            "SELECT a.cr_id,a.cr_policy_id,a.insured_name,a.insured_sex,a.insured_crad_id," +
            "   a.insured_age,a.insured_profession,a.accident_date,a.accident_cause,a.accident_address, " +
            "   a.situation,e.event_things,a.deathcertificate,d.disability_level,a.notice_address, " +
            "   a.post_code,a.payment,a.bank_name,a.bank_no,NULL,NOW() " +
            "FROM case_report a " +
            "LEFT JOIN case_entrust b ON a.cr_id=b.report_id " +
            "LEFT JOIN sitesurvey_task c ON b.entrust_id=c.task_id " +
            "LEFT JOIN peoplesurvey_task d ON b.entrust_id=d.task_id " +
            "LEFT JOIN eventsurvey_task e ON b.entrust_id=e.task_id " +
            "WHERE a.cr_id= #{report_id}"
    )
    int insert2(@Param("report_id") String report_id);

    @Select("SELECT * FROM cases")
    public Map<String,Object> getAll();

    @Select("SELECT * FROM cases c " +
            "LEFT JOIN emp_account e ON c.liable_emp=e.emp_id " +
            "WHERE statu= #{statu}")
    List<Map<String,Object>> getList(@Param("statu") Integer statu);

    @Select("SELECT * FROM cases WHERE statu = 1 and liable_emp= #{emp_id}")
    Map<String,Object> getCasesByLiable_emp(@Param("emp_id") Integer emp_id);//我接取的案件

    @Select("SELECT count(*) FROM cases WHERE statu = 1 and liable_emp= #{emp_id}")
    Object getMyCasesCount(@Param("emp_id") Integer emp_id);

    @Update("UPDATE cases SET liable_emp=${m.emp_id},statu=1 WHERE id=${m.id}")
    int letme(@Param("m") Map<String,Object> map);

    @Update("UPDATE cases SET step=#{m.step} WHERE id=${m.id}")
    int nextStep(@Param("m") Map<String,Object> map);

    @Update("UPDATE cases SET statu=2,step=#{m.step},endtime=NOW() WHERE id=${m.id}")
    int complete(@Param("m") Map<String, Object> map);

    /* 财务部分 */
    //添加财务拨款信息
    @Insert("INSERT INTO finance (cases_id,massage,statu) " +
            "SELECT cases.id,#{m.massage},0 FROM cases WHERE cases.id=#{m.id} ")
    int insertFinance(@Param("m") Map<String,Object> map);

    //查看拨款信息列表
    @Select("SELECT * FROM finance WHERE statu = #{statu}")
    List<Map<String,Object>> getFinances(@Param("statu") int statu);

    //拨款完成
    @Update("UPDATE finance SET statu=1 WHERE cases_id=#{cases_id}")
    int financeComplete(@Param("cases_id") int id);

    //根据id取得状态
    @Select("SELECT statu FROM finance WHERE cases_id= #{cases_id}")
    Map<String,Object> getFinanceStatu(@Param("cases_id") int id);
}
