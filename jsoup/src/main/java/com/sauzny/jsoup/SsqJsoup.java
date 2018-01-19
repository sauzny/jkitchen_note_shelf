package com.sauzny.jsoup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SsqJsoup {
    
    /**
     * @描述: 获取当前期号的数据
     * @param targetQihao
     * @return
     * @throws IOException 
     * @返回 List<String>
     * @创建人  ljx 创建时间 2018年1月11日 上午11:08:26
     */
    public static List<String> ssqData(int targetQihao) throws IOException{
        
        String url = "http://zst.aicai.com/ssq/betOrder/";
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("startIssue",String.valueOf(targetQihao));
        map.put("endIssue",String.valueOf(targetQihao));
        map.put("sIssue","");
        map.put("eIssue","");
        map.put("maxsize","30");
        map.put("sortTag","up");
        map.put("openDate","");
        map.put("currentPage","1");
        map.put("pageSize","30");
        map.put("gameIndex","101");
        map.put("pageInfo","ballOrder");
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {     
               //添加参数
                con.data(entry.getKey(), entry.getValue());
               } 
        }
        //插入cookie（头文件形式）
        //con.header("Cookie", cookie);
        Document document = con.post(); 
        
        //Document document = Jsoup.connect(url).post();
        
        //System.out.println(document.html());
        
        Elements trs = document.getElementsByAttributeValue("onmouseover", "this.style.background='#fff7d8'");
        
        Elements tds = trs.get(0).getElementsByTag("td");
        
        Elements blueEs = trs.get(0).getElementsByClass("blueColor");
        
        // 结果
        List<String> list = Lists.newArrayList();
        
        for(int i=0;i<8;i++){
            list.add(tds.get(i).text());
        }
        list.add(blueEs.get(0).text());
        
        //System.out.println(list);
        
        return list;
    }
    
    public static Document ssqHtml(Path dataPath) throws IOException{
        
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        
        StringBuilder sb = new StringBuilder();
        
        for(String line : lines){
            sb.append(line);
        }
        
        return Jsoup.parse(sb.toString());
    }
    
    @Test
    public void foo01() throws Exception{
        ssqData(2017111);
    }
}
