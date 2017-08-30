package com.test;

import com.nihaov.photograph.common.utils.AnimatedGifEncoder;
import com.nihaov.photograph.common.utils.DesUtil;
import com.nihaov.photograph.common.utils.FontText;
import com.nihaov.photograph.common.utils.ImageUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by nihao on 17/5/21.
 */
public class SimpleTest {
    private String a = "cweveverberbe";

    @Test
    public void test09() throws Exception {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        BufferedImage src1 = ImageIO.read(new File("/Users/nihao/Downloads/tou.png"));
        BufferedImage src2 = ImageIO.read(new File("/Users/nihao/Downloads/0.jpg"));
        e.start("/Users/nihao/Downloads/jjjjjj.gif");
        e.setDelay(100);
        e.addFrame(src1);
        e.addFrame(src2);
        e.finish();
    }

    @Test
    public void test01() throws ExecutionException, InterruptedException {
        FutureTask task1 = new FutureTask(new TThread("111111",5000));
        Thread t1 = new Thread(task1);
//        t1.join();

        FutureTask task2 = new FutureTask(new TThread("222222",0));
        Thread t2 = new Thread(task2);
//        t2.join();

        t1.start();
        t2.start();
        System.out.println("task1:"+task1.isDone());
        System.out.println("task2:"+task2.isDone());
        System.out.println(task2.get());
        System.out.println("task1:"+task1.isDone());
        System.out.println("task2:"+task2.isDone());
        System.out.println(task1.get());
        System.out.println("task1:"+task1.isDone());
        System.out.println("task2:"+task2.isDone());
    }

    class TThread implements Callable<String>{
        private String name;
        private long sle;

        TThread(String name,long sle){
            this.name = name;
            this.sle = sle;
        }

        @Override
        public String call() throws Exception {
            System.out.println(name+": 正在执行");
            Thread.sleep(sle);
            return "结果:"+name;
        }
    }

    @Test
    public void tt(){
        float a = (float) 10/3;
        System.out.println(a);
    }
    @Test
    public void tt通体() throws Exception {
        String s = DesUtil.encrypt("http:www.nihaov.com","key");
        System.out.println(s);
        //86aa1f568379ae4bdb40669acbdfbd876177a15ccffef7f2
    }
    @Test
    public void test(){
        String filePath = "/Users/nihao/Downloads/11111.png";
        String outPath = "/Users/nihao/Downloads/222.png";
//        ImageUtils.drawTextInImg(filePath, outPath, new FontText("哈哈哈哈哈哈洒出 v 额外 v 额外 v 额外", 2, "#CC2BAC", 25, "Serif"));
    }

}
