package com.sauzny.guava.strings;

import org.junit.Test;

import com.google.common.base.CharMatcher;

/**
 * *************************************************************************
 * @文件名称: GuavaCharMatcher.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.strings 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  字符匹配器
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月1日 - 下午3:44:56 
 *	
 **************************************************************************
 */
public class GuavaCharMatcher {

    
    @Test
    public void foo1(){
        
        String string = " \u0013 FirstName LastName +1 123       456       789 !@#$%^&*()_+|}{:\"?>< ";
        
        String noControl = CharMatcher.javaIsoControl().removeFrom(string); //移除control字符
        String theDigits = CharMatcher.digit().retainFrom(string); //只保留数字字符
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' ');//去除两端的空格，并把中间的连续空格替换成单个空格
        String noDigits = CharMatcher.javaDigit().replaceFrom(string, "*"); //用*号替换所有数字
        String lowerAndDigit = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(string);// 只保留数字和小写字母
        
        
        System.out.println("noControl：" + noControl);
        System.out.println("theDigits：" + theDigits);
        System.out.println("spaced：" + spaced);
        System.out.println("noDigits：" + noDigits);
        System.out.println("lowerAndDigit：" + lowerAndDigit);
        

        System.out.println("-------------------------------------------------------");
        
        System.out.println("any：" + CharMatcher.any().matchesAllOf("anything"));
        System.out.println("none：" + CharMatcher.none().matchesAllOf("anything"));
        System.out.println("whitespace：" + CharMatcher.whitespace().matchesAllOf("  "));
        System.out.println("breakingWhitespace：" + CharMatcher.breakingWhitespace().matchesAllOf("\r\n"));
        System.out.println("invisible：" + CharMatcher.invisible().matchesAllOf("\t"));
        System.out.println("digit：" + CharMatcher.digit().matchesAllOf("1346948651"));
        System.out.println("javaLetter：" + CharMatcher.javaLetter().matchesAllOf("gfnfgnFBSBSD"));
        System.out.println("javaDigit：" + CharMatcher.javaDigit().matchesAllOf("5354351"));
        System.out.println("javaLetterOrDigit：" + CharMatcher.javaLetterOrDigit().matchesAllOf("216541615anything"));
        System.out.println("javaIsoControl：" + CharMatcher.javaIsoControl().matchesAllOf("\u0013"));
        System.out.println("javaLowerCase：" + CharMatcher.javaLowerCase().matchesAllOf("vsvsdvds"));
        System.out.println("javaUpperCase：" + CharMatcher.javaUpperCase().matchesAllOf("SVSDVSDFVFCS"));
        System.out.println("ascii：" + CharMatcher.ascii().matchesAllOf("5645615"));
        System.out.println("singleWidth：" + CharMatcher.singleWidth().matchesAllOf("pi pei dan kuan zi fu,zhong wen shi shuang kuan"));
        

        System.out.println("-------------------------------------------------------");
        
        /*
        CharMatcher is(char match): 返回匹配指定字符的Matcher
        CharMatcher isNot(char match): 返回不匹配指定字符的Matcher
        CharMatcher anyOf(CharSequence sequence): 返回匹配sequence中任意字符的Matcher
        CharMatcher noneOf(CharSequence sequence): 返回不匹配sequence中任何一个字符的Matcher
        CharMatcher inRange(char startInclusive, char endIncludesive): 返回匹配范围内任意字符的Matcher
        CharMatcher forPredicate(Predicate<? super Charater> predicate): 返回使用predicate的apply()判断匹配的Matcher
        CharMatcher negate(): 返回以当前Matcher判断规则相反的Matcher
        CharMatcher and(CharMatcher other): 返回与other匹配条件组合做与来判断的Matcher
        CharMatcher or(CharMatcher other): 返回与other匹配条件组合做或来判断的Matcher
        
        boolean matchesAnyOf(CharSequence sequence): 只要sequence中有任意字符能匹配Matcher,返回true
        boolean matchesAllOf(CharSequence sequence): sequence中所有字符都能匹配Matcher,返回true
        boolean matchesNoneOf(CharSequence sequence): sequence中所有字符都不能匹配Matcher,返回true
        
        int indexIn(CharSequence sequence): 返回sequence中匹配到的第一个字符的坐标
        int indexIn(CharSequence sequence, int start): 返回从start开始,在sequence中匹配到的第一个字符的坐标
        int lastIndexIn(CharSequence sequence): 返回sequence中最后一次匹配到的字符的坐标
        int countIn(CharSequence sequence): 返回sequence中匹配到的字符计数
        
        String removeFrom(CharSequence sequence): 删除sequence中匹配到到的字符并返回
        String retainFrom(CharSequence sequence): 保留sequence中匹配到的字符并返回
        String replaceFrom(CharSequence sequence, char replacement): 替换sequence中匹配到的字符并返回
        String trimFrom(CharSequence sequence): 删除首尾匹配到的字符并返回
        String trimLeadingFrom(CharSequence sequence): 删除首部匹配到的字符
        String trimTrailingFrom(CharSequence sequence): 删除尾部匹配到的字符
        String collapseFrom(CharSequence sequence, char replacement): 将匹配到的组(连续匹配的字符)替换成replacement 
        String trimAndCollapseFrom(CharSequence sequence, char replacement): 先trim在replace
        */
        
    }
}
