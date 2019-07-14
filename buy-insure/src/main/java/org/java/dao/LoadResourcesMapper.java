package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface LoadResourcesMapper {

    @Select("select * from insuretype")
    List<Map<String, Object>> loadInsureType();

    @Select("select * from insurance_item")
    List<Map<String, Object>> loadInsureItem();

    @Select("select item_file from insurance_item where item_id = #{item_id}")
    Object searchTerms(@Param("item_id") Integer id);
}
