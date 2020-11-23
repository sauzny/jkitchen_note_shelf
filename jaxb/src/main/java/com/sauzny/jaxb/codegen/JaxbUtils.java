package com.sauzny.jaxb.codegen;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class JaxbUtils {

    private JaxbUtils() {
    }

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String DIR_PATH = PROJECT_PATH + "\\src\\main\\java";
    private static final String XSD_FILE_PATH = PROJECT_PATH + "\\xsd\\myjaxb.xsd";
    private static final String CUSTOM_PATH = "\\com\\sauzny\\jaxb\\codegen\\code";
    private static final String CUSTOM_FULL_PATH = DIR_PATH + CUSTOM_PATH;
    private static final String CUSTOM_PACKAGE_NAME = "com.sauzny.jaxb.codegen.code";

    public static boolean isExistence(){
        return Objects.requireNonNull(Paths.get(CUSTOM_FULL_PATH).toFile().listFiles()).length > 0;
    }

    public static void clean() {

        System.out.println("删除 " + CUSTOM_FULL_PATH + " 中的文件");

        Stream.of(Objects.requireNonNull(Paths.get(CUSTOM_FULL_PATH).toFile().listFiles())).forEach(File::delete);
    }

    public static void create() {

        System.out.println("在 " + CUSTOM_FULL_PATH + " 中生成文件");

        List<String> commands = new ArrayList<>();
        commands.add("D:\\Program Files\\java\\jdk1.8.0_131\\bin\\xjc.exe");
        commands.add("-d");
        commands.add(DIR_PATH);
        commands.add("-p");
        commands.add(CUSTOM_PACKAGE_NAME);
        commands.add(XSD_FILE_PATH);
        commands.add("-encoding");
        commands.add("utf8");
        commands.add("-enableIntrospection");


        ProcessBuilder pb = new ProcessBuilder();
        pb.command(commands);
        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fixToString() {

        Stream.of(Objects.requireNonNull(Paths.get(CUSTOM_FULL_PATH).toFile().listFiles()))
                .filter(file -> file.getName().endsWith("java") && !file.getName().startsWith("ObjectFactory"))
                .forEach(JaxbUtils::fixToString);
        /*
        while(errorFileList.size() > 0){
            errorFileList.forEach(file -> {
                if(fixToString(file)){
                    errorFileList.remove(file);
                }
            });
        }
        */
    }

    private static void fixToString(File file){
        try {
            String javaFilePath = file.getPath();
            String classShortName = file.getName().split("\\.")[0];
            String className = CUSTOM_PACKAGE_NAME + "." + classShortName;

            List<String> content = Files.readAllLines(Paths.get(javaFilePath), StandardCharsets.UTF_8);

            List<String> append = new ArrayList<>();
            append.add("\t@Override");
            append.add("\tpublic String toString() {");
            append.add("\t\treturn \"" + classShortName + "[\"+");

            //Class<?> clazz = loadJavaFile(DIR_PATH, javaFilePath, className);
            Class<?> clazz = Class.forName(className);
            System.out.println("增加 toString() 函数 -> " + javaFilePath);
            for(int i=0; i<clazz.getDeclaredFields().length; i++){

                String d = ", ";
                if(i == 0) d = "";

                String fieldName = clazz.getDeclaredFields()[i].getName();
                append.add("\t\t\t\t\"" + d + fieldName + " = \" + " + fieldName + " +");
            }

            append.add("\t\t\t\"]\";");
            append.add("\t}");
            append.add("");

            content = content.subList(0, content.size()-1);
            content.addAll(append);
            content.add("}");

            Files.write(Paths.get(javaFilePath), content, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private static Class<?> loadJavaFile(String dir, String javaFilePath, String className) throws IOException, ClassNotFoundException {

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = standardJavaFileManager.getJavaFileObjects(javaFilePath);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, iterable);
        task.call();
        standardJavaFileManager.close();

        return new JaxbClassLoader(dir).loadClass(className);
    }
    */

    public static void main(String[] args) {

        // 需要手动删除旧文件，生成新的文件

        if(JaxbUtils.isExistence()){
            JaxbUtils.fixToString();
        }else{
            //JaxbUtils.clean();
            JaxbUtils.create();
        }
    }
}
