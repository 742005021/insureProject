package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface PolicyMapper {

    @Select("SELECT json FROM policy_order WHERE order_id= #{policy_id}")
    String getPolicy_Info(@Param("policy_id") String policy_Id);
}
