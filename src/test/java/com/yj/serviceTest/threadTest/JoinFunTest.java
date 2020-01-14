package com.yj.serviceTest.threadTest;

import java.util.Date;

public class JoinFunTest {

    public static void main(String[] args) throws InterruptedException {
        long startTime=new Date().getTime();
        Thread threadOne=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("child threadOne over!");
            }
        });

        Thread threadTwo=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("child threadTwo over!");
            }
        });

        //启动子线程
        threadOne.start();
        threadTwo.start();

        System.out.println("wait all child thread over!");

        //等待子线程执行完毕，返回
        threadOne.join();
        threadTwo.join();

        System.out.println("all child thread over!");
        System.out.println("spend time: "+(new Date().getTime()-startTime)/1000+"s");
    }
}
