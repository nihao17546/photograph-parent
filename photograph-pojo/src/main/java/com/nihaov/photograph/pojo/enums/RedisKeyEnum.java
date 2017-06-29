package com.nihaov.photograph.pojo.enums;

/**
 * Created by nihao on 17/6/29.
 */
public enum  RedisKeyEnum {
    网站搜索关键字前缀(":photograph_web_search:"),
    微信小程序搜索关键字前缀(":photograph_weixin_search:");
    private String value;

    RedisKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
