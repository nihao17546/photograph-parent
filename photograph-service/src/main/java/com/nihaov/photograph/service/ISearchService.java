package com.nihaov.photograph.service;

import com.nihaov.photograph.pojo.result.SearchResult;

/**
 * Created by nihao on 17/4/15.
 */
public interface ISearchService {
    SearchResult search(String keyword, int page, int rows);
    SearchResult search(String name, String keyword, int page, int rows);
}
