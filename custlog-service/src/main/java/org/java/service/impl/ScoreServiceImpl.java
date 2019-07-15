package org.java.service.impl;

import org.java.dao.ScoreMapper;
import org.java.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreMapper smapper;

    @Override
    public int addInfo(Map<String, Object> m) {
        return smapper.addInfo(m);
    }

    @Override
    public List<Map<String, Object>> showScore(String custid) {
        return smapper.showScore(custid);
    }
}
