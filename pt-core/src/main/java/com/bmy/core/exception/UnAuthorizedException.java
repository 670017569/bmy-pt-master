package com.bmy.core.exception;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;

/**
 * @ClassName UnAuthorizedException
 * @Description 401异常（未认证异常）
 * @Author potato
 * @Date 2020/12/15 下午11:01
 **/
public class UnAuthorizedException extends BadRequestException{

    public UnAuthorizedException(Response responses) {
        super(responses);
    }

    public UnAuthorizedException(R responses){
        super(responses);
    }
}
