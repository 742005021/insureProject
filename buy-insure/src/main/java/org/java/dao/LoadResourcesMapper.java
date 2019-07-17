package org.java.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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

    @Select("select item_id,item_name,start_deadline,end_deadline, max_people,start_age,end_age,base_money from insurance_item where item_id = #{item_id}")
    Map<String, Object> searchInsureInfo(@Param("item_id") Integer item_id);

    @Select("select * from job")
    List<Map<String, Object>> loadJobs();

    @Select("select * from profession where job_id = #{job_id}")
    List<Map<String, Object>> loadProfession(@Param("job_id") Integer job_id);
}
