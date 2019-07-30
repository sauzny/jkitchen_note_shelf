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
            String jump = li.getElementsByTag("a").first().attr("href");
            String title = div_info.getElementsByClass("title").first().getElementsByTag("a").first().text();
            String houseInfo = div_info.getElementsByClass("address").first().getElementsByClass("houseInfo").first().text();
            String positionInfo = div_info.getElementsByClass("flood").first().getElementsByClass("positionInfo").first().text();
            String followInfo = div_info.getElementsByClass("followInfo").first().text();
            String totalPrice = div_info.getElementsByClass("priceInfo").first().getElementsByClass("totalPrice").first().getElementsByTag("span").first().text();
            String unitPrice = div_info.getElementsByClass("priceInfo").first().getElementsByClass("unitPrice").first().getElementsByTag("span").first().text();

            String[] houseInfos = houseInfo.split("\\|");
            String houseInfo1 = houseInfos[0].trim();
            String houseInfo3 = houseInfos[2].trim();
            String houseInfo4 = houseInfos[3].trim();
            String houseInfo5 = houseInfos[4].trim();

            String positionInfo1 = positionInfo.substring(0,1);
            String positionInfo2 = positionInfo.replaceAll("楼层\\(共", "").replaceAll("层\\)板楼 - 宜兴埠", "").substring(1,3);

            String[] followInfos = followInfo.split("\\/");
            String followInfo1 = followInfos[0].trim().replaceAll("人关注","");
            String followInfo2 = followInfos[1].trim().replaceAll("次带看","").replaceAll("共","");
            String followInfo3 = followInfos[2].trim().replaceAll("以前发布","");

            String info = Joiner.on("\t").join(
                    title,
                    houseInfo1,
                    houseInfo3,
                    houseInfo4,
                    houseInfo5,
                    positionInfo1,
                    positionInfo2,
                    followInfo1,
                    followInfo2,
                    followInfo3,
                    totalPrice,
                    unitPrice.substring(2,7),
                    jump);

            result.add(info);
        });

        return result;
    }

    public static void main(String[] args) {

        var map = Maps.newHashMap();
        map.put("兴", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E5%85%B4%E5%9B%AD/");
        map.put("盛", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E7%9B%9B%E5%9B%AD/");
        map.put("祥", "https://tj.ke.com/ershoufang/co41l2rs%E6%B7%AE%E7%A5%A5%E5%9B%AD/");

        String title = Joiner.on("\t").join(
                "标题",
                "小区",
                "面积",
                "朝向",
                "装修",
                "所在楼层",
                "总楼层",
                "关注",
                "带看",
                "发布",
                "总价",
                "单价",
                "详情");
        System.out.println(title);
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
