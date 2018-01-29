package com.nihaov.photograph.spider.controller;

import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.spider.model.JsonResult;
import com.nihaov.photograph.spider.model.SpiderException;
import com.nihaov.photograph.spider.model.enmus.SpiderSourceEnum;
import com.nihaov.photograph.spider.service.ISpiderService;
import com.nihaov.photograph.spider.util.ImageHandler;
import com.nihaov.photograph.spider.util.TopitSpider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by nihao on 18/1/25.
 */
@Controller
@RequestMapping("/spider")
public class SpiderController {
    @Resource
    private ISpiderDAO spiderDAO;
    @Resource
    private ISpiderService spiderService;
    @Value("#{configProperties['savePathPrefix']}")
    private String savePathPrefix;

    @PostConstruct
    public void init(){
        TopitSpider.build().setSpiderDAO(spiderDAO);
        TopitSpider.build().setSavePathPrefix(savePathPrefix);
        ImageHandler.build().setSpiderDAO(spiderDAO);
        ImageHandler.build().setSpiderService(spiderService);
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/status")
    @ResponseBody
    public String status(){
        boolean b = TopitSpider.build().isRunning();
        Long count = spiderDAO.selectCountByFlag(0);
        boolean c = ImageHandler.build().isRunning();
        return JsonResult.success().pull("status", b ? "正在运行中..." : "休眠中...")
                .pull("count", count)
                .pull("imgStatus", c ? "正在运行中..." : "休眠中...").json();
    }

    @RequestMapping("/start")
    @ResponseBody
    public String start(){
        try {
            TopitSpider.build().start(SpiderSourceEnum.TOPIT);
            return JsonResult.success("操作成功").json();
        } catch (SpiderException e) {
            return JsonResult.fail(e.getMessage()).json();
        }
    }

    @RequestMapping("/stop")
    @ResponseBody
    public String stop(){
        try {
            TopitSpider.build().stop();
            return JsonResult.success("操作成功").json();
        } catch (SpiderException e) {
            return JsonResult.fail(e.getMessage()).json();
        }
    }

    @RequestMapping("/startSolr")
    @ResponseBody
    public String startSolr(){
        try {
            ImageHandler.build().start();
            return JsonResult.success("操作成功").json();
        } catch (SpiderException e) {
            return JsonResult.fail(e.getMessage()).json();
        }
    }

    @RequestMapping("/stopSolr")
    @ResponseBody
    public String stopSolr(){
        try {
            ImageHandler.build().stop();
            return JsonResult.success("操作成功").json();
        } catch (SpiderException e) {
            return JsonResult.fail(e.getMessage()).json();
        }
    }

}
