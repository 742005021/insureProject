package org.java.service;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Map<String,Object>> getMessage(String custid);
    int updateStatu(int mid);
    int addMessage(Map<String,Object> map);
}
