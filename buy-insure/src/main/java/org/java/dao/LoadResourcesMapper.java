package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface LoadResourcesMapper {

    @Select("select * from insuretype")
    List<Map<String, Object>> loadInsureType();

    @Select("select item_id,item_name,min_money,start_deadline,end_deadline,max_people,suitable_profession,start_age,end_age,item_style,item_info,item_target,base_money from insurance_item")
    List<Map<String, Object>> loadInsureItem();

    @Select("select item_file from insurance_item where item_id = #{item_id}")
    Object searchTerms(@Param("item_id") Integer id);

    @Select("select item_id,item_name,start_deadline,end_deadline, max_people,start_age,end_age,base_money from insurance_item where item_id = #{item_id}")
    Map<String, Object> searchInsureInfo(@Param("item_id") Integer item_id);

    @Select("select * from job")
    List<Map<String, Object>> loadJobs();

    @Select("select * from profession where job_id = #{job_id}")
    List<Map<String, Object>> loadProfession(@Param("job_id") Integer job_id);

    @Select("select * from cust_account where username=#{uname} and password=#{pwd}")
    Map<String, Object> secondLogin(@Param("uname") String uname, @Param("pwd") String pwd);

    @Select("select * from cust_account where cust_id = #{custid}")
    Map<String, Object> method(@Param("custid") String custid);

    @Select("select * from custinfo where cust_id=#{cust_id}")
    Map<String, Object> loadUserInfo(@Param("cust_id") String cust_id);

    @Insert("insert into custinfo values(#{m.cust_id},#{m.cust_name},#{m.cust_sex},#{m.license_id},#{m.cust_licenseno},#{m.cust_bir},#{m.cust_email},#{m.cust_address},null,null)")
    void addTou(@Param("m") Map<String, Object> map);

    @Update("update custinfo set cust_name=#{m.cust_name},cust_sex=#{m.cust_sex},license_id=#{m.license_id},cust_licenseno=#{m.cust_licenseno},cust_bir=#{m.cust_bir},cust_email=#{m.cust_email},cust_address=#{m.cust_address} where  cust_name=#{m.cust_id}")
    void updateTou(@Param("m") Map<String, Object> map);

    @Insert("insert into insuredinfo values(#{m.insured_id},#{m.insured_name},#{m.insured_sex},#{m.license_id},#{m.license_no},#{m.birthday},#{m.cust_id},#{m.address})")
    void addInsuredInfo(@Param("m") Map<String, Object> map);

    @Insert("insert into policy_order values(#{m.order_id},#{m.cust_id},null,#{m.zhiye},#{m.max_people},#{m.starttime},3,null,#{m.jieguo},#{m.item_id},#{m.json})")
    void generateOrders(@Param("m") Map<String, Object> map);

    @Update("update policy_order set order_statu = #{order_status} where order_id = #{order_id}")
    void nextOrder(@Param("order_status") Integer status, @Param("order_id") String order_id);

    @Insert("insert into policy values(#{m.policy_id},#{m.cust_id},#{m.insure_stime},#{m.insure_etime},#{m.price},#{m.policyorder})")
    void addOrder(@Param("m") Map<String, Object> param);

    @Update("update policy set policyorder=#{policyorder} where policy_id=#{policyid}")
    int addOrder2(@Param("policyorder") Blob blob, @Param("policyid") String policyid);

    @Insert("insert into policy_insured_relationship values(default, #{order_id},#{insured_id})")
    void addInfo(@Param("order_id") String order_id, @Param("insured_id") String insured_id);

    @Select("select cust_coupon.ccid, cust_coupon.cust_id,cust_coupon.statu,coupon.* from cust_coupon,coupon where cust_id=#{cust_id} and statu=1 and cust_coupon.id=coupon.id")
    List<Map<String,Object>> searchVoucher(@Param("cust_id") String cust_id);

    @Update("update custinfo set cust_score = cust_score + #{score} where cust_id = #{cust_id}")
    void score(@Param("score") Integer score, @Param("cust_id") String cust_id);

    @Update("update cust_coupon set statu = 2 where ccid = #{ccid}")
    void updateStatus(@Param("ccid") Integer ccid);

}
