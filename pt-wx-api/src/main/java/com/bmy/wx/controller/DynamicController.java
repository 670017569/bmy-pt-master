package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.util.RelationMapperUtils;
import com.bmy.dao.domain.Comment;
import com.bmy.dao.dto.CommentDTO;
import com.bmy.dao.service.DynamicCommentService;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.dao.service.DynamicService;
import com.bmy.dao.service.UserInfoService;
import com.bmy.dao.dto.DynamicDTO;
import com.bmy.dao.domain.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(tags = "动态管理接口")
public class DynamicController {

    Logger logger = LoggerFactory.getLogger(DynamicController.class);

    @Resource
    private DynamicService dynamicService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private DynamicPraiseService dynamicPraiseService;


    /***********************************         对动态的查询           **********************************/
    @ApiOperation("分页获取动态所有动态")
    @GetMapping("dynamics")
    public R<Object> getAllDynamics(Integer page, Integer size,HttpServletRequest request){
        PageHelper.startPage(page,size);
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicService.selectAll(userInfo.getId())));
    }

    @ApiOperation(value = "发布动态")
    @PostMapping("dynamic")
    public R<Object> pubDynamic(HttpServletRequest request ,@RequestBody DynamicDTO dynamicDTO){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        Boolean res = dynamicService.pubDynamic(dynamicDTO,userInfo);
        logger.info("动态发布操作结果：{}",res);
        return R.success(Response.DYNAMIC_PUBLISH_SUCCESS);
    }

    @GetMapping("/dynamic")
    @ApiOperation("获取用户自己的动态列表")
    public R<Object> getDynamic(HttpServletRequest request,Integer page, Integer size){
        PageHelper.startPage(page,size);
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.QUERY_SUCCESS,dynamicService.selectByUid(userInfo.getId()));
    }


    @ApiOperation("根据动态id删除动态")
    @DeleteMapping("/dynamic/{dynId}")
    public R<Object> delete(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        return R.success(Response.DELETE_SUCCESS,dynamicService.deleteById(dynId,userInfo));
    }

    /***********************************         对动态的点赞           **********************************/

    @ApiOperation("对动态点赞")
    @GetMapping("/dynamic/praise/{dynId}")
    public R<Object> praise(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将请求者uid与dynId插入动态点赞表中
        boolean res = dynamicPraiseService.praise(userInfo,dynId);
        //根据res返回操作结果
        if (res){
            return R.success(Response.PRAISE_SUCCESS);
        }else {
            return R.fail(Response.PRAISE_FAILED);
        }
    }
    @ApiOperation("取消对动态的点赞")
    @DeleteMapping("/dynamic/praise/{dynId}")
    public R<Object> delPraise(@PathVariable("dynId") Long dynId,HttpServletRequest request){
        //根据令牌查询操接口请求者身份信息
        UserInfo userInfo = userInfoService.getUserByToken(request);
        //然后将根据请求者uid与dynId进行删除
        boolean res = dynamicPraiseService.delPraise(userInfo,dynId);
        if (res){
            return R.success(Response.PRAISE_CANCEL_SUCCESS);
        }
        return R.success(Response.PRAISE_CANCEL_FAILED);
    }

    /***********************************         对动态的评论           **********************************/

    @Resource
    private DynamicCommentService dynamicCommentService;


    /**
     * 对动态发表评论
     * @param commentDTO
     * @param request
     * @return
     */
    @PostMapping("/comment")
    public R<Object> pubComment(@Valid @RequestBody CommentDTO commentDTO, HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);

        Comment comment = Comment.builder()
                .uid(userInfo.getId())
                .dynId(commentDTO.getDynId())
                .content(commentDTO.getContent())
                .toUid(commentDTO.getToUid())
                .build();
        boolean res =  dynamicCommentService.pubComment(comment);
        if (res){
            return new R<>(Response.COMMENT_SUCCESS);
        }else {
            return R.fail(Response.COMMENT_FAILED);
        }
    }


}
