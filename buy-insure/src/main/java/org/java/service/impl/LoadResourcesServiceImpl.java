package org.java.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.java.config.AlipayConfig;
import org.java.dao.LoadResourcesMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private RedisTemplate<Object, Object> objectTemplate;

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
        if (map == null) {
            String custid = template.opsForValue().get("custid");
            return mapper.loadUserInfo(custid);
        }
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
        result.put("starttime", map.get("starttime"));
        result.put("phone", ((Map<String, Object>) ses.getAttribute("cust")).get("phone"));
        result.put("insuredIds", insuredIds);
        return result;
    }

    @Transactional
    @Override
    public Map<String, Object> generateOrders(String json) {
        Map<String, Object> map = JSON.parseObject(json, Map.class);

        map.put("order_id", UUID.randomUUID().toString());
        if (ses.getAttribute("cust") == null) {
            Map<String, Object> user = mapper.method(template.opsForValue().get("custid"));
            map.put("cust_id", user.get("cust_id"));
            ses.setAttribute("cust", user);
        } else {
            map.put("cust_id", ((Map<String, Object>) ses.getAttribute("cust")).get("cust_id"));
        }
        mapper.generateOrders(map);
        return map;
    }

    @Transactional
    @Override
    public void nextOrder(String order_id, double money, String starttime, String insuredIds, String ccid) {
        //订单下一步
        mapper.nextOrder(6, order_id);
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
            c.add(Calendar.MONTH, 12);
            String insure_etime = format.format(c.getTime());
            param.put("insure_stime", starttime);
            param.put("insure_etime", insure_etime);
            param.put("price", money);
            //生成保单
            mapper.addOrder(param);
            //添加关系
            String[] insured_id = insuredIds.split(";");
            for (String i : insured_id) {
                mapper.addInfo(order_id, i);
            }
            //生成电子信息
            File file = new File(order_id + ".pdf");
            InputStream in = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(in);
            Blob blob = new SerialBlob(bytes);
            mapper.addOrder2(blob, order_id);
            file.delete();
            //是否使用积分
            String[] scoreStatus = ccid.split("@");
            int score = ((int) money / 100) * 20;
            if(scoreStatus[1].equals("no")){
                //没有使用积分
                if(money >= 100){
                    //送积分
                    String cust_id = (String) ((Map<String, Object>) ses.getAttribute("cust")).get("cust_id");
                    mapper.score(score, cust_id);
                }
            } else {
                //使用了积分
                //把优惠券状态改为2
                int cid = Integer.parseInt(scoreStatus[0]);
                mapper.updateStatus(cid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void createPolicy(Map<String, Object> map) {
        String modelPath = "policy.pdf";
        //生成文件新路径
        String newFilePath = map.get("order_id").toString() + ".pdf";
        PdfReader pdfReader = null;
        FileOutputStream out = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;
        try {
            //输出流
            out = new FileOutputStream(newFilePath);
            //读取模板
            pdfReader = new PdfReader(modelPath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(pdfReader, bos);
            //获得表单
            AcroFields form = stamper.getAcroFields();
            //获得表单中的所有表单key
            Set<String> keys = form.getFields().keySet();
            List<Map<String, Object>> peoples = (List<Map<String, Object>>) map.get("peoples");
            Map<String, Object> people = peoples.get(0);
            Map<String, Object> tou = (Map<String, Object>) map.get("tou");
            form.setField("policyid", map.get("order_id").toString().substring(0, 10));
            form.setField("custname", tou.get("cust_name").toString());
            form.setField("custltype", "身份证");
            form.setField("custlno", tou.get("cust_licenseno").toString());
            form.setField("phone", map.get("phone").toString());
            form.setField("price", map.get("money").toString());
            form.setField("count", peoples.size() + "");
            form.setField("insuredname", people.get("insured_name").toString());
            form.setField("ship", "亲属");
            form.setField("insuredltype", "身份证");
            form.setField("insuredlno", people.get("license_no").toString());
            form.setField("job", "一级职业");
            form.setField("people", "法定");
            form.setField("insuredlno", people.get("license_no").toString());
            form.setField("starttime", "2019-7-14");
            form.setField("endtime", "2020-7-14");
            form.setField("date", "2019-7-9");
            //设置好后，pdf不能编辑
            stamper.setFormFlattening(true);
            Document document = new Document();
            PdfCopy pdfCopy = new PdfCopy(document, out);
            document.open();
            PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, 1);
            pdfCopy.addPage(importedPage);
            document.close();
            InputStream in = new FileInputStream(newFilePath);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = in.read(bytes)) != -1) {
                o.write(bytes, 0, len);
            }
            o.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, Object> searchVoucher(String cust_id) {
        List<Map<String, Object>> data = mapper.searchVoucher(cust_id);
        Map<String, Object> m = new HashMap<>();
        if (data == null || data.size() == 0){
            m.put("code", 0);
            m.put("data", null);
        } else {
            m.put("code", 1);
            m.put("data", data);
        }
        return m;
    }

}
