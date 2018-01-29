package com.nihaov.photograph.spider.model;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by nihao on 18/1/25.
 */
public class HProxy {

    private String host;
    private Integer port;

    private HProxy(String host,Integer port){
        this.host=host;
        this.port=port;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public static HProxy build(String host, Integer port){
        if(host == null || port == null)
            throw new NullPointerException("neither host nor port can be null");
        return new HProxy(host,port);
    }

    @Override
    public int hashCode() {
        return this.host.hashCode()+this.port.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HProxy){
            HProxy proxy = (HProxy) obj;
            if(proxy.getHost()!=null&&proxy.getPort()!=null){
                if(proxy.getHost().equals(this.host)
                        &&proxy.getPort()==this.port){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "host["+host+"],port["+port+"]";
    }

    public Proxy getProxy(){
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        return proxy;
    }
}
