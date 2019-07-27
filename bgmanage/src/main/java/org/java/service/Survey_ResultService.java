package org.java.service;

import java.util.List;
import java.util.Map;

public interface Survey_ResultService {

    int insert(String task_id);

    List<Map<String,Object>> getList(Integer statu);

    Map<String,Object> getResult(String task_id);

    int submitVerify(Map<String,Object> map);
}
