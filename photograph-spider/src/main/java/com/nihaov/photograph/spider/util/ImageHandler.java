package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.model.SpiderException;
import com.nihaov.photograph.spider.service.ISpiderService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nihao on 18/1/24.
 */
public class ImageHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImageHandler.class);
    private static volatile ImageHandler imageHandler = null;

    private Thread thread = null;
    private boolean shutdown = true;// true:终止
    private ISpiderService spiderService;
    private ISpiderDAO spiderDAO;

    public ISpiderService getSpiderService() {
        return spiderService;
    }

    public void setSpiderService(ISpiderService spiderService) {
        this.spiderService = spiderService;
    }

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
            throw new SpiderException("数据正在处理中...");
        }
        if(spiderService == null){
            throw new SpiderException("SpiderService is null");
        }
        if(spiderDAO == null){
            throw new SpiderException("SpiderDAO is null");
        }
        shutdown = false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("=================开始任务=================");
                while (true){
                    List<SpiderImgPO> list = spiderDAO.selectByFlag(0,
                            new RowBounds(0, 100));
                    for(SpiderImgPO spiderImgPO : list){
                        if(shutdown){
                            logger.info("=================任务终止=================");
                            return;
                        }
                        spiderService.handler(spiderImgPO);
                    }
                    if(list.size() < 100){
                        break;
                    }
                }
                logger.info("=================完成任务=================");
            }
        });
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



    private ImageHandler(){
    }

    public static ImageHandler build(){
        if(imageHandler == null){
            synchronized (ImageHandler.class){
                if(imageHandler == null){
                    imageHandler = new ImageHandler();
                }
            }
        }
        return imageHandler;
    }

}
