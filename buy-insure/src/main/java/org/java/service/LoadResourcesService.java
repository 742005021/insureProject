package org.java.service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LoadResourcesService {
    List<Map<String, Object>> loadInsureType();

    List<Map<String, Object>> loadInsureItem();

    Object searchTerms(Integer id);

    Map<String, Object> searchInsureInfo(Integer item_id);

    List<Map<String, Object>> loadJobs();

    List<Map<String, Object>> loadProfession(Integer job_id);

    String searchCacheUserData();

    String secondLogin(String uname, String pwd);

    Map<String, Object> loadUserInfo();

    Map<String, Object> dataProcessing(Map<String, Object> map);

    //生成订单
    Map<String, Object> generateOrders(String json);

    //订单下一步
    void nextOrder(String order_id, double money,String starttime);

    void ali(HttpServletResponse res, HttpServletRequest req, String order_id, double money, String order_name) throws IOException, AlipayApiException;
}
