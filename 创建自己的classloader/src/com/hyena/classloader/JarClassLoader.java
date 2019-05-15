package com.hyena.classloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * JarClassLoader: 通过给定的groupId,artifactId,Version,qualified name
 * 来动态加载类，并用arthas来查看
 * <p>
 * 步骤： 1.通过groupId，artifactId，version，来定位jar包所在位置
 * 2.解析qualified name ，找到相应类的字节码文件，然后载入
 * 3.使用ClassLoader.define()来定义类
 */
public class JarClassLoader extends ClassLoader{
    //设置环境参数
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String MAVEN_HOME = USER_HOME + "/.m2/repository/";
    public static final String JAR_PROTOCOL = "jar:file:";





    public Class<?> mavenClassLoad(String groupId, String artifactId, String version, String qualified) throws IOException {

        System.out.println("载入: " + qualified);
        System.out.println("位置: " + "[groupId: " + groupId+
                            " artifactId: " + artifactId +
                            " version: " + version + " ]");
        return getClassByByteCodes(groupId, artifactId, version, qualified);
    }


    /**
     * @param groupId
     * @param artifactId
     * @param version
     * @param qualified 传入qualified name ，eg： com.hyena.Test
     * @return
     */
    public Class<?> getClassByByteCodes(String groupId,String artifactId,String version,String qualified) throws IOException {
        String classPath = getClassFileName(groupId, artifactId, version, qualified);

        URL url = new URL(classPath);
        System.out.println(url);
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

        return defineClass(qualified,byteCodes,0,offset) ;
    }


    private String getClassFileName(String groupId, String artifactId,String version, String qualified) {
        String classFileName = JAR_PROTOCOL+ MAVEN_HOME + groupId + "/" + artifactId
                + "/" + version + "/"+ (artifactId+ "-" + version + ".jar")+"!" + transformQualifiedName(qualified);
        return classFileName;
    }

    /**
     * com.hyena.test ---> com/hyena/Test
     * @param qualifedName  eg: com.hyena.Test
     * @return
     */
    private String transformQualifiedName(String qualifedName){
        String[] strs = qualifedName.split("\\.");
        String transferedName = "";

        for (int i = 0; i < strs.length; i++) {
            transferedName += "/" + strs[i];
        }

        return transferedName + ".class";
    }





}
