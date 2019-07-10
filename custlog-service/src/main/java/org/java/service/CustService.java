package org.java.service;

import java.util.Map;

public interface CustService {


    Map<String,Object> custLogin(String username,  String password);


    int addCustAccount( Map<String,Object> map);

}
