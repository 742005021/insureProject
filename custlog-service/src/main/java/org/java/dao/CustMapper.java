package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
@Component
public interface CustMapper {

    @Select("select * from cust_account where username=#{uname} and password=#{pwd}")
    Map<String,Object> custLogin(@Param("uname") String username, @Param("pwd") String password);

    @Insert("insert cust_account values(uuid(),#{m.uname},#{m.pwd},#{m.phone})")
    int addCustAccount(@Param("m") Map<String,Object> map);

    @Select("select cust_id from cust_account where username=#{uname}")
    String check(@Param("uname") String uname);
}
