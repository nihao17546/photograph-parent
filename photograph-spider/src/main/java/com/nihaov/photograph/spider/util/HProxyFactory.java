package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.spider.model.HProxy;
import com.nihaov.photograph.spider.model.Header;
import org.apache.http.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by nihao on 18/1/25.
 */
public class HProxyFactory {
    private static final Logger logger = LoggerFactory.getLogger(HProxyFactory.class);

    private static final String grabUrl = "http://www.xicidaili.com/nn/";
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0";

    private static final ConcurrentLinkedQueue<HProxy> queue = new ConcurrentLinkedQueue();
    static {
        fetch();
    }

    public static HProxy create(){
        HProxy hProxy = queue.poll();
        if(hProxy == null){
            synchronized (queue){
                hProxy = queue.poll();
                if(hProxy == null){
                    fetch();
                    hProxy = queue.poll();
                }
            }
        }
        return hProxy;
    }

    private static void fetch() {
        Header header = Header.build().pull("User-Agent", userAgent);
        String responseStr = null;
        for(int i = 1; i <=5; i++){
            try {
                responseStr = HttpUtils.get(grabUrl, header, null);
                break;
            } catch (HttpException e) {
                logger.error("第{}次抓取代理失败", i, e);
                ThreadUtils.sleep(10);
            }
        }
        if(responseStr != null){
            List<HProxy> list = new ArrayList<>();
            Document document = Jsoup.parse(responseStr);
            Element element = document.getElementById("ip_list");
            Elements elements = element.getElementsByTag("tr");
            for(Element ele : elements){
                if(list.size() >= 50){
                    break;
                }
                Elements tds = ele.getElementsByTag("td");
                if(tds.size() == 0){
                    continue;
                }
                Element host = tds.get(1);
                Element port = tds.get(2);
                if(host != null && port != null){
                    list.add(HProxy.build(host.text(),Integer.parseInt(port.text())));
                }
            }
            logger.info("总共抓取代理{}个", list.size());
            queue.addAll(list);
        }
    }
}
