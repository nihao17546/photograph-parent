package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.constant.BaseConstant;
import org.apache.http.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;


/**
 * Created by nihao on 18/1/25.
 */
public class TopitSpiderThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "http://www.topit.cc";
    private ISpiderDAO spiderDAO;
    private static final Random rd = new Random();
    private String savePathPrefix = null;

    public TopitSpiderThread(ISpiderDAO spiderDAO, String savePathPrefix) {
        this.spiderDAO = spiderDAO;
        this.savePathPrefix = savePathPrefix;
    }

    @Override
    public void run() {
        logger.info("==============开始爬取==============");
        for(int page = 1; page <= 31 ; page++) {
            if(TopitSpider.build().isShutdown()){
                logger.info("==============终止操作==============");
                break;
            }
            logger.info("==============爬取第{}页==============", page);
            int total = 0;
            Document document = fetch(url + "/" + page);
            if(document != null){
                Elements elements = document.select("div[class='e m']");
                for(Element element : elements){
                    if(TopitSpider.build().isShutdown()){
                        logger.info("==============终止操作==============");
                        break;
                    }
                    String title = element.select(".title").get(0).text();
                    String href = element.select("img").get(0).parent().attr("href");
                    Document herfDocument = fetch("http://www.topit.cc" + href);
                    if(herfDocument != null){
                        Element itemTip = herfDocument.getElementById("item-tip");
                        String img = itemTip.attr("href");
                        String src = "http://www.topit.cc" + img;
                        SpiderImgPO check = spiderDAO.selectBySrc(src);
                        if(check != null){
                            logger.info("===========忽略已存在图片{}===========", src);
                            continue;
                        }
                        Date now = new Date();
                        String savePath = savePathPrefix
                                + SimpleDateUtil.shortFormat(now).replaceAll("-", "") + "/"
                                + now.getHours() + "/"
                                + System.currentTimeMillis() + HttpUtils.getTypeStr(src);
                        SpiderImgPO spiderImgPO = HttpUtils.download(src,savePath,
                                BaseConstant.UserAgents[rd.nextInt(BaseConstant.UserAgents.length)]);
                        if(spiderImgPO != null){
                            spiderImgPO.setTitle(title);
                            int a = spiderDAO.insert(spiderImgPO);
                            if(a == 1){
                                total ++;
                            }
                        }
                    }
                }
            }
            logger.info("==========第{}页总共抓取{}张图片==========", page, total);
        }
        logger.info("==============爬取完成==============");
    }

    private Document fetch(String url) {
        String str = null;
        for(int i = 1; i <= 5; i++){
            try {
                str = HttpUtils.get(url, null, null);
                break;
            } catch (HttpException e) {
                if(i == 5){
                    logger.error("网页数据抓取失败[{}]", url, e);
                    break;
                }
            }
        }
        if(str != null){
            Document document = Jsoup.parse(str, "utf-8");
            return document;
        }
        return null;
    }

}
