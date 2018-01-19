package com.sauzny.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class Mvnrepository {

    public static String license(String groupId, String artifactId){
        String result = "";
        try {
            String url = "http://mvnrepository.com/artifact/"+groupId+"/"+artifactId;
            Document document = Jsoup.connect(url).get();
            result = document.getElementsByClass("lic").get(0).text();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    public static String lastVersion(String groupId, String artifactId){
        String result = "";
        try {
            String url = "http://mvnrepository.com/artifact/"+groupId+"/"+artifactId;
            Document document = Jsoup.connect(url).get();
            result = document.getElementsByClass("vbtn").get(0).text();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    @Test
    public void foo01(){
        String groupId = "org.projectlombok";
        String artifactId = "lombok";
        String license = Mvnrepository.license(groupId, artifactId);
        String lastVersion = Mvnrepository.lastVersion(groupId, artifactId);
        System.out.println(license);
        System.out.println(lastVersion);
    }
}
