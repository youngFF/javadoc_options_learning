package com.hyena.test;

import com.hyena.classloader.JarClassLoader;
import org.junit.Test;

import java.io.IOException;

public class JarClassLoaderTest {


    /**
     * 用来测试JarClassLoader类的功能
     * @throws IOException
     * @throws InterruptedException
     *
     * 可以多使用几组参数做测试： 修改groupId，artifactId，version，className
     *
     *   #1 ： asm，asm，3.1，org.objectweb.asm.Label
     *   #2 ： junit , junit , 3.8.1 , junit.runner.BaseTestRunner
     */
    @Test
    public void loadTest() throws IOException, InterruptedException {
        String groupId = "asm";
        String artifactId = "asm";
        String version = "3.1";
        String className = "org.objectweb.asm.Label";

        JarClassLoader loader = new JarClassLoader();

        Class<?> aClass = loader.mavenClassLoad(groupId, artifactId, version, className);
        System.out.println(className + " name: " + aClass.getName());
        System.out.println(className + " protectionDomain: " + aClass.getProtectionDomain());
        System.out.println(className + " classloader: " + aClass.getClassLoader());


        // 选择一个对象作为lock，使用taobao arthas工具查看 classloader以及载入的类
        synchronized (groupId) {
            groupId.wait();
        }

//        name                                       numberOfInstances  loadedCountTotal
//        BootstrapClassLoader                       1                  1494
//        com.taobao.arthas.agent.ArthasClassloader  1                  1029
//        sun.misc.Launcher$AppClassLoader           1                  147
//        sun.reflect.DelegatingClassLoader          6                  6
//        com.hyena.classloader.JarClassLoader       1                  1
//        通过arthas可以看到，JarClassLoader载入了一个类
    }
}
