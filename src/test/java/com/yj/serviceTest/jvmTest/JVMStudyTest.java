package com.yj.serviceTest.jvmTest;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class JVMStudyTest {

    @Test
    public void ClassLoaderTest() throws Exception{
        ClassLoader myLoader=new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is=getClass().getResourceAsStream(fileName);
                    if(is == null){
                        return super.loadClass(name);
                    }
                    byte[] b=new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                }catch (IOException e){
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj=myLoader.loadClass("com.yj.serviceTest.JVMStudyTest.ClassLoaderTest").
                newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof JVMStudyTest.ClassLoaderTest);
    }

    class ClassLoaderTest {

    }

}


