package com.bmy.wx.controller;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.service.MessageService;
import com.bmy.dao.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "消息接收读取接口")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("接收点赞消息")
    @GetMapping("/praise/message")
    public R<Object> getAllPraiseMessage(Integer page,Integer size,HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(messageService.selectPraiseMessage(userInfo.getId())));
    }

//    @ApiOperation("接收被关注的消息")
//    @GetMapping("/follow/message")
//    public R<Object> getAllFollowMessage(Integer page,Integer size,HttpServletRequest request){
//        UserInfo userInfo = userInfoService.getUserByToken(request);
//        PageHelper.startPage(page,size);
//        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(messageService.selectFollowMessage(userInfo.getId())));
//    }

}
