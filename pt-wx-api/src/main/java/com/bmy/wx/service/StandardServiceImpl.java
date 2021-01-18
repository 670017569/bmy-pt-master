package com.bmy.wx.service;

import com.bmy.dao.domain.Standard;
import com.bmy.dao.mapper.StandardMapper;
import com.bmy.dao.service.StandardService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StandardServiceImpl implements StandardService {

    @Resource
    private StandardMapper standardMapper;

    @Override
    public List<Standard> selectByRegionId(Integer regionId) {

        Example example = new Example(Standard.class);
        example.and()
                .andEqualTo("regionId",regionId)
                .andEqualTo("deleted",0);
        return standardMapper.selectByExample(example);
    }
}
