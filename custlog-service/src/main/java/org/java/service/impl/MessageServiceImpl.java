package org.java.service.impl;

import org.java.dao.MessageMapper;
import org.java.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public List<Map<String, Object>> getMessage(String custid) {
        return messageMapper.getMessage(custid);
    }

    @Override
    public int updateStatu(int mid) {
        return messageMapper.updateStatu(mid);
    }

    @Override
    public int addMessage(Map<String, Object> map) {
        return messageMapper.addMessage(map);
    }
}
