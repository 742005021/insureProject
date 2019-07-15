package org.java.service;

import java.util.List;
import java.util.Map;

public interface CouponService {

    List<Map<String,Object>> findUsable(String custid,Integer statu);
    int addCustCoupon(String custid,int cid);
}
