package org.java.service.impl;

import org.java.dao.CustMapper;
import org.java.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CustServiceImpl implements CustService {

    @Autowired
  private CustMapper mapper;
    @Override
    public Map<String, Object> custLogin(String username, String password) {
        return mapper.custLogin(username, password);
    }

    @Override
    public int addCustAccount(Map<String, Object> map) {
        return mapper.addCustAccount(map);
    }

    @Override
    public String check(String uname) {
        return mapper.check(uname);
    }
}
