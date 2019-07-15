package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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

    @Select("SELECT * FROM cases")
    public Map<String,Object> getAll();
}
