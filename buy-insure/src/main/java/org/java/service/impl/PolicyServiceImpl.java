package org.java.service.impl;

import org.java.dao.PolicyMapper;
import org.java.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    private PolicyMapper policyMapper;
    @Override
    public int addOrder(Blob blob, String policyid) {
        return policyMapper.addOrder(blob, policyid);
    }
}
