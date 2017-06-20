package com.nihaov.photograph.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by nihao on 17/6/16.
 */
public class IMGVO {
    private Long id;
    private String title;
    private String compressSrc;
    private String src;
    private Integer width;
    private Integer height;
    private Date createdAt;
    private List<String> tags;

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
