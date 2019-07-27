package org.java.service.impl;

import org.java.dao.Case_ReportMapper;
import org.java.dao.CasesMapper;
import org.java.dao.Survey_ResultMapper;
import org.java.service.Survey_ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class Survey_ResultServiceImpl implements Survey_ResultService {

    @Autowired
    private Survey_ResultMapper survey_resultMapper;

    @Autowired
    private Case_ReportMapper case_reportMapper;

    @Autowired
    private CasesMapper casesMapper;

    @Override
    public int insert(String task_id) {
        return survey_resultMapper.insert(task_id);
    }

    @Override
    public List<Map<String, Object>> getList(Integer statu) {
        return survey_resultMapper.getList(statu);
    }

    @Override
    public Map<String, Object> getResult(String task_id) {
        Map<String, Object> map = survey_resultMapper.getResult(task_id);
        //空值判断
        if (map.get("s_i")==null){
            map.put("s_i","");
        }
        if (map.get("p_i")==null){
            map.put("p_i","");
        }
        if (map.get("e_i")==null){
            map.put("e_i","");
        }
        return map;
    }

    @Override
    public int submitVerify(Map<String, Object> map) {
        boolean b=Boolean.valueOf(map.get("is_pass").toString());
        String report_id=map.get("report_id").toString();
        survey_resultMapper.update(report_id);
        if(b){//通过
            case_reportMapper.update_Statu(report_id,1);
            //将数据添加到案件表
            casesMapper.insert2(report_id);
        }else{//不通过
            case_reportMapper.update_Statu(report_id,2);
        }
        //添加立案结果

        return 1;
    }
}
