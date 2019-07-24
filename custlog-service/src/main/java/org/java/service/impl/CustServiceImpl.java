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
      int n=  mapper.addCustAccount(map);
      if (n==1){
          mapper.addCustInfoId(map);
      }
      return n;
    }

    @Override
    public String check(String uname) {
        return mapper.check(uname);
    }

    @Override
    public int editPwd(String pwd, String custid) {

        return mapper.editPwd(pwd, custid);
    }

    @Override
    public int getCustScore(String custid) {
        return mapper.getCustScore(custid);
    }

    @Override
    public int updateScore(String custid, int score) {
        return mapper.updateScore(custid, score);
    }

    @Override
    public Map<String, Object> getcustinfo(String custid) {
        return mapper.getcustinfo(custid);
    }

}
