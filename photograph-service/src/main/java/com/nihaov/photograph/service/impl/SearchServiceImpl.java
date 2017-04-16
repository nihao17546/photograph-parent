package com.nihaov.photograph.service.impl;

import com.google.common.base.Strings;
import com.nihaov.photograph.common.utils.BaseUtil;
import com.nihaov.photograph.pojo.constant.BaseConstant;
import com.nihaov.photograph.pojo.result.SearchResult;
import com.nihaov.photograph.pojo.vo.ImageVO;
import com.nihaov.photograph.service.ISearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nihao on 17/4/15.
 */
@Service
public class SearchServiceImpl implements ISearchService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SolrServer solrServer;
    @Value("#{configProperties['solrUrl']}")
    private String solrUrl;
    @Value("#{configProperties['defaultQuery']}")
    private String defaultQuery;

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(solrUrl);
    }

    @Override
    public SearchResult search(String keyword, int page, int rows) {
        return search(defaultQuery,keyword,page,rows);
    }

    private ImageVO convert(SolrDocument document){
        ImageVO imageVO = new ImageVO();
        Object id = document.get("id");
        Object title = document.get("image_title");
        Object path = document.get("image_path");
        Object uid = document.get("image_uid");
        Object tags = document.get("image_tag");
        Object date = document.get("image_date");
        Object width = document.get("image_width");
        Object height = document.get("image_height");
        if(id!=null){
            imageVO.setId(Long.parseLong(id.toString()));
        }
        if(title!=null){
            imageVO.setTitle((String) title);
        }
        if(path!=null){
            imageVO.setPath((String) path);
        }
        if(uid!=null){
            imageVO.setUid((long) uid);
        }
        if(tags!=null){
            imageVO.setTags((List<String>) tags);
        }
        if(width!=null){
            imageVO.setWidth((Integer) width);
        }
        if(height!=null){
            imageVO.setHeight((Integer) height);
        }
        if(date!=null){
            imageVO.setCreatedAt((Date) date);
        }
        return imageVO;
    }

    @Override
    public SearchResult search(String name, String keyword,int page,int rows) {
        SearchResult result = new SearchResult();
        if(!Strings.isNullOrEmpty(keyword)){
            SolrQuery query = new SolrQuery();
            query.set("df", name);
            query.setQuery(keyword);
            query.setStart((page - 1) * rows);
            query.setRows(rows);
            QueryResponse response = null;
            try {
                response = solrServer.query(query);
            } catch (SolrServerException e) {
                logger.error("solr search ERROR",e);
            }
            if(response!=null){
                SolrDocumentList solrDocumentList = response.getResults();
                long recordCount = solrDocumentList.getNumFound();
                List<ImageVO> list = new ArrayList(new Long(recordCount).intValue());
                for (SolrDocument solrDocument : solrDocumentList) {
                    ImageVO imageVO = convert(solrDocument);
                    list.add(imageVO);
                }
                result.setData(list);
                result.setRecordCount(recordCount);
                long pageCount = recordCount / rows;
                if (recordCount % rows > 0) {
                    pageCount++;
                }
                result.setPageCount(pageCount);
                if(pageCount==0){
                    result.setCurPage(0);
                }
                else{
                    result.setCurPage(page);
                }
            }
        }
        return result;
    }
}
