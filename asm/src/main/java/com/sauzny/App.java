package com.sauzny;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        String className = "com.sauzny.asm.Target";
        String fileSeparator = System.getProperty("file.separator");
        String classPath = System.getProperty("java.class.path").split(System.getProperty("path.separator"))[0] + fileSeparator + className.replaceAll("\\.", "\\\\") + ".class";

        System.out.println(classPath);

    }
}
