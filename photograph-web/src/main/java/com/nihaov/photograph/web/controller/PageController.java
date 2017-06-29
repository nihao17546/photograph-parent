package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.nihaov.photograph.common.utils.DesEncrypt;
import com.nihaov.photograph.dao.IMGDAO;
import com.nihaov.photograph.dao.IVisitDAO;
import com.nihaov.photograph.pojo.constant.BaseConstant;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.vo.ImageVO;
import com.nihaov.photograph.service.IBaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nihao on 17/6/24.
 */
@Controller
public class PageController {
    @Resource
    private IMGDAO imgdao;
    @Resource
    private IVisitDAO visitDAO;

    private DesEncrypt desEncrypt = new DesEncrypt();

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");
        if(Strings.isNullOrEmpty(userAgent)){
            return "h";
        }
        String ip = getIpAddr(request);
        String host = request.getServerName();
        visitDAO.insert(ip,userAgent,host);
        Integer limit = 50;
        String limit_ = request.getParameter("limit");
        if(!Strings.isNullOrEmpty(limit_))
            limit = Integer.parseInt(limit_);
        List<IMGPO> list = imgdao.selectRandom(limit);
        List<IMGPO> list1 = new ArrayList<>();
        List<IMGPO> list2 = new ArrayList<>();
        List<IMGPO> list3 = new ArrayList<>();
        List<IMGPO> list4 = new ArrayList<>();
        float h1 = 0,h2 = 0,h3 = 0,h4 = 0;
        for(IMGPO imgpo:list){
            //对src加密
            imgpo.setSrc(desEncrypt.encrypt(imgpo.getSrc()));
            List put = list1;
            float h = (float)imgpo.getHeight()/imgpo.getWidth();
            if(h1<=h2){
                if(h1<=h3){
                    if(h1<=h4){
                        h1+=h;
                    }
                    else{
                        put = list4;
                        h4+=h;
                    }
                }
                else{
                    if(h3<=h4){
                        put = list3;
                        h3+=h;
                    }
                    else{
                        put = list4;
                        h4+=h;
                    }
                }
            }
            else{
                if(h2<=h3){
                    if(h2<=h4){
                        put = list2;
                        h2+=h;
                    }
                    else{
                        put = list4;
                        h4+=h;
                    }
                }
                else{
                    if(h3<=h4){
                        put = list3;
                        h3+=h;
                    }
                    else{
                        put = list4;
                        h4+=h;
                    }
                }
            }
            put.add(imgpo);
        }
        model.addAttribute("pic1",list1);
        model.addAttribute("pic2",list2);
        model.addAttribute("pic3",list3);
        model.addAttribute("pic4",list4);
        model.addAttribute("md",new Random().nextInt(6));
        model.addAttribute("eckey",desEncrypt.getKeyStr());
        model.addAttribute("hot",BaseConstant.hot);
        return "index";
    }



    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
