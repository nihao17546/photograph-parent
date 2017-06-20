package com.nihaov.photograph.service;

import com.nihaov.photograph.pojo.result.SearchResult;

/**
 * Created by nihao on 17/6/16.
 */
public interface ISolrQueryService {
    SearchResult query(String name, String key, int page, int rows, String sort, String asc);
    SearchResult query(String key,int page, int rows,String sort,String asc);
}
