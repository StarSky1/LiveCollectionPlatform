package com.yj.serviceTest.threadTest;

/**
 * ThreadLocal不支持继承性，但InheritableThreadLocal支持（InheritableThreadLocal extends ThreadLocal）
 * Thread类有两个成员变量，分别是 threadlocals 和 inheritableThreadlocals
 * 他们都是ThreadLocalMap类型的变量，ThreadLocalMap是一个定制化的HashMap
 */
public class InheritableThreadLocalTest {

    //(1)创建线程变量
//    public static ThreadLocal<String> threadLocal=new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal=new InheritableThreadLocal<>();

    public static void main(String[] args) {
        //(2)设置线程变量
        threadLocal.set("hello world");
        //(3)启动子线程
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //(4)子线程输出线程变量的值
                System.out.println("thread:"+threadLocal.get());
            }
        });
        thread.start();

        //(5) 主线程输出线程变量的值
        System.out.println("main:"+threadLocal.get());
    }
}
