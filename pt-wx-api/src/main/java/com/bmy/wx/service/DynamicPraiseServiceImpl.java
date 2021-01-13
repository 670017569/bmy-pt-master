package com.bmy.wx.service;

import com.bmy.dao.service.DynamicPraiseService;
import org.springframework.stereotype.Service;

@Service
public class DynamicPraiseServiceImpl implements DynamicPraiseService {
    @Override
    public boolean praise(Long uid, Long dynId) {
        return false;
    }

    @Override
    public boolean delPraise(Long uid, Long dynId) {
        return false;
    }
}
