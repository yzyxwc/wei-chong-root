package com.example.demo.exception;


import com.example.demo.bean.ResultJson;
import lombok.Getter;

/**
 * @author wc
 * Created at 2018/8/24.
 */
@Getter
public class CustomException extends RuntimeException{
    private ResultJson resultJson;

    public CustomException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}
