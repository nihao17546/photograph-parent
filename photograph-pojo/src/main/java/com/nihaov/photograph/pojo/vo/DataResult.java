package com.nihaov.photograph.pojo.vo;

/**
 * Created by nihao on 17/4/12.
 */
public class DataResult {
    private Integer code = 300;
    private String message;
    private Object result;

    public DataResult() {
    }

    public DataResult(Integer code) {
        this.code = code;
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
