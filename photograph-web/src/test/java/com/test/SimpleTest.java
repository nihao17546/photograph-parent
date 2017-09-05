package com.test;

import com.nihaov.photograph.common.utils.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by nihao on 17/5/21.
 */
public class SimpleTest {
//    private String a = "cweveverberbe";
//
//    @Test
//    public void test09() throws Exception {
//        AnimatedGifEncoder e = new AnimatedGifEncoder();
//        BufferedImage src1 = ImageIO.read(new File("/Users/nihao/Downloads/tou.png"));
//        BufferedImage src2 = ImageIO.read(new File("/Users/nihao/Downloads/0.jpg"));
//        e.start("/Users/nihao/Downloads/jjjjjj.gif");
//        e.setDelay(100);
//        e.addFrame(src1);
//        e.addFrame(src2);
//        e.finish();
//    }
//
//    @Test
//    public void test01() throws ExecutionException, InterruptedException {
//        FutureTask task1 = new FutureTask(new TThread("111111",5000));
//        Thread t1 = new Thread(task1);
////        t1.join();
//
//        FutureTask task2 = new FutureTask(new TThread("222222",0));
//        Thread t2 = new Thread(task2);
////        t2.join();
//
//        t1.start();
//        t2.start();
//        System.out.println("task1:"+task1.isDone());
//        System.out.println("task2:"+task2.isDone());
//        System.out.println(task2.get());
//        System.out.println("task1:"+task1.isDone());
//        System.out.println("task2:"+task2.isDone());
//        System.out.println(task1.get());
//        System.out.println("task1:"+task1.isDone());
//        System.out.println("task2:"+task2.isDone());
//    }
//
//    class TThread implements Callable<String>{
//        private String name;
//        private long sle;
//
//        TThread(String name,long sle){
//            this.name = name;
//            this.sle = sle;
//        }
//
//        @Override
//        public String call() throws Exception {
//            System.out.println(name+": 正在执行");
//            Thread.sleep(sle);
//            return "结果:"+name;
//        }
//    }
//
//    @Test
//    public void tt(){
//        float a = (float) 10/3;
//        System.out.println(a);
//    }
//    @Test
//    public void tt通体() throws Exception {
//        String s = DesUtil.encrypt("http:www.nihaov.com","key");
//        System.out.println(s);
//        //86aa1f568379ae4bdb40669acbdfbd876177a15ccffef7f2
//    }
//    @Test
//    public void test(){
//        String filePath = "/Users/nihao/Downloads/11111.png";
//        String outPath = "/Users/nihao/Downloads/222.png";
////        ImageUtils.drawTextInImg(filePath, outPath, new FontText("哈哈哈哈哈哈洒出 v 额外 v 额外 v 额外", 2, "#CC2BAC", 25, "Serif"));
//    }
//    @Test
//    public void bubbleSortTest(){
//        int[] arr = new int[]{7,98,5,0,22,54,1};
//        sout(bubbleSort(arr));
//    }
//
//    public void sout(int[] arr){
//        for (int a : arr){
//            System.out.print(a + ",");
//        }
//    }
//
//    public int[] bubbleSort(int[] arr){
//        for(int i=0;i<arr.length-1;i++){
//            for(int j=0;j<arr.length-i-1;j++){
//                if(arr[j]>arr[j+1]){
//                    int temp = arr[j];
//                    arr[j] = arr[j+1];
//                    arr[j+1] = temp;
//                }
//            }
//        }
//        return arr;
//    }
//
//    @Test
//    public void dasda() throws UnsupportedEncodingException {
////        W43qSKG_juEvrHXffVzwQ1pDByogWfdOFObp_xd3:bfv-E_8jIOX3A10ZE-Q3Isn0BTc=:eyJzY29wZSI6Im15ZGF0YSIsImRlYWRsaW5lIjoxNTA0NjA3NjMzfQ==
//        String s = "暖暖玥兒\uD83D\uDC93";
//        String s2 = "哈哈哈";
//        System.out.println(UTF8Utils.contains4BytesChar2(s));
//        System.out.println(UTF8Utils.contains4BytesChar2(s2));
//        System.out.println(new String(UTF8Utils.remove4BytesUTF8Char(s),"UTF-8"));
//        System.out.println(UTF8Utils.bytesToHex(s2.getBytes()));
//    }
//
//    @Test
//    public void cascascd(){
//        while (true){
//            Auth auth = Auth.create("W43qSKG_juEvrHXffVzwQ1pDByogWfdOFObp_xd3", "GDr17GJwC5Qiotw30cfXvG-fG6urbM5n4Jt_UZ63");
//            String token = auth.uploadToken("mydata");
//            System.out.println(token);
//        }
//    }
//
//    @Test
//    public void qiniu() throws Exception{
////            <form method="post" action="http://upload.qiniu.com/"
////        enctype="multipart/form-data">
////      <input name="key" type="hidden" value="<resource_key>">
////      <input name="x:<custom_name>" type="hidden" value="<custom_value>">
////      <input name="token" type="hidden" value="<upload_token>">
////      <input name="file" type="file" />
////      <input name="crc32" type="hidden" />
////      <input name="accept" type="hidden" />
////    </form>
//
//        Configuration configuration = new Configuration();
//        UploadManager uploadManager = new UploadManager(configuration);
//        Auth auth = Auth.create("", "-");
//        String token = auth.uploadToken("mydata");
////        upload(uploadManager, "/Users/nihao/Downloads/11111.png", token);
//
////        File file = new File("/Users/nihao/Downloads/11111.png");
////        CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
////        HttpEntity reqEntity = MultipartEntityBuilder.create()
////                .addBinaryBody("file", file,
////                        ContentType.MULTIPART_FORM_DATA, "test.png")
////                .addTextBody("token", token)
////                .build();
////        HttpPost httpPost = new HttpPost("http://up-z2.qiniu.com");
////        httpPost.setEntity(reqEntity);
////        HttpClientUtils.config(httpPost);
////        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
////        HttpEntity entity = httpResponse.getEntity();
//        System.out.println("----");
//    }
//
//    //普通上传
//    public void upload(UploadManager uploadManager, String FilePath, String token) throws IOException{
//        try {
//            //调用put方法上传
//            Response res = uploadManager.put(FilePath, "test.png", token);
//            //打印返回的信息
//            System.out.println(res.bodyString());
//        } catch (QiniuException e) {
//            Response r = e.response;
//            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
//            try {
//                //响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                //ignore
//            }
//        }
//    }
}
