package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Exit_PolicyMapper {

    @Select("select * from exitpolicy")
    List<Map<String,Object>> getExitPolicy();



    @Select("select insure_stime,a.* from exitpolicy a,policy b where eid=#{eid} and a.policyid=b.policy_id")
    Map<String,Object> getOnePolicy(@Param("eid") int eid);


   @Select("select * from exitpolicy where exitstatu=#{statu}")
    List<Map<String,Object>> getPersonPolicy(@Param("statu") int statu);


    @Update("update exitpolicy set exitstatu=exitstatu+1 where eid=${eid}")
    int updateStatu(@Param("eid") int eid);

}
