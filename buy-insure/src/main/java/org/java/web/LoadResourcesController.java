package org.java.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.io.FileUtils;
import org.java.dao.TestMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoadResourcesController {

    @Autowired
    private LoadResourcesService service;

    @Autowired
    private TestMapper mapper;

    @Autowired
    private ServletContext context;
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
        if(!path.exists()) path = new File("");
        File file = new File(path.getAbsolutePath(),"static/file/zhiyefenlei2014.xlsx");
        //String filePath = context.getRealPath("file/zhiyefenlei2014.xlsx");
        //File file = new File(filePath);
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
     * @param item_id
     * @return
     */
    @RequestMapping("/yiNianDetermine/{item_id}")
    public String yiNianDetermine(@PathVariable("item_id") Integer item_id,HttpServletRequest req) throws Exception{
        Map<String, Object> map = service.searchInsureInfo(item_id);
        req.setAttribute("map", map);
        /*System.out.println(map);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse("2019-7-16");
        System.out.println(format.format(parse)+"@@@@@@@@@@@@@@@@");*/
        Calendar now1 = Calendar.getInstance();
        int month = now1.get(Calendar.MONTH) + 1;
        req.setAttribute("year", now1.get(Calendar.YEAR));
        req.setAttribute("month", month);
        req.setAttribute("day", now1.get(Calendar.DAY_OF_MONTH) + 5);
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            req.setAttribute("endDay", 31);
        } else {
            req.setAttribute("endDay", 30);
        }
        return "/yiNianInfo";
    }

    @RequestMapping("/loadJobs")
    @ResponseBody
    public List<Map<String, Object>> loadJobs(){
        return service.loadJobs();
    }

    @RequestMapping("/loadProfession")
    @ResponseBody
    public List<Map<String, Object>> loadProfession(Integer job_id){
        return service.loadProfession(job_id);
    }

    @RequestMapping("/toBook")
    public String toBook(String json, HttpServletRequest req) throws IOException {
        JsonNode node = new ObjectMapper().readTree(json);
        Map<String, Object> map = new HashMap<>();
        map.put("ren", node.get("ren"));
        map.put("jieguo", node.get("jieguo"));
        map.put("max_people", node.get("max_people"));
        map.put("yiwai", node.get("yiwai"));
        map.put("yiliao", node.get("yiliao"));
        map.put("zhuyuan", node.get("zhuyuan"));
        map.put("jiuhuiche", node.get("jiuhuiche"));
        map.put("feiji", node.get("feiji"));
        map.put("huoche", node.get("huoche"));
        map.put("lunchuan", node.get("lunchuan"));
        map.put("qiche", node.get("qiche"));
        req.setAttribute("map", map);
        return "/book";
    }

}