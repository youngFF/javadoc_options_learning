package com.hyena.main;

import org.junit.Test;
import org.junit.matchers.JUnitMatchers;


/**
  命令行：
 java -cp com/hyena/main/:/Users/hyena/.m2/repository/junit/junit/4.12/junit-4.12.jar
          :/Users/hyena/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
          com/hyena/main/HelloWorld

 注意：主类一定是如上的全限定名称，并且没有.class文件后缀。因为查找类的时候是在三类路径中查找
 (sun.boot.class.path,java.ext.dirs,java.class.path)。
 而java.class.path，可以是jar，zip，目录。对于这个HelloWorld主类来说，上面的命令并不能找到
 这个类。因为这个类是在这个目录下：/Users/hyena/Desktop/javadoc参数学习/out/production/java命令语法

 所以在-cp中把这个加上 -cp /Users/hyena/Desktop/javadoc参数学习/out/production/java命令语法:....


 还有一个问题：把下面的代码第四行注释掉，并且在-cp中不加入hamcrest-core-1.3.jar，能成功运行。
 但是在JUnitMatchers的代码中，import了hamcrest-core中的类。

 而是在调用JUnitMatchers.hasItem()时，在-cp不加入hamcrest-core时会出错。


 结论：当载入一个类时，如果没有调用与第三方类有关的 方法，属性等等时，不会载入第三方类；
 而是等到直接使用的时候才会import。




 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");   // line 1
        System.out.println(Test.class.getCanonicalName());  // line 2
        System.out.println(JUnitMatchers.class.getCanonicalName()); // line 3
        System.out.println(JUnitMatchers.hasItem(new Object())); // line 4
    }



}
