package com.bmy.dao.service;


import com.bmy.dao.domain.Region;

import java.util.List;

/**
 * 地区信息接口
 */
public interface RegionService {

    List<Region> selectAllProvince();

}
