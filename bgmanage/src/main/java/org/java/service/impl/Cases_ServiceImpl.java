package org.java.service.impl;

import org.java.dao.CasesMapper;
import org.java.service.Cases_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Cases_ServiceImpl implements Cases_Service {

    @Autowired
    private CasesMapper casesMapper;

    @Override
    public List<Map<String, Object>> getList(Integer statu) {
        return casesMapper.getList(statu);
    }

    @Override
    public Map<String, Object> getCasesByLiable_emp(Integer emp_id) {
        return casesMapper.getCasesByLiable_emp(emp_id);
    }

    @Override
    public int letme(Map<String, Object> map) {
        return casesMapper.letme(map);
    }

    @Override
    public int nextStep(Map<String, Object> map) {
        return casesMapper.nextStep(map);
    }

    @Override
    public int complete(Map<String, Object> map) {
        return casesMapper.complete(map);
    }

    @Override
    public int insertFinance(Map<String, Object> map) {
        return casesMapper.insertFinance(map);
    }

    @Override
    public int getMyCasesCount(Integer emp_id) {
        Object obj=casesMapper.getMyCasesCount(emp_id);
        if(obj==null){
            return -1;
        }else{
            return Integer.parseInt(obj.toString());
        }
    }

    @Override
    public List<Map<String, Object>> getFinances(int statu) {
        return casesMapper.getFinances(statu);
    }

    @Override
    public int financeComplete(int id) {
        return casesMapper.financeComplete(id);
    }

    @Override
    public int getFinanceStatu(int id) {
        Map<String, Object> map = casesMapper.getFinanceStatu(id);
        if(map.get("statu")==null){
            return -1;
        }else{
            return Integer.parseInt(map.get("statu").toString());
        }
    }


}
