package org.java.service.impl;

import org.java.dao.LinkManMapper;
import org.java.service.LinkManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class LinkManServiceImpl implements LinkManService {

    @Autowired
    private LinkManMapper lmapper;
    @Override
    public List<Map<String, Object>> getByCustId(String custid) {
       return lmapper.getByCustId(custid);

    }

    @Override
    public int addLinkMan(Map<String, Object> m) {
        return lmapper.addLinkMan(m);
    }

    @Override
    public int delLinkMan(String insureid) {
        return lmapper.delLinkMan(insureid);
    }

    @Override
    public int updateLinkMan(Map<String, Object> m) {
        return lmapper.updateLinkMan(m);
    }

    @Override
    public Map<String, Object> getOne(String insuredid) {
        return lmapper.getOne(insuredid);
    }
}
