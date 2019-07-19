package org.java.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.java.config.AlipayConfig;
import org.java.dao.LoadResourcesMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoadResourcesServiceImpl implements LoadResourcesService {

    @Autowired
    private LoadResourcesMapper mapper;

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private HttpSession ses;
    @Autowired
    private RedisTemplate<Object,Object> objectTemplate;


    @Override
    public List<Map<String, Object>> loadInsureType() {
        return mapper.loadInsureType();
    }

    @Override
    public List<Map<String, Object>> loadInsureItem() {
        return mapper.loadInsureItem();
    }

    @Override
    public Object searchTerms(Integer id) {
        return mapper.searchTerms(id);
    }

    @Override
    public Map<String, Object> searchInsureInfo(Integer item_id) {
        return mapper.searchInsureInfo(item_id);
    }

    @Override
    public List<Map<String, Object>> loadJobs() {
        return mapper.loadJobs();
    }

    @Override
    public List<Map<String, Object>> loadProfession(Integer job_id) {
        return mapper.loadProfession(job_id);
    }

    @Override
    public String searchCacheUserData() {
        String custid = template.opsForValue().get("custid");
        if (custid != null && !"".equals(custid)) {
            return "yes";
        }
        return "no";
    }

    @Override
    public String secondLogin(String uname, String pwd) {
        Map<String, Object> map = mapper.secondLogin(uname, pwd);
        if (map != null) {
            ses.setAttribute("cust", map);
            String custid = (String) map.get("cust_id");
            template.opsForValue().set("custid", custid, 20, TimeUnit.MINUTES);
            objectTemplate.opsForHash().put("cust", "map", map);
            return "yes";
        }
        return "no";
    }

    @Override
    public Map<String, Object> loadUserInfo() {
        Map<String, Object> map = (Map<String, Object>) ses.getAttribute("cust");
        String custid = (String) map.get("cust_id");
        return mapper.loadUserInfo(custid);
    }

    @Transactional
    @Override
    public Map<String, Object> dataProcessing(Map<String, Object> map) {
        //处理投保人
        Map<String, Object> tou = new HashMap<>();
        String cust_id = (String) map.get("cust_id");
        //添加投保人信息
        if (cust_id == null || "".equals(cust_id)) {
            cust_id = (String) ((Map<String, Object>) ses.getAttribute("cust")).get("cust_id");
            tou.put("cust_id", cust_id);
            tou.put("cust_name", map.get("cust_name"));
            tou.put("cust_sex", map.get("sex"));
            tou.put("license_id", map.get("license"));
            tou.put("cust_licenseno", map.get("cust_licenseno"));
            tou.put("cust_bir", map.get("cust_bir"));
            tou.put("cust_email", map.get("cust_email"));
            tou.put("cust_address", map.get("cust_address"));
            mapper.addTou(tou);
            //修改投保人信息
        } else {
            tou.put("cust_id", cust_id);
            tou.put("cust_name", map.get("cust_name"));
            tou.put("cust_sex", map.get("sex"));
            tou.put("license_id", map.get("license"));
            tou.put("cust_licenseno", map.get("cust_licenseno"));
            tou.put("cust_bir", map.get("cust_bir"));
            tou.put("cust_email", map.get("cust_email"));
            tou.put("cust_address", map.get("cust_address"));
            mapper.updateTou(tou);
        }
        //处理被保人
        int max_people = 0;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (key.startsWith("insured_name")) {
                Integer number = Integer.parseInt(key.substring(key.length() - 1));
                if (number > max_people) {
                    max_people = number;
                }
            }
        }
        List<Map<String, Object>> peoples = new ArrayList<>();
        for (int i = 1; i <= max_people; i++) {
            Map<String, Object> people = new HashMap<>();
            people.put("insured_name", map.get("insured_name" + i));
            people.put("licnese_id", map.get("licnese_id" + i));
            people.put("license_no", map.get("license_no" + i));
            people.put("birthday", map.get("birthday" + i));
            people.put("insured_sex", map.get("insured_sex" + i));
            people.put("address", map.get("address" + i));
            people.put("cust_id", cust_id);
            peoples.add(people);
        }
        for (int i = 0; i < peoples.size(); i++) {
            mapper.addInsuredInfo(peoples.get(i));
        }
        //订单下一步
        String order_id = (String) map.get("order_id");
        mapper.nextOrder(3, order_id);

        //返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("money", map.get("money"));
        result.put("tou", tou);
        result.put("peoples", peoples);
        result.put("order_id", map.get("order_id"));
        result.put("phone", ((Map<String, Object>) ses.getAttribute("cust")).get("phone"));
        return result;
    }

    @Transactional
    @Override
    public void generateOrders(Map<String, Object> map) {
        mapper.generateOrders(map);
    }

    @Transactional
    @Override
    public void nextOrder(String order_id) {
        mapper.nextOrder(3, order_id);
    }

    @Override
    public void ali(HttpServletResponse response, HttpServletRequest request, String order_id, double money, String order_name) throws IOException, AlipayApiException {
        //设置编码
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = new String(order_id);
        //付款金额，从前台获取，必填
        String total_amount = new String(money + "");
        //订单名称，必填
        String subject = new String(order_name);
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(aliPayRequest).getBody();
        //输出
        out.println(result);
    }

}
