package com.bmy.wx.service;

import com.bmy.dao.domain.WxOfficialNews;
import com.bmy.dao.mapper.NewsLinkMapper;
import com.bmy.dao.service.NewsLinkService;
import com.bmy.dao.dto.QuerySortTermDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
 * @ClassName NewsLinkService
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午7:49
 **/
@Service
public class NewsLinkServiceImpl implements NewsLinkService {

    Logger logger = LoggerFactory.getLogger(NewsLinkServiceImpl.class);

    @Resource
    private NewsLinkMapper newsLinkMapper;

    /**
     * 分页按条件查询新闻
     * @return
     */
    public List<WxOfficialNews> selectCategoryPage(QuerySortTermDTO querySortTermDTO){
        Example example = new Example(WxOfficialNews.class);
        Map<String,String> sort = querySortTermDTO.getSort();
        example.setOrderByClause(sort.get("type")+" "+sort.get("value"));
        return newsLinkMapper.selectByExample(example);
    }

}
