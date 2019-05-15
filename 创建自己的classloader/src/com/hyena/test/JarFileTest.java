package com.hyena.test;


import org.junit.Test;
import sun.misc.URLClassPath;

import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.jar.JarFile;

/**
 * 解析Jar文件.
 *
 */
public class JarFileTest {


    public static final String MAVEN_HOME = "/home/hyena/.m2/repository/";
    public static final String JAR_SUFFIX = ".jar";


    /**
     * 用来从某个jar包里面读取某个class文件，然后写到一个新的class文件中，然后
     * 使用javap -verbose XXX.class 查看正确性
     * success
     * @throws IOException
     */
    @Test
    public void readJarFile() throws IOException {
        String entry = generateJarName("junit", "junit", "3.8.1"
                ) +"!/" +"junit/runner/BaseTestRunner.class";

        URL url = new URL("jar:file:"+entry);
        JarURLConnection jarConnection = (JarURLConnection) url.openConnection();

        InputStream inputStream = jarConnection.getInputStream();

        //创建一个ByteBuffer来装字节码，本来我想使用ByteBuffer 来装子字节码，不过
        //出现了java.nio.BufferOverFlowException
        //ByteBuffer buffer = ByteBuffer.allocate(0);

        int tmp = -1 ;

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("/home/hyena/hyena.class"));


        while ((tmp = (inputStream.read())) != -1) {
            outputStream.write(tmp);
        }



        // 关闭流
        inputStream.close();
        outputStream.close();
    }


    /**
     * 将class文件的字节码放在byte[]
     */
    @Test
    public void byteCodesInByteArrayTest() throws IOException {
        String entry = generateJarName("junit", "junit", "3.8.1"
                ) +"!/" +"junit/runner/BaseTestRunner.class";

        URL url = new URL("jar:file:"+entry);
        JarURLConnection jarConnection = (JarURLConnection) url.openConnection();

        InputStream inputStream = jarConnection.getInputStream();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] byteCodes = new byte[2 * bufferedInputStream.available()];

        int tmp = -1 ;
        int offset = 0 ;

        while ((tmp = inputStream.read()) != -1) {
            byteCodes[offset++] = (byte) tmp;
        }

        bufferedInputStream.close();


    }






    /**通过给定的groupdId ， artifactId，version ，qualifiedName确定jar包位置
     * @param groupId
     * @param artifactId
     * @param version
     * @return
     */
    public String generateJarName(String groupId, String artifactId, String version
                                  ) {

        String jarName = artifactId + "-" + version + JAR_SUFFIX;
        return MAVEN_HOME + groupId + "/" + artifactId + "/" + version + "/" + jarName ;
    }




}
