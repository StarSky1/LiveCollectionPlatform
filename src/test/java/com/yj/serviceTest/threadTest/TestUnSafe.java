package com.yj.serviceTest.threadTest;

import sun.misc.Unsafe;

public class TestUnSafe {

    //获取Unsafe的实例（2.2.1）
    static final Unsafe unsafe=Unsafe.getUnsafe();

    //记录变量state在类TestUnsafe中的偏移值(2.2.2)
    static final long stateOffset;

    //变量（2.2.3）
    private volatile long state=0;

    static {
        try{
            //获取state变量在类TestUnSafe中的偏移值（2.2.4）
            stateOffset=unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));
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
