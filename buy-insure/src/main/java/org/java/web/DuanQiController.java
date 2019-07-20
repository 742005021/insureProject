package org.java.web;

import com.netflix.discovery.converters.Auto;
import org.java.service.DuanQiService;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

        // 计算日期
        Calendar c = Calendar.getInstance();
        //现在时间
        Date now = new Date();
        c.setTime(now);
        c.add(Calendar.MONTH, 6);
        //180天后的时间
        Date time = c.getTime();
        c.setTime(now);
        Map<String, Object> date = new HashMap<>();
        date.put("nowYear", c.get(Calendar.YEAR));
        date.put("nowMonth", c.get(Calendar.MONTH) + 1);
        date.put("nowDay", c.get(Calendar.DAY_OF_MONTH) + 1);
        c.setTime(time);
        date.put("desYear", c.get(Calendar.YEAR));
        date.put("desMonth", c.get(Calendar.MONTH) + 1);
        date.put("desDay", c.get(Calendar.DAY_OF_MONTH) + 1);
        map.put("date", date);
        req.setAttribute("map", map);
        return "/duanqi/duanQiInfo";
    }

}
