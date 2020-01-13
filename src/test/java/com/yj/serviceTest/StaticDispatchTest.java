package com.yj.serviceTest;

/**
 * 方法静态分派演示
 */
public class StaticDispatchTest {

    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    public void sayHello(Human guy){
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy){
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Woman guy){
        System.out.println("hello,lady!");
    }

    public static void main(String[] args) {
        Human man=new Man();
        Human woman=new Woman();
        StaticDispatchTest sr=new StaticDispatchTest();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
