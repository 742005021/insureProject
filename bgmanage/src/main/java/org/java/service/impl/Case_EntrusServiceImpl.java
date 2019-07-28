package org.java.service.impl;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.java.dao.*;
import org.java.service.Case_EntrusService;
import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Case_EntrusServiceImpl implements Case_EntrusService {

    @Autowired
    private Case_EntrustMapper case_entrustMapper;
    @Autowired
    private Sitesurvey_TaskMapper sitesurvey_taskMapper;
    @Autowired
    private Peoplesurvey_TaskMapper peoplesurvey_taskMapper;
    @Autowired
    private Eventsurvey_TaskMapper eventsurvey_taskMapper;
    @Autowired
    private Case_ReportMapper case_reportMapper;
    @Override
    public int insert(Map<String, Object> map) {
        int n=0;
        String uuid = UuidUtil.getTimeBasedUuid().toString();
        map.put("entrust_id",uuid);
        if(Boolean.valueOf(map.get("is_sitesurvey").toString())){
            int emp_id=Integer.parseInt(map.get("sitesurvey_emp").toString());
            String explains = (String) map.get("s_survey_explains");
            map.put("sitesurvey_id",uuid);
            sitesurvey_taskMapper.insert2(uuid,emp_id,explains);
        }
        if(Boolean.valueOf(map.get("is_peoplesurvey").toString())){
            int emp_id=Integer.parseInt(map.get("peoplesurvey_emp").toString());
            String explains = (String) map.get("s_survey_explains");
            map.put("peoplesurvey_id",uuid);
            peoplesurvey_taskMapper.insert2(uuid, emp_id,explains);
        }
        if(Boolean.valueOf(map.get("is_eventsurvey").toString())){
            int emp_id=Integer.parseInt(map.get("eventsurvey_emp").toString());
            String explains = (String) map.get("s_survey_explains");
            map.put("eventsurvey_id",uuid);
            eventsurvey_taskMapper.insert2(uuid, emp_id,explains);
        }
        n=case_entrustMapper.insert(map);
        if(n==1){
            case_reportMapper.update_Statu(map.get("report_id").toString(),3);
        }
        return n;
    }

    @Override
    public boolean checkTaskStatu(String task_id) {
        boolean b=false;
        //三个任务中必有一个任务
        Object eStatu = eventsurvey_taskMapper.getStatu(task_id);
        if(eStatu!=null && (Integer)eStatu==0){//如果有值且状态为0，则返回fase
            return b;
        }
        Object pStatu = peoplesurvey_taskMapper.getStatu(task_id);
        if(pStatu!=null && (Integer)pStatu==0){
            return b;
        }
        Object sStatu = sitesurvey_taskMapper.getStatu(task_id);
        if(sStatu!=null && (Integer)sStatu==0){
            return b;
        }
        //当每个任务状态都为1时返回true
        return true;
    }
}
