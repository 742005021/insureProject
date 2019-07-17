package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface Case_EntrustMapper {

    @Insert("INSERT case_entrust VALUES (#{m.entrust_id},#{m.policy_id},#{m.report_id},#{m.sitesurvey_id},#{m.peoplesurvey_id},#{m.eventsurvey_id}) ")
    public int insert(@Param("m")Map<String,Object> map);

    @Select("SELECT * FROM case_entrust")
    public Map<String,Object> getAll();
}
