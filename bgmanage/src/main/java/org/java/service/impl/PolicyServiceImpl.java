package org.java.service.impl;

import org.java.dao.PolicyMapper;
import org.java.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public String getPolicy_Info(String policy_Id) {
        return policyMapper.getPolicy_Info(policy_Id);
    }
}
