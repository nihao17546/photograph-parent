package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.spider.model.SpiderException;

/**
 * Created by nihao on 18/1/24.
 */
public class TopitSpider {
    private static volatile TopitSpider topitSpider = null;

    private Thread thread = null;
    private boolean shutdown = true;// true:终止
    private ISpiderDAO spiderDAO = null;

    public ISpiderDAO getSpiderDAO() {
        return spiderDAO;
    }

    public void setSpiderDAO(ISpiderDAO spiderDAO) {
        this.spiderDAO = spiderDAO;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public void start() throws SpiderException {
        if(thread != null && thread.isAlive()){
            throw new SpiderException("数据正在爬取中...");
        }
        if(spiderDAO == null){
            throw new SpiderException("SpiderDAO is null");
        }
        shutdown = false;
        thread = new Thread(new TopitSpiderThread(spiderDAO));
        thread.start();
    }

    public void stop() throws SpiderException{
        if(thread == null || !thread.isAlive()){
            throw new SpiderException("操作已经终止...");
        }
        shutdown = true;
    }

    public boolean isRunning(){
        if(thread != null && thread.isAlive()){
            return true;
        }
        return false;
    }



    private TopitSpider(){
    }

    public static TopitSpider build(){
        if(topitSpider == null){
            synchronized (TopitSpider.class){
                if(topitSpider == null){
                    topitSpider = new TopitSpider();
                }
            }
        }
        return topitSpider;
    }

}
