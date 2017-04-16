package com.nihaov.photograph.pojo.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */
public class SearchResult {
    private List data;
    private long recordCount;
    private long pageCount;
    private long curPage;

    public SearchResult() {
        this.data = new ArrayList();
        this.recordCount = 0;
        this.pageCount = 0;
        this.curPage = 0;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getCurPage() {
        return curPage;
    }

    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }
}
