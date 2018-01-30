import com.nihaov.photograph.spider.util.UnicodeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by nihao on 18/1/29.
 */
public class SimpleTest {
    @Test
    public void test01() throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpPost post = new HttpPost("http://juju.la/blog/hot");
//        BasicClientCookie cookie = new BasicClientCookie("sessionid", "l2w14641ac396qv254xqv7k1j2dewtid");
//        cookie.setPath("/");
//        cookie.setDomain("juju.la");
//        cookieStore.addCookie(cookie);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String s = EntityUtils.toString(httpEntity, "utf-8");
        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            System.out.println("Local cookie: " + cookies.get(i));
        }
        System.out.printf("---------");
    }
    @Test
    public void test02() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("r1");
                System.out.println("r2");
            }
        });
        t.start();
        System.out.println("m1");
        t.join();
        System.out.println("m2");
    }
    @Test
    public void test03(){
        first();
    }
    class Value{
        public int i = 15;
    }
    private void first(){
        int i = 5;
        Value v = new Value();
        v.i = 25;
        second(v, i);
        System.out.println(v.i);
        System.out.println(i);
    }

    private void second(Value v, int i) {
        i = 0;
        v.i = 20;
        Value val = new Value();
        v = val;
        System.out.println(v.i + " " + i);
    }

    private void qwe(String s){
        s = "123";
        System.out.println(s);
    }

    @Test
    public void test04(){
        String s = "qwe";
        qwe(s);
        System.out.println(s);
    }

    @Test
    public void test05(){
        System.out.println(UnicodeUtils.unicode2String("\\u516b\\u91cd\\u6a31\\u548c\\u7ec8\\u7ed3\\u7684\\u70bd\\u5929\\u4f7f"));
    }
    @Test
    public void test06(){
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("a", "b");
        cookie.setPath("/");
        cookie.setDomain("juju.la");
//        cookie.setExpiryDate(new Date(new Date().getTime() - 1000));
        cookieStore.addCookie(cookie);
        System.out.println("-----");
    }
}
