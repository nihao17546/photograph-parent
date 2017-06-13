package com.test;

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

}
