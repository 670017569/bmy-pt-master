package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.WxOfficialNews;
import com.bmy.wx.service.NewsLinkService;
import com.bmy.wx.vo.QuerySortTerm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName NewsLinkController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/23 下午8:31
 **/
@RestController
@Api(tags = "新闻链接接口")
public class WxOfficialNewsController {

    Logger logger = LoggerFactory.getLogger(WxOfficialNewsController.class);

    @Resource
    private NewsLinkService newsLinkService;

    /**
     * 根据查询条件和排序方式查询新闻
     * @param page
     * @param size
     * @param querySortTerm
     * @return
     */
    @ApiOperation("根据查询条件和排序方式查询新闻")
    @PostMapping("/news")
    private R<PageInfo<WxOfficialNews>> getNewsPage(@ApiParam("页码") @RequestParam(name = "page",defaultValue = "1") Integer page ,
                                                    @ApiParam("分页大小") @RequestParam(name = "size",defaultValue = "10") Integer size ,
                                                    @ApiParam("查询条件及排序方式") @RequestBody(required = false) QuerySortTerm querySortTerm){
        PageHelper.startPage(page,size);
        return new R<>(Response.QUERY_SUCCESS,new PageInfo<>(newsLinkService.selectCategoryPage(querySortTerm)));
    }




}
