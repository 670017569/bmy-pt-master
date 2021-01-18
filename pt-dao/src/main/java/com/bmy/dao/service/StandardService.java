package com.bmy.dao.service;


import com.bmy.dao.domain.Standard;

import java.util.List;

public interface StandardService {

    List<Standard> selectByRegionId(Integer regionId);

}
