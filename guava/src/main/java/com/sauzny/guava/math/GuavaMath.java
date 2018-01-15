package com.sauzny.guava.math;

import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;

/**
 * *************************************************************************
 * @文件名称: GuavaMath.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.math 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  数学
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月2日 - 上午10:00:45 
 *	
 **************************************************************************
 */
public class GuavaMath {

    // 溢出检测
    @Test
    public void foo01(){
        
        System.out.println("溢出检测");
        
        try {
            IntMath.checkedAdd(Integer.MAX_VALUE,Integer.MAX_VALUE);
        } catch (ArithmeticException e) {
            System.out.println("有溢出检查的运算    throws ArithmeticException");
        }
        
        IntMath.checkedSubtract(4,5);
        IntMath.checkedMultiply(4,5);
        IntMath.checkedPow(4,5);
        
        LongMath.checkedAdd(4,5);
        LongMath.checkedSubtract(4,5);
        LongMath.checkedMultiply(4,5);
        LongMath.checkedPow(4,5);
        
        System.out.println("-------------------------------------------");
        
    }
    
    // 实数运算，运算后取整
    @Test
    public void foo02(){
        
        System.out.println("实数运算，运算后取整");
        
        /*
                            一个 java.math.RoundingMode 枚举值作为舍入的模式：
            
            DOWN：向零方向舍入（去尾法）
            UP：远离零方向舍入
            FLOOR：向负无限大方向舍入
            CEILING：向正无限大方向舍入
            UNNECESSARY：不需要舍入，如果用此模式进行舍入，应直接抛出 ArithmeticException
            HALF_UP：向最近的整数舍入，其中 x.5 远离零方向舍入
            HALF_DOWN：向最近的整数舍入，其中 x.5 向零方向舍入
            HALF_EVEN：向最近的整数舍入，其中 x.5 向相邻的偶数舍入
         */
        
        BigInteger b1 = BigInteger.TEN;
        BigInteger b2 = BigInteger.valueOf(874);
        
        //除法  
        int chu1 = IntMath.divide(88, 78, RoundingMode.DOWN);
        long chu2 = LongMath.divide(57, 15, RoundingMode.UP);
        BigInteger chu3 = BigIntegerMath.divide(b1, b2, RoundingMode.FLOOR);
        
        System.out.println("除法，取整：" + chu1 + " | " + chu2 + " | " + chu3);
        
        //2为底的对数  
        int twodi1 = IntMath.log2(357, RoundingMode.CEILING);
        int twodi2 = LongMath.log2(6549, RoundingMode.HALF_UP);
        int twodi3 = BigIntegerMath.log2(b2, RoundingMode.HALF_UP);
        
        System.out.println("2为底的对数，取整：" + twodi1 + " | " + twodi2 + " | " + twodi3);
        
        //10为底的对数 
        int tendi1 = IntMath.log10(654, RoundingMode.HALF_DOWN);
        int tendi2 = LongMath.log10(385415, RoundingMode.HALF_EVEN);
        int tendi3 = BigIntegerMath.log10(b2, RoundingMode.DOWN);
        
        System.out.println("10为底的对数 法，取整：" + tendi1 + " | " + tendi2 + " | " + tendi3);
        
        //平方根 
        int gen1 = IntMath.sqrt(587, RoundingMode.UP);
        long gen2 = LongMath.sqrt(534863465, RoundingMode.FLOOR);
        BigInteger gen3 = BigIntegerMath.sqrt(b2, RoundingMode.CEILING);
        
        System.out.println("平方根，取整：" + gen1 + " | " + gen2 + " | " + gen3);
        
        System.out.println("-------------------------------------------");
    }
    
    //  附加功能
    @Test
    public void foo03(){
        
        System.out.println("附加功能");

        BigInteger b1 = BigInteger.TEN;
        BigInteger b2 = BigInteger.valueOf(874);
        
        //最大公约数  
        int gcd1 = IntMath.gcd(6432, 3618);
        long gcd2 = LongMath.gcd(381518198, 84313128);
        BigInteger gcd3 = b1.gcd(b2);
        
        System.out.println("最大公约数：" + gcd1 + " | " + gcd2 + " | " + gcd3);
        
        //取模  
        int mod1 = IntMath.mod(846516, 38746518);
        long mod2 = LongMath.mod(365498416, 36878946541898L);
        BigInteger mod3 = b1.mod(b2);
        
        System.out.println("取模：" + mod1 + " | " + mod2 + " | " + mod3);
        
        //取幂  
        int pow1 = IntMath.pow(1024, 2);
        long pow2 = LongMath.pow(2, 1024);
        BigInteger pow3 = b1.pow(2);
        
        System.out.println("取幂：" + pow1 + " | " + pow2 + " | " + pow3);
        
        //是否 2的幂 
        boolean isPowerOfTwo1 = IntMath.isPowerOfTwo(1024);
        boolean isPowerOfTwo2 = LongMath.isPowerOfTwo(65536);
        boolean isPowerOfTwo3 = BigIntegerMath.isPowerOfTwo(BigInteger.valueOf(1024));
        
        System.out.println("是否 2的幂 ：" + isPowerOfTwo1 + " | " + isPowerOfTwo2 + " | " + isPowerOfTwo3);
        
        //阶乘* 
        int factorial1 = IntMath.factorial(4);
        long factorial2 = LongMath.factorial(86);
        BigInteger factorial3 = BigIntegerMath.factorial(26);
        
        System.out.println("阶乘*：" + factorial1 + " | " + factorial2 + " | " + factorial3);
        
        //二项式系数*
        int binomial1 = IntMath.binomial(8, 2);
        long binomial2 = LongMath.binomial(18, 6);
        BigInteger binomial3 = BigIntegerMath.binomial(66, 16);
        
        System.out.println("二项式系数*：" + binomial1 + " | " + binomial2 + " | " + binomial3);
        
        System.out.println("-------------------------------------------");
        
    }
    
    // 浮点数运算
    @Test
    public void foo04(){
        
        System.out.println("浮点数运算");
        
        //判断该浮点数是不是一个整数
        boolean isMathematicalInteger = DoubleMath.isMathematicalInteger(52.56);
        
        //舍入为 int；对无限小数、溢出抛出异常
        int roundToInt = DoubleMath.roundToInt(3645.2, RoundingMode.HALF_DOWN);  
        
        //舍入为 long；对无限小数、溢出抛出异常
        long roundToLong = DoubleMath.roundToLong(984615.6, RoundingMode.HALF_UP);   
        
        //舍入为 BigInteger；对无限小数抛出异常
        BigInteger roundToBigInteger = DoubleMath.roundToBigInteger(68415.8, RoundingMode.HALF_DOWN); 
        
        //2 的浮点对数，并且舍入为 int，比 JDK 的 Math.log(double) 更快
        int log2 = DoubleMath.log2(651615.58, RoundingMode.HALF_UP);  
        
        System.out.println("浮点数运算：" + isMathematicalInteger + " | " + roundToInt + " | " + roundToLong + " | " + roundToBigInteger + " | " + log2);

        
        System.out.println("-------------------------------------------");
    }
}
