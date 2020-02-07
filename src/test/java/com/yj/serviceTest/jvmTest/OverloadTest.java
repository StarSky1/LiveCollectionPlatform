package com.yj.serviceTest.jvmTest;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 重载方法匹配优先级 char->int->long->float->double->Character->Serializable         -> Object
 *                                                             ->Comparable<Character>
 *                                                             Serializable和Comparable重载方法优先级同级
 */
public class OverloadTest {

    public static void sayHello(Object arg){
        System.out.println("hello Object");
    }

//    public static void sayHello(int arg){
//        System.out.println("hello int");
//    }
//
//    public static void sayHello(long arg){
//        System.out.println("hello long");
//    }

    public static void sayHello(float arg){
        System.out.println("hello float");
    }

    public static void sayHello(double arg){
        System.out.println("hello double");
    }

    public static void sayHello(Character arg){
        System.out.println("hello Character");
    }

//    public static void sayHello(char arg){
//        System.out.println("hello char");
//    }

    public static void sayHello(char... arg){
        System.out.println("hello char ...");
    }

    public static void sayHello(Serializable arg){
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        sayHello('a');

        Calendar calendar=Calendar.getInstance();
        calendar.set(2020,1,4);
        Date now=calendar.getTime();
        System.out.println(calendar.getTime());
        calendar.set(2019,5,6);
        Date before=calendar.getTime();
        System.out.println(before);
        long diff=now.getTime()-before.getTime();
        long bottom=1000*60*60*24;
        System.out.println(diff/bottom);
    }
}
