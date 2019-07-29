package org.java.web;

import org.java.dao.LoadResourcesMapper;
import org.java.dao.TestMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class LoadResourcesController {

    @Autowired
    private LoadResourcesService service;

    @Autowired
    private TestMapper mapper;

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private LoadResourcesMapper loadResourcesMapper;
    //加载保险类型
    /*@RequestMapping("/loadInsureType")
    @ResponseBody
    public List<Map<String, Object>> loadInsureType(){
        return service.loadInsureType();
    }*/

    @RequestMapping("/loadInsureItem")
    @ResponseBody
    public List<Map<String, Object>> loadInsureItem() {
        List<Map<String, Object>> maps = service.loadInsureItem();
        return maps;
    }

    @RequestMapping("/file")
    public void file(@RequestParam("myfile") MultipartFile myfile) throws Exception {
        InputStream inputStream = myfile.getInputStream();
        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
        Blob blob = new SerialBlob(bytes);
        mapper.update(blob);
    }


    @RequestMapping("/buy/searchItem/{item_id}/{item_target}")
    public String searchItem(@PathVariable("item_id") Integer item_id, @PathVariable("item_target") String target, HttpServletRequest req) {
        req.setAttribute("item_id", item_id);
        return "/" + target;
    }

    @RequestMapping("/buy/searchTerms/{item_id}")
    @ResponseBody
    public void searchTerms(@PathVariable("item_id") Integer item_id, HttpServletResponse res) throws IOException {
        Object blob = service.searchTerms(item_id);
        byte[] data = (byte[]) blob;
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        OutputStream out = res.getOutputStream();
        byte[] b = new byte[8192];
        while ((len = in.read(b, 0, 8192)) != -1) {
            out.write(b, 0, len);
        }

        out.close();
        in.close();
    }

    @RequestMapping("/searchTable")
    @ResponseBody
    public void searchTable(HttpServletResponse res) throws Exception {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) path = new File("");
        File file = new File(path.getAbsolutePath(), "static/file/zhiyefenlei2014.xlsx");
        res.setContentType("application/ms-download");
        String newName = URLEncoder.encode(file.getName(), "utf-8");
        res.setHeader("Content-disposition", "attachment;fileName=" + newName);
        int bufferSize = 131072;
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buff = ByteBuffer.allocateDirect(786432);
        byte[] byteArr = new byte[bufferSize];
        int nRead, nGet;
        while ((nRead = fileChannel.read(buff)) != -1) {
            if (nRead == 0) {
                continue;
            }
            buff.position(0);
            buff.limit(nRead);
            while (buff.hasRemaining()) {
                nGet = Math.min(buff.remaining(), bufferSize);
                buff.get(byteArr, 0, nGet);
                res.getOutputStream().write(byteArr);
            }
            buff.clear();
        }
        buff.clear();
        fileChannel.close();
        fileInputStream.close();
    }

    /**
     * 一年意外险确认
     *
     * @param item_id
     * @return
     */
    @RequestMapping("/yiNianDetermine/{item_id}")
    public String yiNianDetermine(@PathVariable("item_id") Integer item_id, HttpServletRequest req) throws Exception {
        Map<String, Object> map = service.searchInsureInfo(item_id);
        req.setAttribute("map", map);
        Calendar now1 = Calendar.getInstance();
        int month = now1.get(Calendar.MONTH) + 1;
        req.setAttribute("year", now1.get(Calendar.YEAR));
        req.setAttribute("month", month);
        req.setAttribute("day", now1.get(Calendar.DAY_OF_MONTH));
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            req.setAttribute("endDay", 31);
        } else {
            req.setAttribute("endDay", 30);
        }
        req.setAttribute("item_id", item_id);
        return "/yiNianInfo";
    }

    @RequestMapping("/loadJobs")
    @ResponseBody
    public List<Map<String, Object>> loadJobs() {
        return service.loadJobs();
    }

    @RequestMapping("/loadProfession")
    @ResponseBody
    public List<Map<String, Object>> loadProfession(Integer job_id) {
        return service.loadProfession(job_id);
    }

    @RequestMapping("/toBook")
    public String toBook(String json, HttpServletRequest req, HttpSession ses) {
        // 生成订单
        Map<String, Object> map = service.generateOrders(json);
        req.setAttribute("map", map);
        return "/book";
    }

    @RequestMapping("/searchCacheUserData")
    @ResponseBody
    public String searchCacheUserData() {
        return service.searchCacheUserData();
    }

    @RequestMapping("/secondLogin")
    @ResponseBody
    public String secondLogin(String uname, String pwd) {
        return service.secondLogin(uname, pwd);
    }

    @RequestMapping("/loadUserInfo")
    @ResponseBody
    public Map<String, Object> loadUserInfo() {
        return service.loadUserInfo();
    }

    @RequestMapping("/bookData")
    public String bookData(@RequestParam Map<String, Object> map, HttpServletRequest req) {
        Map<String, Object> data = service.dataProcessing(map);
        req.setAttribute("data", data);
        service.createPolicy(data);
        return "/book_detail";
    }

    //付款加订单下一步和生成保单
    @RequestMapping("/payment/{order_id}/{money}/{starttime}/{insuredIds}/{ccid}")
    public void payment(@PathVariable("order_id") String order_id,
                        @PathVariable("money") double money,
                        @PathVariable("starttime") String starttime,
                        @PathVariable("insuredIds") String insuredIds,
                        @PathVariable("ccid") String ccid,
                        HttpServletRequest req, HttpServletResponse res)throws Exception{
        service.nextOrder(order_id, money, starttime, insuredIds, ccid);
        service.ali(res, req, order_id, money, "一年意外险支付");
    }

    @RequestMapping("/searchVoucher")
    @ResponseBody
    public Map<String, Object> searchVoucher(HttpSession ses){
        Map<String, Object> m = (Map<String, Object>) ses.getAttribute("cust");
        String cust_id = (String) m.get("cust_id");
        return service.searchVoucher(cust_id);
    }


}