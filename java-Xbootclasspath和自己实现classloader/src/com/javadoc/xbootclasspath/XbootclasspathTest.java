package com.javadoc.xbootclasspath;

import com.jayway.jsonpath.Filter;

import java.net.Inet4Address;

public class XbootclasspathTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("TapestryJsonProvider: " + com.jayway.jsonpath.spi.json.TapestryJsonProvider.class.getClassLoader());
        System.out.println("filter classloader: " + Filter.class.getClassLoader());
        System.out.println("Object classloader: " + Object.class.getClassLoader());
        System.out.println("String classloader: " + String.class.getClassLoader());
        System.out.println("Inet classLoader:" + Inet4Address.class.getClassLoader());
        Object lock = new Object();

        // 使用wait，让线程暂停。然后使用taobao arthas工具查看程序状态
        synchronized (lock) {
            lock.wait();
        }
    }
}
