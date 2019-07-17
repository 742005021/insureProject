package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.sql.Blob;

@Mapper
@Component
public interface TestMapper {

    @Update("update insurance_item set item_file = #{item_file} where item_id = 6")
    void update(@Param("item_file") Blob blob);

}
