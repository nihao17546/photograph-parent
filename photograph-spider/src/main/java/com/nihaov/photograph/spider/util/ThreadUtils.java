package com.nihaov.photograph.spider.util;

/**
 * Created by nihao on 18/1/25.
 */
public class ThreadUtils {
    public static void sleep(long seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
