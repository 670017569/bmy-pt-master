package com.bmy.core.constant;


/**
 * @ClassName Response
 * @Description 枚举已知返回信息
 * @Author potato
 * @Date 2020/12/15 下午10:54
 **/
public enum Response {
    NONE_DATA(200,"暂无数据"),
    FOLLOW_SUCCESS(200,"关注成功"),
    DELETE_FOLLOW_SUCCESS(200,"取消关注成功"),
    FOLLOW_FAILED(406,"关注失败"),
    DELETE_FOLLOW_FAILED(406,"取消关注失败"),
    LOAD_SUCCESS(200,"加载成功"),
    LOAD_FAILED(406,"加载失败"),
    PUBLISH_SUCCESS(200,"发布成功"),
    COMMENT_SUCCESS(200,"评论成功"),
    COMMENT_FAILED(406,"评论失败"),
    PUBLISH_FAILED(407,"发布失败，请稍后再试"),
    DYNAMIC_PUBLISH_SUCCESS(200,"动态发布成功"),
    PRAISE_SUCCESS(200,"点赞成功"),
    PRAISE_CANCEL_SUCCESS(200,"取消点赞成功"),
    PRAISE_CANCEL_FAILED(200,"取消点赞失败"),
    PRAISE_FAILED(406,"请勿重复点赞"),
    INVALID_ACCESS_TOKEN(401,"UnAuthorized"),
    DELETE_SUCCESS(200,"删除成功"),
    DELETE_FAILED(400,"删除失败"),
    QUERY_SUCCESS(200,"查询成功"),
    QUERY_FAILED(406,"查询失败"),
    SIGN_IN_SUCCESS(200,"签到成功"),
    SIGN_IN_FAILED(406,"签到失败"),
    REFRESH_SUCCESS(200,"更新成功"),
    REFRESH_FAILED(400,"更新失败"),
    SMS_SEND_SUCCESS(200, "短信发送成功"),
    SMS_SEND_FAILED(401, "短信发送失败"),
    ILLEGAL_PARAMS(406,"非法参数"),
    LOGIN_SUCCESS(200,"登录成功"),
    REGISTER_SUCCESS(200,"注册成功"),
    REGISTER_FAILED(401,"注册失败"),
    CHECK_SUCCESS(200,"查询成功"),
    INVALID_INPUT(40001, "不合法信息"),
    UNAUTHORIZED(401,"未登录，或登录已过期"),
    INVALID_GRANT_TYPE(405,"不支持的认证方式"),
    INVALID_PASSWORD_USERNAME(403,"用户名或密码错误"),
    ILLEGAL_SCOPE(408,"不合法的授权范围"),
    USER_AUTH_UPDATE_SUCCESS(200,"用户认证信息更新成功"),
    USER_AUTH_UPDATE_FAILED(200,"用户认证信息更新失败"),
    USERINFO_UPDATE_SUCCESS(200,"用户详细信息更新成功"),
    USERINFO_UPDATE_FAILED(403,"用户详细信息更新失败"),
    INVALID_PHONE(40004, "无效的手机号"),
    SUCCESS_SMS_CODE(200,"验证码验证通过"),
    INVALID_SMS_CODE(400, "验证码错误"),
    ILLEGAL_CLIENT(406,"不合法的客户端"),
    ALREADY_DONE(40006, "不要重复操作"),
    UPDATE_FAIL(40007, "更新失败"),
    NO_PERMISSION(40301, "无权限"),
    WX_SESSION_OPEN_FAIL(406,"微信会话打开失败"),
    WX_SESSION_INVALID(407,"微信签名完整性错误"),
    ACCOUNT_LOCKED(408,"账号状态异常"),
    USER_NOT_EXIST(405,"用户不存在"),
    INVALID_PASSWORD(406,"密码错误"),
    ACCOUNT_PASSWORD_WRONG(407,"用户名或密码错误"),
    PHONE_HAS_USED(401,"该手机号已注册"),
    UNKNOWN_ERROR(500,"系统未知错误");

    private Integer code;
    private String message;

    Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
