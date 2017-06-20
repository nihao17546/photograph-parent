package com.nihaov.photograph.pojo.po;

import java.util.Date;

/**
 * Created by nihao on 17/6/15.
 */
public class IMGPO {
    private Long id;
    private String title;
    private String compressSrc;
    private String src;
    private Integer width;
    private Integer height;
    private Date createdAt;
    private Boolean flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompressSrc() {
        return compressSrc;
    }

    public void setCompressSrc(String compressSrc) {
        this.compressSrc = compressSrc;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
