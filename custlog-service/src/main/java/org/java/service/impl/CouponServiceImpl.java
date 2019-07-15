package org.java.service.impl;

import org.java.dao.CouponMapper;
import org.java.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper cmapper;
    @Override
    public List<Map<String, Object>> findUsable(String custid, Integer statu) {
        return cmapper.findUsable(custid, statu);
    }

    @Override
    public int addCustCoupon(String custid, int cid) {
        return cmapper.addCustCoupon(custid, cid);
    }
}
