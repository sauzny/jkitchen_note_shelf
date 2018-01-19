package com.sauzny.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Lianjia {

    public static void onePage(String url) throws IOException{
        
        Document document = Jsoup.connect(url).get();
        
        Elements elements = document.getElementById("house-lst").getElementsByTag("li");
        
        for(int i=0;i<elements.size();i++){
            String href = elements.get(i).getElementsByClass("pic-panel").get(0).getElementsByTag("a").get(0).attr("href");
            
            Element element = elements.get(i).getElementsByClass("info-panel").get(0);
            
            Element element1 = element.getElementsByClass("col-1").get(0);
            Element element2 = element.getElementsByClass("col-2").get(0);
            Element element3 = element.getElementsByClass("col-3").get(0);
            
            System.out.println(element1.text().replaceAll("  ", "\t")+"\t"+element2.text().replaceAll("人 看过此房", "")+"\t"+element3.text().replaceAll(" 更新", "").replaceAll("元/月 ", "元/月 \t")+"\t"+href);
        }
    }
    
    public static void yibindao() throws IOException{
        
        String baseurl = "https://tj.lianjia.com/ditiezufang/li1213444528222088s1213444558105971";
        
        for(int i=1; i<7; i++){
            String url = baseurl;
            if(i > 1){
                url = baseurl+"/pg"+i;
            }
            Lianjia.onePage(url);
        }
        
    }
    
    @Test
    public void foo01() throws IOException{
        Lianjia.yibindao();
    }
}
