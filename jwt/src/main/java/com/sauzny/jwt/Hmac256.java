package com.sauzny.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Hmac256 {

    /*
     * 
                .setId(id)                                      // JWT_ID
                .setAudience("")                                // 接受者
                .setClaims(null)                                // 自定义属性
                .setSubject("")                                 // 主题
                .setIssuer("")                                  // 签发者
                .setIssuedAt(new Date())                        // 签发时间
                .setNotBefore(new Date())                       // 失效时间
                .setExpiration(long)                                // 过期时间
                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
     */
    
    public static String create(){
        
        String token = "";
        
        // head
        Map<String, Object> headerClaims = new HashMap<String, Object>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String,Object> payloadClaims = new HashMap<String,Object>();
        payloadClaims.put("sub", "1234567890");
        payloadClaims.put("name", "John Doe");
        payloadClaims.put("admin","true");
        
        try {
            token = JWT.create()
                .withHeader(headerClaims)
                .withClaim("sub", "1234567890")
                .withClaim("name", "John Doe")
                .withClaim("admin",true)
                .sign(Algorithm.HMAC256("secret"));
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }
    
    public static DecodedJWT verify(String token){
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return jwt;
    }
    
    
    public static void main(String[] args) {
        String token = Hmac256.create();
        System.out.println(token);
        
        DecodedJWT jwt = Hmac256.verify(token);
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getPayload());
        System.out.println(jwt.getSignature());
        
        System.out.println(jwt.getClaim("sub").asString());
    }
}
