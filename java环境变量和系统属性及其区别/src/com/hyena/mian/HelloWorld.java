package com.hyena.mian;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class HelloWorld {


    public static void main(String[] args) throws IOException {

        // 获得环境变量
        Map<String, String> env = System.getenv();
        for (Map.Entry entry : env.entrySet()) {
            System.out.println(entry.getKey() + " :" + entry.getValue());
        }

        // 获得系统属性
        System.getProperties().store(new FileOutputStream("system.properties"), "message...");
    }
}
