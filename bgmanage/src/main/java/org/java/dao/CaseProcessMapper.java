package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface CaseProcessMapper {
    //根据订单id，显示详情数据
    @Select("select * from case_report where cr_id=#{cr_id}")
    public Map<String,Object> showCaseDetail(@Param("cr_id") String cr_id);

    // 根据流程实例的id，查询对应的业务数据
    @Select("select * from case_report where process_instance_id=#{instanceId}")
    public Map<String,Object> findByProcessInstanceId(@Param("instanceId") String instanceId);

    //添加审核记录
    @Insert("")
    public void submitAudit(Map<String,Object> map);

    //查询所有流程实例
    @Select("select * from case_report")
    public Map<String,Object> getAll();
}
