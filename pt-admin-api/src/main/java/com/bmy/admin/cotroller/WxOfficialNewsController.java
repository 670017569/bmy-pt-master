package com.bmy.admin.cotroller;

import com.bmy.admin.service.WxOfficialService;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.mapper.WxOfficialNewsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName WxOfficialNewsController
 * @Description 微信公众号文章接口
 * @Author potato
 * @Date 2020/12/24 下午8:44
 **/
@RestController
@Api(tags = "微信公众号文章接口——首页新闻")
public class WxOfficialNewsController {

    Logger logger = LoggerFactory.getLogger(WxOfficialNewsController.class);

    @Resource
    private WxOfficialService wxOfficialService;

    @Resource
    private WxOfficialNewsMapper wxOfficialNewsMapper;

    @GetMapping("/news")
    @ApiOperation("分页查询当前置顶新闻")
    public R<Object> getNews(Integer page,Integer size){
        PageHelper.startPage(page,size);
        return new R<>(Response.QUERY_SUCCESS,new PageInfo<>(wxOfficialNewsMapper.selectAll()));
    }

    @GetMapping("/news/refresh")
    @ApiOperation("从微信小程序服务器更新新闻")
    public R<Object> refresh(Integer count){
        PageHelper.startPage(1,10);
        int res = wxOfficialService.refresh(count);
        if (res == 0){
            return new R<>(200,"数据已经是最新的了");
        }else {
            return new R<>(200,"成功更新"+res+"条数据",new PageInfo<>(wxOfficialNewsMapper.selectAll()));
        }
    }


    @DeleteMapping("/new")
    @ApiOperation("在数据库中删除一条新闻")
    public R<Object> delete(Long id){
        try {
            if ( 1 == wxOfficialNewsMapper.deleteByPrimaryKey(id)){
                return new R<>(Response.DELETE_SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new R<>(Response.DELETE_FAILED);
    }



}
