package org.java.web;

import com.netflix.discovery.converters.Auto;
import org.java.service.DuanQiService;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加保单
 */
@Controller
public class DuanQiController {

    @Autowired
    private LoadResourcesService loadResourcesService;

    @Autowired
    private DuanQiService service;


    /**
     * 短期确认
     */
    @RequestMapping("/duanqidetermine/{item_id}")
    public String duanQiDetermine(@PathVariable("item_id") Integer item_id, HttpServletRequest req) {
        Map<String, Object> map = loadResourcesService.searchInsureInfo(item_id);
        req.setAttribute("map", map);
        return "/duanqi/duanQiInfo";
    }

    @RequestMapping("/toDuanQiBook")
    public String toDuanQiBook(String json, HttpServletRequest req){
        //生成订单
        Map<String, Object> map = service.toDuanQiBook(json);
        req.setAttribute("map", map);
        return "/duanqi/duanQiBook";
    }

    @RequestMapping("/duanQiBookData")
    public String duanQiBookData(@RequestParam Map<String, Object> map, HttpServletRequest req){
        Map<String, Object> result = service.dataProcessing(map);
        req.setAttribute("data", result);
        loadResourcesService.createPolicy(result);
        return "/duanqi/duanQiBook_detail";
    }

    @RequestMapping("/duanQiPayment/{order_id}/{money}/{starttime}/{insuredIds}/{day}")
    public void payment(@PathVariable("order_id") String order_id,
                        @PathVariable("money") double money,
                        @PathVariable("starttime") String starttime,
                        @PathVariable("insuredIds") String insuredIds,
                        @PathVariable("day") Integer day,
                        HttpServletRequest req, HttpServletResponse res)throws Exception{
        service.nextOrder(order_id, money, starttime, insuredIds, day);
        loadResourcesService.ali(res, req, order_id, money, "短期意外险支付");
    }

}
