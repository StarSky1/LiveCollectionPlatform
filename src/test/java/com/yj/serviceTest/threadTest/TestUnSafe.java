package com.yj.serviceTest.threadTest;

import sun.misc.Unsafe;

/**
 * 运行报错，因为Unsafe类是rt.jar包中的类，rt.jar包中的类是通过Bootstrap classloader加载器加载的，
 * 而Unsafe得调用者，main方法所属的类TestUnsafe则是由Application classloader加载的
 * 在getUnsafe()方法中，做了callerClass的classloader与bootstrap加载器的比较，
 * 该方法名是isSystemDomainLoader，如果callerClass的类加载器不是bootstrap加载器，
 * 则抛出异常
 *
 * */
public class TestUnSafe {

    //获取Unsafe的实例（2.2.1）
    static final Unsafe unsafe=null; //=Unsafe.getUnsafe();

    //记录变量state在类TestUnsafe中的偏移值(2.2.2)
    static final long stateOffset=0;

    //变量（2.2.3）
    private volatile long state=0;

    static {
        try{
            //获取state变量在类TestUnSafe中的偏移值（2.2.4）
            //stateOffset=unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        //创建实例，并且设置state值为1(2.2.5)
        TestUnSafe test=new TestUnSafe();
        //(2.2.6)
        Boolean success=unsafe.compareAndSwapInt(test,stateOffset,0,1);
        System.out.println(success);
    }
}
