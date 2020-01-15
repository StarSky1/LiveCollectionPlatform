package com.yj.serviceTest.threadTest;

/**
 * TestForContent比TestForContent2快，（1）耗时10ms以下，(2)耗时10ms以上。
 * 原因：当顺序访问数组里面元素时，如果当前元素在缓存没有命中，那么会从主内存一下子读取
 * 后续后续若干个元素到缓存，也就是一次内存访问可以让后面多次访问直接在cpu缓存中命中。而代码(2)
 * 是跳跃式访问数组元素的，不是顺序的，这破坏了程序访问的局部性原则，并且缓存时有容量控制的，当缓存
 * 满了时会根据一定淘汰算法替换缓存行，这会导致从内存置换过来的缓存行的元素还没等到被读取就被替换掉了
 */
public class TestForContent {

    static final int LINE_NUM=1024;
    static final int COLUM_NUM=1024;

    public static void main(String[] args) {
        long[][] array=new long[LINE_NUM][COLUM_NUM];

        long startTime=System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUM_NUM; j++) {
                array[i][j]=i*2+j;
            }
        }
        long endTime=System.currentTimeMillis();
        long cacheTime=endTime-startTime;
        System.out.println("cache time:"+cacheTime);
    }
}
