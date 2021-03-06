package com.bmy.core.exception;


import com.bmy.core.constant.R;
import com.bmy.core.constant.Response;

/**
 * @ClassName BadRequestException
 * @Description 400异常
 * 其他40x异常的基类
 * @Author potato
 * @Date 2020/12/15 下午10:59
 **/
public class BadRequestException extends RuntimeException{

    // 只读
    private final R<Object> resp;

    public BadRequestException(Response response) {
        super(response.getMessage());
        this.resp = new R<>(response);
    }

    public BadRequestException(R<Object> resp){
        super(resp.getMessage());
        this.resp = resp;
    }

    public R<Object> getResp() {
        return resp;
    }


}
