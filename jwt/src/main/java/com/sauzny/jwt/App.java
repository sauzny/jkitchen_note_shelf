package com.sauzny.jwt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main(String[] args) throws SignatureGenerationException, IllegalArgumentException, UnsupportedEncodingException {
        
        // json字符串
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        // json字符串
        String payloadJson = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true}";
        
        String header = Base64.encodeBase64URLSafeString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = Base64.encodeBase64URLSafeString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String content = String.format("%s.%s", header, payload);

        byte[] signatureBytes = Algorithm.HMAC256("secret").sign(content.getBytes(StandardCharsets.UTF_8));
        // HMAC计算返回原始二进制数据后进行Base64编码
        String signature = Base64.encodeBase64URLSafeString((signatureBytes));
        
    }
}
