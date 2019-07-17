package org.java.service;

import java.util.List;
import java.util.Map;

public interface LinkManService {

    public List<Map<String,Object>> getByCustId( String custid);

    public int addLinkMan( Map<String,Object> m);

    public int delLinkMan(String insureid);


    public int updateLinkMan( Map<String,Object> m);

    public Map<String,Object> getOne( String insuredid);

}
