package com.bmy.core.constant;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName R
 * @Description TODO
 * @Author potato
 * @Date 2020/12/15 下午10:49
 **/
@Data
@NoArgsConstructor
public class R<T> {

    private Integer code;
    private String message;
    private T data;

    public R(Response response) {
        this.code = response.getCode();
        this.message = response.getMessage();
    }
    public R(Response response,T o) {
        this.code = response.getCode();
        this.message = response.getMessage();
        this.data = o;
    }

}
