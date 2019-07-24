package org.java.service;

import java.util.Map;

public interface CustService {

    Map<String,Object> custLogin(String username,  String password);

    int addCustAccount( Map<String,Object> map);
    String check( String uname);
    int editPwd(String pwd, String custid);

    int getCustScore( String custid);

    int updateScore(String custid,  int score);
    Map<String,Object> getcustinfo(String custid);

}
