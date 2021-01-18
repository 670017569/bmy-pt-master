package com.bmy.wx.controller;

import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.dao.domain.Comment;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.dto.CommentDTO;
import com.bmy.dao.service.DynamicCommentService;
import com.bmy.dao.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "动态评论接口")
public class DynamicCommentController {

    /***********************************         对动态的评论           **********************************/
    @Resource
    private DynamicCommentService dynamicCommentService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 对动态发表评论
     * @param commentDTO
     * @param request
     * @return
     */
    @ApiOperation("发表评论,一级评论不需要id，二级评论需要插入一个一级评论的id")
    @PostMapping("/dynamic/comment")
    public R<Object> pubComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        UserInfo userInfo = userInfoService.getUserByToken(request);
        Comment comment = Comment.builder()
                .uid(userInfo.getId())
                .dynId(commentDTO.getDynId())
                .content(commentDTO.getContent())
                .toUid(commentDTO.getToUid())
                .pid(commentDTO.getId())
                .build();
        Comment comments =  dynamicCommentService.pubComment(comment);
        if (comments == null){
            return R.fail(Response.COMMENT_FAILED);
        }
        return new R<>(Response.COMMENT_SUCCESS,comments);
    }

    @ApiOperation("获取动态的一级评论")
    @GetMapping("/dynamic/comments")
    public R<Object> getComments(Long dynId, Integer page, Integer size){
        PageHelper.startPage(page,size);
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicCommentService.selectAll(dynId)));
    }

    @ApiOperation("根据评论的id获取该评论下的二级评论")
    @GetMapping("/dynamic/comment/{id}")
    public R<Object> getComment(@PathVariable("id") Long id, Integer page, Integer size){
        PageHelper.startPage(page,size);
        return R.success(Response.LOAD_SUCCESS,new PageInfo<>(dynamicCommentService.selectByPid(id)));
    }

    @ApiOperation("根据评论id删除评论")
    @DeleteMapping("/dynamic/comment/{id}")
    public R<Object> delComment(@PathVariable("id") Long id){
        return R.success(Response.DELETE_SUCCESS,dynamicCommentService.delComment(id));
    }

    @ApiOperation("获取动态的所有评论")
    @GetMapping("/dynamic/comment")
    public R<Object> getAllComments(Long dynId){
        return R.success(Response.QUERY_SUCCESS,new PageInfo<>(dynamicCommentService.selectAll(dynId)));
    }
}
