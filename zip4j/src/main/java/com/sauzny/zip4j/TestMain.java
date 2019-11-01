package com.sauzny.zip4j;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class TestMain {

    /**
     * 使用给定密码压缩指定文件或文件夹到指定位置.
     * 
     * @param src
     *            要压缩的文件或文件夹路径 
     * @param dest
     *            压缩文件存放路径  /mnt/a.zip
     * @param passwd
     *            压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String zip(String src, String dest, String passwd) {
        File srcFile = new File(src);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
        if (!StringUtils.isEmpty(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(dest);
            zipFile.addFile(srcFile, parameters);
            return dest;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static void main(String[] args) {
        TestMain.zip("D:/d.xls", "D:/1.zip", "123");
    }

}
