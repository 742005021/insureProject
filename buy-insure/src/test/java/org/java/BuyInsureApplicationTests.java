package org.java;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.java.service.PolicyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyInsureApplicationTests {


    @Autowired
    private PolicyService service;

    @Test
    public void contextLoads() {
    }

@Test
    public void addOrder(){
        String modelPath = "policy.pdf";
        //生成文件新路径
        String newFilePath = "20170717.pdf";

        PdfReader pdfReader = null;
        FileOutputStream out = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper = null;

        try{
            //输出流
            out = new FileOutputStream(newFilePath);
            //读取模板
            pdfReader = new PdfReader(modelPath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(pdfReader,bos);
            //获得表单
            AcroFields form = stamper.getAcroFields();

            //获得表单中的所有表单key
            Set<String> keys = form.getFields().keySet();





            form.setField("policyid", "20170714");
            form.setField("custname", "杨鹏飞");
            form.setField("custltype", "身份证");
            form.setField("custlno", "45454654875454445");
            form.setField("phone","17343232980");
            form.setField("price", "254");
            form.setField("count", "1");
            form.setField("insuredname", "李四");
            form.setField("ship", "亲属");
            form.setField("insuredltype", "身份证");
            form.setField("insuredlno", "4545454512");
            form.setField("job", "三级职业");
            form.setField("people", "法定");

            form.setField("starttime", "2019-5-4");
            form.setField("endtime", "2020-5-4");

            form.setField("date", "2019-5-1");

            //设置好后，pdf不能编辑
            stamper.setFormFlattening(true);

            Document document = new Document();

            PdfCopy pdfCopy = new PdfCopy(document,out);

            document.open();

            PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader,1);

            pdfCopy.addPage(importedPage);

            document.close();

            InputStream in = new FileInputStream(newFilePath);

            byte[] bytes = FileCopyUtils.copyToByteArray(in);
            Blob blob=new SerialBlob(bytes);


            service.addOrder(blob, "20170717");

            System.out.println("完成");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
