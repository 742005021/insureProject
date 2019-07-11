package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface EmpMapper {

    @Select("SELECT * FROM emp_account WHERE emp_username = #{uname} AND emp_password= #{pwd}")
    Map<String,Object> empLogin(@Param("uname") String username, @Param("pwd") String password);

    @Select("SELECT emp_id FROM emp_account WHERE emp_username = #{uname}")
    String empCheck(@Param("uname") String uname);
}
