package com.nihaov.photograph.pojo.vo;

import com.nihaov.photograph.pojo.po.ImagePO;

import java.util.Date;
import java.util.List;

/**
 * Created by nihao on 17/4/14.
 */
public class ImageVO {
    private Long id;
    private String title;
    private String path;
    private Long uid;
    private List<String> tags;
    private Integer width;
    private Integer height;
    private Date createdAt;

    public ImageVO() {
    }

    public ImageVO(ImagePO imagePO) {
        this.id=imagePO.getId();
        this.title=imagePO.getTitle();
        this.path=imagePO.getPath();
        this.width=imagePO.getWidth();
        this.height=imagePO.getHeight();
        this.createdAt=imagePO.getCreatedAt();
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
}
