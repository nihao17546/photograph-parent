package com.nihaov.photograph.web.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.nihaov.photograph.pojo.vo.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by nihao on 16/10/22.
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("请求路径:"+httpServletRequest.getServletPath(),e);

        if("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))){
            DataResult jsonResult=new DataResult();
            jsonResult.setCode(500);
            jsonResult.setMessage(e.getClass().getName()+" "+e.getMessage());
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            try(PrintWriter out=httpServletResponse.getWriter()){
                out.append(JSON.toJSONString(jsonResult));
            }catch (Exception e1){
                logger.error(e1.getMessage(),e1);
            }
            return null;
        }
        else{
            Map<String,Object> map= Maps.newHashMap();
            map.put("mess",e.getMessage());
            return new ModelAndView("error",map);
        }
    }
}
