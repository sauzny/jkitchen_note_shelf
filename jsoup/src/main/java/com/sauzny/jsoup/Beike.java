package com.sauzny.jsoup;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/***************************************************************************
 *
 * ███████╗ █████╗ ██╗   ██╗███████╗███╗   ██╗██╗   ██╗
 * ██╔════╝██╔══██╗██║   ██║╚══███╔╝████╗  ██║╚██╗ ██╔╝
 * ███████╗███████║██║   ██║  ███╔╝ ██╔██╗ ██║ ╚████╔╝ 
 * ╚════██║██╔══██║██║   ██║ ███╔╝  ██║╚██╗██║  ╚██╔╝  
 * ███████║██║  ██║╚██████╔╝███████╗██║ ╚████║   ██║   
 * ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
 *
 * @时间: 2018/11/21 - 14:04
 *
 * @描述: TODO
 *
 ***************************************************************************/
public class Beike {

    public static List<String> getList(String url) throws IOException {

        List<String> result = Lists.newArrayList();

        Document document = Jsoup.connect(url).get();

        Elements lis = document.getElementsByClass("sellListContent").first().getElementsByTag("li");

        lis.forEach(li -> {
            Element div_info = li.getElementsByClass("info").first();
            String title = div_info.getElementsByClass("title").first().getElementsByTag("a").first().text();
            String houseInfo = div_info.getElementsByClass("address").first().getElementsByClass("houseInfo").first().text();
            String positionInfo = div_info.getElementsByClass("flood").first().getElementsByClass("positionInfo").first().text();
            String followInfo = div_info.getElementsByClass("followInfo").first().text();
            String totalPrice = div_info.getElementsByClass("priceInfo").first().getElementsByClass("totalPrice").first().getElementsByTag("span").first().text();
            String unitPrice = div_info.getElementsByClass("priceInfo").first().getElementsByClass("unitPrice").first().getElementsByTag("span").first().text();

            String info = Joiner.on("\t").join(title, houseInfo, positionInfo, followInfo, totalPrice, unitPrice);

            result.add(info);
        });

        return result;
    }

    public static void main(String[] args) {

        var map = Maps.newHashMap();
        map.put("兴", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E5%85%B4%E5%9B%AD/");
        map.put("盛", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E7%9B%9B%E5%9B%AD/");
        map.put("祥", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E7%A5%A5%E5%9B%AD/");

        map.forEach((k, v) -> {
            try {
                List<String> result = Beike.getList(v.toString());
                result.forEach(System.out::println);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
