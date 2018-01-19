package com.sauzny.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Hdw {

    public static String getThunderIndesc(String url) throws IOException{
        Document document = Jsoup.connect(url).get();
        
        String post_content = document.getElementById("post_content").text()
                .replaceAll(" ", "")
                .replaceAll(" ", "")
                .replaceAll("　", "")
                .replaceAll("：", "");
        
        String douban = " 豆瓣评分：暂无 ";
        String imdb = " IMDb评分：暂无 ";
        
        if(post_content.contains("豆瓣评分")){
            //System.out.println(post_content.split("豆瓣评分")[1].substring(0,6));
            douban = " 豆瓣评分： " + post_content.split("豆瓣评分")[1].substring(0,3);
        }
        
        if(post_content.contains("IMDb评分")){
            //System.out.println(post_content.split("IMDb评分")[1].substring(0,6));
            imdb = " IMDb评分： " + post_content.split("IMDb评分")[1].substring(0,3);
        }
        
        Element element = document.getElementsByClass("hacklogdownload_down_link").get(0);
        return "http://www.hdw.la" + element.getElementsByTag("a").attr("href") + "\t" + douban + "\t" + imdb;
    }
    
    public static void onePage(String url) {
        
        try{
            Document document = Jsoup.connect(url).get();

            Element post_container = document.getElementById("post_container");
            
            Elements a = post_container.getElementsByTag("a");
            
            for(Element element : a){
                if(element.attr("class").equals("zoom")){
                    
                    String msg = element.attr("title"); 
                    
                    System.out.printf("%-60s\t%-9s\n",msg, getThunderIndesc(element.attr("href")));
                    
                }
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void foo01(){ 
        
        for(int i=0;i<5; i++){
            
            String url = "http://www.hdw.la";
            
            if(i > 0){
                url += "/page/"+(i+1);
            }
            onePage(url);
        }
    }
}
