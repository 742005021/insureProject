package org.java.service;

import java.util.Map;

public interface Case_EntrusService {

    int insert(Map<String, Object> map);

    boolean checkTaskStatu(String task_id);
}
