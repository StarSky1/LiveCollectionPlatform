package com.yj.serviceTest.threadTest;

/**
 * Java指令重排序
 * 预期结果输出4，但是因为指令重排序，写操作num=2可能重排在ready=true之后发生，
 * 导致最终输出结果可能为0
 * 如果使用volatile修饰ready则可避免指令重排序和内存不可见问题
 * volatile修饰保证volatile变量写操作之前的操作不会被重排序到volatile变量写操作之后
 * volatile变量读操作之后的操作不会被重排序到volatile变量读操作之前
 */
public class TestDirectiveReOrder {

    public static class ReadThread extends Thread {
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                if(ready){  //(1)
                    System.out.println(num+num);  //(2)
                }
            }
            System.out.println("read thread....");
        }
    }

    public static class Writethread extends Thread {
        public void run(){
            num=2;  //(3)
            ready=true;//(4)
            System.out.println("writeThread set over...");
        }
    }

    private static int num=0;
    private static boolean ready=false;

    public static void main(String[] args) throws InterruptedException {
        ReadThread rt=new ReadThread();
        rt.start();

        Writethread wt=new Writethread();
        wt.start();

        Thread.sleep(10);
        rt.interrupt();
        System.out.println("main exit");
    }
}
