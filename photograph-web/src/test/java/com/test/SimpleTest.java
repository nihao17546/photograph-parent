package com.test;

import com.nihaov.photograph.common.utils.DesUtil;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by nihao on 17/5/21.
 */
public class SimpleTest {

    @Test
    public void test01() throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(new TThread("hello"));
        new Thread(task).start();
        System.out.println(task.get());
    }

    class TThread implements Callable<Integer>{
        private String name;

        TThread(String name){
            this.name = name;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(name+":-------------");
            return name.hashCode();
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

}
