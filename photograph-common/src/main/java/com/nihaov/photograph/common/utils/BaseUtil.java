package com.nihaov.photograph.common.utils;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nihao on 17/4/22.
 */
public class BaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(BaseUtil.class);

    public static List split(List list,Integer pageSize){
        int listSize = list.size();
        int page = (listSize + (pageSize-1))/ pageSize;
        List<List> result = new ArrayList<>();
        for(int i=0;i<page;i++){
            List subList = new ArrayList();
            for(int j=0;j<listSize;j++){
                int pageIndex = ( (j + 1) + (pageSize-1) ) / pageSize;
                if(pageIndex == (i + 1)) {
                    subList.add(list.get(j));
                }

                if( (j + 1) == ((j + 1) * pageSize) ) {
                    break;
                }
            }
            result.add(subList);
        }
        return result;
    }

    public static String getIpAddr(HttpServletRequest request) {
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

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    private static BASE64Encoder encoder = new BASE64Encoder();
    public static String getBase64(InputStream inputStream, Boolean close){
        String s = null;
        try {
            s = encoder.encode(toByteArray(inputStream));
        } catch (Exception e) {
            logger.error("转base64错误", e);
        }finally {
            if(close != null && close){
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.error("close InputStream error", e);
                    }
                }
            }
        }
        return s;
    }
}
