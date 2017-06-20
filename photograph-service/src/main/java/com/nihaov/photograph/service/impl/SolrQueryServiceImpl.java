package com.nihaov.photograph.service.impl;

import com.google.common.base.Strings;
import com.nihaov.photograph.pojo.result.SearchResult;
import com.nihaov.photograph.pojo.vo.IMGVO;
import com.nihaov.photograph.service.ISolrQueryService;
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
 * Created by nihao on 17/6/16.
 */
@Service
public class SolrQueryServiceImpl implements ISolrQueryService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SolrServer solrServer;
    @Value("#{configProperties['coreUrl']}")
    private String coreUrl;
    @Value("#{configProperties['defaultQuery']}")
    private String defaultQuery;

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(coreUrl);
    }

    private IMGVO convert(SolrDocument document){
        IMGVO imageVO = new IMGVO();
        Object id = document.get("id");
        Object title = document.get("image_title");
        Object image_compress_src = document.get("image_compress_src");
        Object image_src = document.get("image_src");
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
        if(image_compress_src!=null){
            imageVO.setCompressSrc((String) image_compress_src);
        }
        if(image_src!=null){
            imageVO.setSrc((String) image_src);
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
    public SearchResult query(String name,String keyword, int page, int rows,String sort,String asc) {
        SearchResult result = new SearchResult();
        if(!Strings.isNullOrEmpty(keyword)){
            SolrQuery query = new SolrQuery();
            if(sort!=null){
                if(asc!=null&&asc.equals("asc")){
                    query.setSort(sort, SolrQuery.ORDER.asc);
                }
                else{
                    query.setSort(sort, SolrQuery.ORDER.desc);
                }
            }
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
                List<IMGVO> list = new ArrayList(new Long(recordCount).intValue());
                for (SolrDocument solrDocument : solrDocumentList) {
                    IMGVO imageVO = convert(solrDocument);
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

    @Override
    public SearchResult query(String key, int page, int rows,String sort,String asc) {
        return query(defaultQuery,key,page,rows,sort,asc);
    }
}
