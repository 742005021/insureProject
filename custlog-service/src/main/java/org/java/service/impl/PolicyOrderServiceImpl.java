package org.java.service.impl;

import org.java.dao.PolicyOrderMapper;
import org.java.service.PolicyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class PolicyOrderServiceImpl implements PolicyOrderService {

    @Autowired
    private PolicyOrderMapper mapper;
    @Override
    public List<Map<String, Object>> getPolicy(String custid, String statu) {
        return mapper.getPolicy(custid, statu);
    }

    @Override
    public Map<String, Object> getPolicyOrder(String policyid) {
        return mapper.getPolicyOrder(policyid);
    }
}
