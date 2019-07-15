package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CouponMapper  {

    @Select("select * from cust_coupon,coupon where cust_id=#{custid} and statu=#{statu} and cust_coupon.id=coupon.id")
    List<Map<String,Object>> findUsable(@Param("custid") String custid, @Param("statu") Integer statu);


    @Insert("insert cust_coupon values(null,#{custid},#{cid},default)")
    int addCustCoupon(@Param("custid")String custid,@Param("cid")int cid);
}
