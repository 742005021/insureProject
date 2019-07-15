package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface EmpMapper {

    @Select("SELECT * FROM emp_account WHERE emp_username = #{uname} AND emp_password= #{pwd}")
    Map<String,Object> empLogin(@Param("uname") String username, @Param("pwd") String password);

    @Select("SELECT emp_id FROM emp_account WHERE emp_username = #{uname}")
    String empCheck(@Param("uname") String uname);

    @Update("UPDATE emp_account SET lastlogintime=now() WHERE emp_username = #{uname} AND emp_password= #{pwd}")
    int updateLogintime(@Param("uname") String username, @Param("pwd") String password);

    @Select("SELECT emp_id,emp_name FROM employee WHERE emp_id IN(SELECT emp_id FROM employee_role WHERE rid=1)")
    List<Map<String,Object>> getAllSurvey_Emp();
}
