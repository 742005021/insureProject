package org.java.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Survey_ResultService {

    int insert(String task_id);

    List<Map<String,Object>> getList(@Param("statu") Integer statu);

    Map<String,Object> getResult(String task_id);

    int submitVerify(Map<String,Object> map);
}
