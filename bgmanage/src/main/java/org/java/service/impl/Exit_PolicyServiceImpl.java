package org.java.service.impl;

import org.java.dao.Exit_PolicyMapper;
import org.java.service.Exit_policyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class Exit_PolicyServiceImpl implements Exit_policyService
{

    @Autowired
    private Exit_PolicyMapper exit_policyMapper;
    @Override
    public List<Map<String, Object>> getExitPolicy() {
        return exit_policyMapper.getExitPolicy();
    }

    @Override
    public Map<String, Object> getOnePolicy(int eid) {
        return exit_policyMapper.getOnePolicy(eid);
    }

    @Override
    public List<Map<String, Object>> getPersonPolicy(int statu) {
        return exit_policyMapper.getPersonPolicy(statu);
    }

    @Override
    public int updateStatu(int eid) {
        return exit_policyMapper.updateStatu(eid);
    }

    @Override
    public int addAudit(Map<String, Object> map) {
        return exit_policyMapper.addAudit(map);
    }

    @Override
    public int addMessage(Map<String, Object> map) {
        return exit_policyMapper.addMessage(map);
    }

    @Override
    public int updateOrderDate(String orderid) {
        return exit_policyMapper.updateOrderDate(orderid);
    }
}
