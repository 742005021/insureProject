package org.java.service;

import java.util.Map;

public interface DuanQiService {

    Map<String, Object> toDuanQiBook(String json);

    Map<String, Object> dataProcessing(Map<String, Object> map);

    void nextOrder(String order_id, double money,String starttime, String insuredIds, Integer day, String ccid);

}
