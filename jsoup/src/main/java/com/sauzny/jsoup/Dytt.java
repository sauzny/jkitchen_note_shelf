package com.sauzny.jsoup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.common.collect.Lists;

public class Dytt {

    public static String getThunderIndesc(String url) throws IOException{
        
        String result = "";
        
        Document document = Jsoup.connect(url).get();
        
        Element element = document.getElementsByTag("td").get(0);
        result = element.text();
        
        for(String line : document.getElementById("Zoom").text().split("◎")){
            if(line.contains("评分")){
                result = result + " | " + line;
            }
        }
        
        return result;
    }
    
    @Test
    public void foo01() throws IOException, InterruptedException{
        String url = "http://www.dytt8.net";
        
        List<String> list = Lists.newArrayList();
        list.add(" 电影名字 | 下载地址 | 评分1 | 评分2 ");
        list.add(" -- | -- | -- | -- ");
        
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("inddline");
        for(Element element : elements){
            
            if(element.html().contains("最新电影下载")){
                Element element1 = element.getElementsByTag("a").get(1);
                String href = element1.attr("href");
                String text = element1.text();
                Thread.sleep(1000L);
                list.add(text + " | " + getThunderIndesc(url+href));
            }
            
        }

        
        String userDir = System.getProperty("user.dir");
        Files.write(Paths.get(userDir+"/dytt.md"), list, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }
}
