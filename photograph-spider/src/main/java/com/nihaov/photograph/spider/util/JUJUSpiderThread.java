package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.dao.ISpiderDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by nihao on 18/1/29.
 */
public class JUJUSpiderThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "http://juju.la/blog/hot";
    private ISpiderDAO spiderDAO;
    private static final Random rd = new Random();
    private String savePathPrefix = null;

    public JUJUSpiderThread(ISpiderDAO spiderDAO, String savePathPrefix) {
        this.spiderDAO = spiderDAO;
        this.savePathPrefix = savePathPrefix;
    }

    @Override
    public void run() {
        logger.info("==============开始爬取==============");
        while (true){
            if(TopitSpider.build().isShutdown()){
                logger.info("==============终止操作==============");
                break;
            }

        }
    }
}
