package com.bmy.dao.service;

import com.bmy.core.vo.QuerySortTerm;
import com.bmy.dao.domain.WxOfficialNews;

import java.util.List;

public interface NewsLinkService {

    public List<WxOfficialNews> selectCategoryPage(QuerySortTerm querySortTerm);

}
