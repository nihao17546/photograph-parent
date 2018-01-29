package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.spider.model.SpiderException;
import com.nihaov.photograph.spider.model.enmus.SpiderSourceEnum;

/**
 * Created by nihao on 18/1/24.
 */
public class TopitSpider {
    private static volatile TopitSpider topitSpider = null;

    private Thread thread = null;
    private boolean shutdown = true;// true:终止
    private ISpiderDAO spiderDAO = null;
    private String savePathPrefix = null;

    public ISpiderDAO getSpiderDAO() {
        return spiderDAO;
    }

    public void setSpiderDAO(ISpiderDAO spiderDAO) {
        this.spiderDAO = spiderDAO;
    }

    public String getSavePathPrefix() {
        return savePathPrefix;
    }

    public void setSavePathPrefix(String savePathPrefix) {
        this.savePathPrefix = savePathPrefix;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public void start(SpiderSourceEnum sourceEnum) throws SpiderException {
        if(thread != null && thread.isAlive()){
            throw new SpiderException("数据正在爬取中...");
        }
        if(spiderDAO == null){
            throw new SpiderException("SpiderDAO is null");
        }
        if(savePathPrefix == null){
            throw new SpiderException("savePathPrefix is null");
        }
        shutdown = false;
        if(sourceEnum == SpiderSourceEnum.TOPIT){
            thread = new Thread(new TopitSpiderThread(spiderDAO, savePathPrefix));
        }
        else if(sourceEnum == SpiderSourceEnum.JUJU){
            thread = new Thread(new JUJUSpiderThread(spiderDAO, savePathPrefix));
        }
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
