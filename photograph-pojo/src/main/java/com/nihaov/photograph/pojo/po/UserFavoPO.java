package com.nihaov.photograph.pojo.po;

import java.util.Date;

/**
 * Created by nihao on 17/6/15.
 */
public class UserFavoPO {
    private Long uid;
    private Long picId;
    private Date createdAt;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
