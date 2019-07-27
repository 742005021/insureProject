package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface Exit_PolicyMapper {

    @Select("select * from exitpolicy ")
    List<Map<String,Object>> getExitPolicy();



    @Select("select insure_stime,a.* ,price from exitpolicy a,policy b where eid=#{eid} and a.policyid=b.policy_id")
    Map<String,Object> getOnePolicy(@Param("eid") int eid);


   @Select("select * from exitpolicy where exitstatu=#{statu}")
    List<Map<String,Object>> getPersonPolicy(@Param("statu") int statu);


    @Update("update exitpolicy set exitstatu=exitstatu+1 where eid=${eid}")
    int updateStatu(@Param("eid") int eid);


    @Insert("insert exitemp values(#{map.eid},#{map.emp_id},#{map.audit})")
    int addAudit(@Param("map") Map<String, Object> map);

    @Insert("insert into message values(null,#{map.custid},#{map.title},#{map.content},#{map.date},default)")
    int addMessage(@Param("map")Map<String,Object> map);

    @Update("update policy_order set order_statu=7 where order_id=#{order_id}")
     int updateOrderDate(@Param("order_id")String orderid);
}
