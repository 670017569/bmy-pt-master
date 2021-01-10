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

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public R(Integer code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R<Object> fail(Response response){
        return new R<>(response);
    }
    public static R<Object> fail(Integer code,String message){
        return new R<>(code,message);
    }


    public static R<Object> success(Integer code,String message,Object o){
        return new R<>(code,message,o);
    }
    public static R<Object> success(Integer code,String message){
        return new R<>(code,message);
    }
    public static R<Object> success(Response response,Object o){
        return new R<>(response,o);
    }
    public static R<Object> success(Response response){
        return new R<>(response);
    }

}
