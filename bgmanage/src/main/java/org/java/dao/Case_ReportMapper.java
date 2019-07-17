package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Case_ReportMapper {

    @Insert("INSERT case_report VALUES ("+
            "#{m.cr_id},#{m.insured_name},#{m.insured_sex},#{m.insured_crad_id},#{m.insured_age},"+
            "#{m.cr_enterprise},#{m.insured_profession},#{m.accident_date},#{m.accident_cause},#{m.accident_address},"+
            "#{m.situation},#{m.things},#{m.autopsy},#{m.deathcertificate},#{m.cr_name},"+
            "#{m.cr_phone},#{m.cr_relationship},#{m.cr_policy_id},now(),#{m.notice_address},"+
            "#{m.post_code},#{m.payment},#{m.bank_name},#{m.bank_no},0," +
            "#{m.process_instance_id} )"
    )
    public int insert(@Param("m") Map<String,Object> map);
    
    @Select("SELECT * FROM case_report")
    public Map<String,Object> getAll();

    @Select("SELECT * FROM case_report WHERE statu = #{statu}")
    List<Map<String,Object>> getList_ByStatu(@Param("statu") int statu);

    @Select("SELECT * FROM case_report where cr_id=#{cr_id}")
    Map<String,Object> getReport_ById(@Param("cr_id") String cr_id);

    @Update("update case_report set statu = #{statu} where cr_id=#{cr_id} ")
    int update_Statu(@Param("cr_id") String cr_id,@Param("statu") int statu);
}
