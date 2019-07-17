package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface LinkManMapper {

    @Select("select * from insuredinfo a ,license b where cust_id=#{custid} and a.license_id=b.license_id")
    public List<Map<String,Object>> getByCustId(@Param("custid") String custid);

    @Insert("insert into insuredinfo values(uuid(),#{m.name},#{m.sex},#{m.lid},#{m.lno},#{m.bir},#{m.custid},#{m.address})")
    public int addLinkMan(@Param("m") Map<String,Object> m);

    @Delete("delete from insuredinfo where insured_id=#{insuredid}")
    public int delLinkMan(@Param("insuredid")String insureid);


   @Update("update insuredinfo set insured_name=#{m.name},insured_sex=#{m.sex},license_id=#{m.lid},license_no=#{m.lno},birthday=#{m.bir},address=#{m.address} where insured_id=#{m.insured_id} ")
    public int updateLinkMan(@Param("m") Map<String,Object> m);


   @Select("select * from insuredinfo where insured_id=#{insuredid}")
   public Map<String,Object> getOne(@Param("insuredid") String insuredid);
}
