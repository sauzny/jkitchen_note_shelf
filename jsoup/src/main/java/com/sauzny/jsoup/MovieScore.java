package com.sauzny.jsoup;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class MovieScore {

    public static String douban(String name) throws IOException{
        
        String url = "https://movie.douban.com/subject_search?search_text=%E8%9C%98%E8%9B%9B%E4%BE%A0%EF%BC%9A%E8%8B%B1%E9%9B%84%E5%BD%92%E6%9D%A5&cat=1002";
        
        Document document = Jsoup.connect(url).get();

        System.out.println(document.html());
        
        return name;
    }
    
    @Test
    public void foo01() throws IOException{
        String name = "";
        System.out.println("douban : " + douban(name));
        System.out.println("douban : " + douban(name));
    }
}
