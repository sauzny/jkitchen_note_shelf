package org.example;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// 在需要查看所有依赖信息的项目下执行
// mvn dependency:list
// 获取如下信息，给 mvnDependencyListStr 赋值

public class DependencyCopyer {
    // 本地maven仓库地址
    private static String sourcePathStr = "F:\\m2";
    // 复制所有依赖的目标位置，必须唯一
    private static String projectId = UUID.randomUUID().toString().substring(0, 8);
    private static String targetPathStr = "F:\\m2temp\\"+projectId;

    private static String mvnDependencyListStr = "[INFO]    org.springframework.boot:spring-boot-starter:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework:spring-context:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-autoconfigure:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-logging:jar:2.4.5:compile\n" +
            "[INFO]    ch.qos.logback:logback-classic:jar:1.2.3:compile\n" +
            "[INFO]    ch.qos.logback:logback-core:jar:1.2.3:compile\n" +
            "[INFO]    org.apache.logging.log4j:log4j-to-slf4j:jar:2.13.3:compile\n" +
            "[INFO]    org.apache.logging.log4j:log4j-api:jar:2.13.3:compile\n" +
            "[INFO]    org.slf4j:jul-to-slf4j:jar:1.7.30:compile\n" +
            "[INFO]    jakarta.annotation:jakarta.annotation-api:jar:1.3.5:compile\n" +
            "[INFO]    org.springframework:spring-core:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-jcl:jar:5.3.6:compile\n" +
            "[INFO]    org.yaml:snakeyaml:jar:1.27:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-web:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-json:jar:2.4.5:compile\n" +
            "[INFO]    com.fasterxml.jackson.core:jackson-databind:jar:2.11.4:compile\n" +
            "[INFO]    com.fasterxml.jackson.core:jackson-annotations:jar:2.11.4:compile\n" +
            "[INFO]    com.fasterxml.jackson.core:jackson-core:jar:2.11.4:compile\n" +
            "[INFO]    com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.11.4:compile\n" +
            "[INFO]    com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.11.4:compile\n" +
            "[INFO]    com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.11.4:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-tomcat:jar:2.4.5:compile\n" +
            "[INFO]    org.apache.tomcat.embed:tomcat-embed-core:jar:9.0.45:compile\n" +
            "[INFO]    org.glassfish:jakarta.el:jar:3.0.3:compile\n" +
            "[INFO]    org.apache.tomcat.embed:tomcat-embed-websocket:jar:9.0.45:compile\n" +
            "[INFO]    org.springframework:spring-web:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-beans:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-webmvc:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-aop:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-expression:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-actuator:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-actuator-autoconfigure:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-actuator:jar:2.4.5:compile\n" +
            "[INFO]    io.micrometer:micrometer-core:jar:1.6.6:compile\n" +
            "[INFO]    org.hdrhistogram:HdrHistogram:jar:2.1.12:compile\n" +
            "[INFO]    org.latencyutils:LatencyUtils:jar:2.0.3:runtime\n" +
            "[INFO]    io.micrometer:micrometer-registry-prometheus:jar:1.6.6:compile\n" +
            "[INFO]    io.prometheus:simpleclient_common:jar:0.9.0:compile\n" +
            "[INFO]    io.prometheus:simpleclient:jar:0.9.0:compile\n" +
            "[INFO]    org.projectlombok:lombok:jar:1.18.20:compile\n" +
            "[INFO]    org.springframework.boot:spring-boot-starter-jdbc:jar:2.4.5:compile\n" +
            "[INFO]    org.springframework:spring-jdbc:jar:5.3.6:compile\n" +
            "[INFO]    org.springframework:spring-tx:jar:5.3.6:compile\n" +
            "[INFO]    com.alibaba:druid-spring-boot-starter:jar:1.2.6:compile\n" +
            "[INFO]    com.alibaba:druid:jar:1.2.6:compile\n" +
            "[INFO]    org.slf4j:slf4j-api:jar:1.7.30:compile\n" +
            "[INFO]    com.google.guava:guava:jar:30.1.1-jre:compile\n" +
            "[INFO]    com.google.guava:failureaccess:jar:1.0.1:compile\n" +
            "[INFO]    com.google.guava:listenablefuture:jar:9999.0-empty-to-avoid-conflict-with-guava:compile\n" +
            "[INFO]    com.google.code.findbugs:jsr305:jar:3.0.2:compile\n" +
            "[INFO]    org.checkerframework:checker-qual:jar:3.8.0:compile\n" +
            "[INFO]    com.google.errorprone:error_prone_annotations:jar:2.5.1:compile\n" +
            "[INFO]    com.google.j2objc:j2objc-annotations:jar:1.3:compile\n" +
            "[INFO]    junit:junit:jar:4.11:test\n" +
            "[INFO]    org.hamcrest:hamcrest-core:jar:2.2:test\n" +
            "[INFO]    org.hamcrest:hamcrest:jar:2.2:test";
    @Test
    public void mvnDependencyListReader() throws Exception {

        // 所有的依赖信息
        List<String> mvnDependencyList = Lists.newArrayList(mvnDependencyListStr.split("\n"));

        String springBootStarterDependency = "";

        for (String d : mvnDependencyList) {
            DependencyCopyer.copy(d);
            //
            if(d.contains("org.springframework.boot:spring-boot-starter:")){
                springBootStarterDependency = d;
            }
        }

        if(!springBootStarterDependency.isEmpty()){
            String version = springBootStarterDependency.substring(10).split(":")[3];
            String d = "[INFO]    org.springframework.boot:spring-boot-starter-parent:pom:"+version+":compile";
            DependencyCopyer.copy(d);
        }
    }

    public static void copy(String d) throws Exception {

        // 组织获取 三要素
        String[] strs = d.substring(10).split(":");

        String groupId = strs[0];
        String artifactId = strs[1];
        String version = strs[3];

        List<String> everyUnitPathList = Lists.newLinkedList();
        everyUnitPathList.addAll(Arrays.asList(groupId.split("\\.")));
        everyUnitPathList.add(artifactId);
        everyUnitPathList.add(version);
        String[] paths = everyUnitPathList.toArray(new String[0]);

        // 准备复制文件
        Path sourcePath = Paths.get(sourcePathStr, paths);
        Path targetPath = Paths.get(targetPathStr, paths);

        boolean isMk = targetPath.toFile().mkdirs();
        if (isMk) {
            for (File f : Objects.requireNonNull(sourcePath.toFile().listFiles())) {
                Files.copy(Paths.get(f.getPath()), Paths.get(targetPath.toString(), f.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        System.out.println(targetPath);
    }
}
