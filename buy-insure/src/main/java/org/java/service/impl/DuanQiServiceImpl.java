package org.java.service.impl;

import org.java.dao.DuanQiMapper;
import org.java.dao.LoadResourcesMapper;
import org.java.service.DuanQiService;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DuanQiServiceImpl implements DuanQiService {

    @Autowired
    private DuanQiMapper mapper;

    @Autowired
    private LoadResourcesService otherService;

    @Autowired
    private LoadResourcesMapper otherMapper;

    @Autowired
    private HttpSession ses;

    @Transactional
    @Override
    public Map<String, Object> toDuanQiBook(String json) {
        return otherService.generateOrders(json);
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
            otherMapper.addTou(tou);
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
            otherMapper.updateTou(tou);
        }
        String insuredIds = "";
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
        //被保人信息
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
            String insured_id = UUID.randomUUID().toString();
            people.put("insured_id", insured_id);
            insuredIds += insured_id + ";";
            peoples.add(people);
        }
        for (int i = 0; i < peoples.size(); i++) {
            otherMapper.addInsuredInfo(peoples.get(i));
        }
        //订单下一步
        String order_id = (String) map.get("order_id");
        otherMapper.nextOrder(3, order_id);

        //返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("money", map.get("money"));
        result.put("tou", tou);
        result.put("peoples", peoples);
        result.put("order_id", map.get("order_id"));
        result.put("starttime", map.get("starttime"));
        result.put("phone", ((Map<String, Object>) ses.getAttribute("cust")).get("phone"));
        result.put("insuredIds", insuredIds);
        result.put("day", map.get("day"));
        return result;
    }

    @Transactional
    @Override
    public void nextOrder(String order_id, double money, String starttime, String insuredIds, Integer day, String ccid) {
        //订单下一步
        otherMapper.nextOrder(6, order_id);
        //生成保单
        Map<String, Object> cust = (Map<String, Object>) ses.getAttribute("cust");
        //需要的参数
        Map<String, Object> param = new HashMap<>();
        param.put("policy_id", order_id);
        param.put("cust_id", cust.get("cust_id"));
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date insure_stime = format.parse(starttime);
            c.setTime(insure_stime);
            c.add(Calendar.DAY_OF_MONTH, day);
            String insure_etime = format.format(c.getTime());
            param.put("insure_stime", starttime);
            param.put("insure_etime", insure_etime);
            param.put("price", money);
            //生成保单
            otherMapper.addOrder(param);
            //添加关系
            String[] insured_id = insuredIds.split(";");
            for (String i : insured_id) {
                otherMapper.addInfo(order_id, i);
            }
            //生成电子信息
            File file = new File(order_id + ".pdf");
            InputStream in = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(in);
            Blob blob = new SerialBlob(bytes);
            otherMapper.addOrder2(blob, order_id);
            file.delete();
            //是否使用积分
            String[] scoreStatus = ccid.split("@");
            int score = ((int) money / 100) * 20;
            if(scoreStatus[1].equals("no")){
                //没有使用积分
                if(money >= 100){
                    //送积分
                    String cust_id = (String) ((Map<String, Object>) ses.getAttribute("cust")).get("cust_id");
                    otherMapper.score(score, cust_id);
                }
            } else {
                //使用了积分
                //把优惠券状态改为2
                int cid = Integer.parseInt(scoreStatus[0]);
                otherMapper.updateStatus(cid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
