package com.bmy.wx.controller;
import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.BadRequestException;
import com.bmy.dao.domain.User;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.mapper.UserInfoMapper;
import com.bmy.dao.mapper.UserMapper;
import com.bmy.wx.drools.RuleService;
import com.bmy.wx.service.UserInfoService;
import com.bmy.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserInfoController
 * @Description TODO
 * @Author potato
 * @Date 2020/12/25 下午5:59
 **/
@RestController
@Api(tags = "用户详细信息接口")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RuleService ruleService;

    @ApiOperation(value = "获取用户详细信息")
    @GetMapping("/user/info")
    public R<Object> getUserInfo(HttpServletRequest servletRequest){
        return new R<>(Response.QUERY_SUCCESS,userInfoService.getUserByToken(servletRequest));
    }

    /**
     *获取用户账号及角色信息
     * @param servletRequest
     * @return
     */
    @ApiOperation(value = "获取用户账号及角色信息")
    @GetMapping("/user/me")
    public Object checkToken(HttpServletRequest servletRequest){
        String header = servletRequest.getHeader("Authorization");
        String token = header.replace("bearer ","");
        return userInfoService.me(token);
    }

    @GetMapping("/sign")
    @ApiOperation("签到")
    public R<Object> rule(HttpServletRequest servletRequest){
        UserInfo userInfo = userInfoService.getUserByToken(servletRequest);
        // 用户不为空进入下一步
        if(userInfo != null){
            if (userInfo.getSignToday()){
                return R.success(302,"今天已签到，请明天再来");
            }
            ruleService.rule(userInfo, "rule_sign_in");
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
            return R.success(Response.SIGN_IN_SUCCESS,userInfo);
        }
        return new R<>(Response.UPDATE_FAIL);
    }



}
