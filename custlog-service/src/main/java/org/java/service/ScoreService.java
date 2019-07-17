package org.java.service;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    int addInfo(Map<String,Object> m);
    List<Map<String,Object>> showScore(String custid);
}
