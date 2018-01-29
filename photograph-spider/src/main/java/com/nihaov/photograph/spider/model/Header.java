package com.nihaov.photograph.spider.model;

import java.util.HashMap;

/**
 * Created by nihao on 18/1/25.
 */
public class Header<K,V> extends HashMap<K,V> {
    public Header<K,V> pull(K k, V v){
        put(k, v);
        return this;
    }
    public static Header build(){
        return new Header();
    }
}
