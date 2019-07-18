package org.java.service.impl;

import org.java.dao.PolicyMapper;
import org.java.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;
    @Override
    public List<Map<String, Object>> getPolicy(String custid, String statu) {
        return policyMapper.getPolicy(custid, statu);
    }
}
