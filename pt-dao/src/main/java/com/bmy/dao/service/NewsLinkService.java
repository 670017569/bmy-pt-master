package com.bmy.dao.service;

import com.bmy.dao.dto.QuerySortTermDTO;
import com.bmy.dao.domain.WxOfficialNews;

import java.util.List;

public interface NewsLinkService {

    public List<WxOfficialNews> selectCategoryPage(QuerySortTermDTO querySortTermDTO);

}
