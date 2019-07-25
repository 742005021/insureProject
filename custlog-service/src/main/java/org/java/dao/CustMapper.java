package org.java.dao;

import org.apache.ibatis.annotations.*;
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

    @Update("update cust_account set password=#{pwd} where cust_id=#{custid}")
    int editPwd(@Param("pwd")String pwd,@Param("custid")String custid);

    @Insert("insert into custinfo(cust_id) values ((select cust_id from cust_account where username=#{m.uname} and password=#{m.pwd})) ")
    int addCustInfoId(@Param("m") Map<String,Object> map);

    @Select("select cust_score from custinfo where cust_id=#{custid}")
    int getCustScore(@Param("custid") String custid);

    @Update("update custinfo set cust_score =#{score} where cust_id=#{custid}")
    int updateScore(@Param("custid")String custid,@Param("score") int score);

    @Select("select * from custinfo where cust_id=#{custid}")
    Map<String,Object> getcustinfo(@Param("custid") String custid);
}
