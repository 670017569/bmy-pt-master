package com.bmy.wx.service;

import com.bmy.dao.domain.Region;
import com.bmy.dao.mapper.RegionMapper;
import com.bmy.dao.service.RegionService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionMapper regionMapper;

    @Override
    public List<Region> selectAllProvince() {
        Example example = new Example(Region.class);
        example.and().andEqualTo("level",1);
        return regionMapper.selectByExample(example);
    }


}
