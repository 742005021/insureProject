package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface PolicyMapper {


    @Select("select c.item_name,a.price,b.order_statu,a.insure_stime stime,insure_etime etime,a.policy_id,c.item_target,c.item_id,\n" +
            "(select insured_name from insuredinfo where insured_id\n" +
            "\n" +
            " in(select insured_id from policy_insured_relationship where policy_id=a.policy_id)) insuredname\n" +
            " \n" +
            " from policy a,policy_order b,insurance_item c\n" +
            " \n" +
            " where a.policy_id=b.order_id and b.item_id=c.item_id \n" +
            "and a.cust_id=#{custid} and b.order_statu like #{statu} ")
    List<Map<String,Object>> getPolicy(@Param("custid") String custid, @Param("statu") String statu);
}
