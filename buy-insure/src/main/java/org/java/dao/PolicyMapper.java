package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.sql.Blob;

@Component
@Mapper
public interface PolicyMapper {

    @Update("update policy set policyorder=#{policyorder} where policy_id=#{policyid}")
    int addOrder(@Param("policyorder") Blob blob, @Param("policyid") String policyid);
}
