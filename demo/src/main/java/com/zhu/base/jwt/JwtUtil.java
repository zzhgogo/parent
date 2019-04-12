package com.zhu.base.jwt;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuhao
 * @title: JwtUtil
 * @projectName parent
 * @description: TODO
 * @date 2019/4/113:57 PM
 */
public class JwtUtil {

    private String jianshu = "mqtt:";

    /**
     * 由字符串生成加密key
     * @return
     */
    public SecretKey generalKey(){
        String stringKey = jianshu+Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     * @return
     */
    public static String generalSubject(){
        JSONObject jo = new JSONObject();
        jo.put("userId", "zhuhao");
        jo.put("mobile", "18244975178");
        return jo.toJSONString();
    }

    @Test
    public void t1() throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String key = "AJAHDAHSDASDBASDJASJD";
        JwtBuilder builder = Jwts.builder()
                .setId("123")
                .setIssuedAt(now)
                .setSubject("123")
                .signWith(signatureAlgorithm, key);
        String token = builder.compact();

        System.out.println(token);

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();

        System.out.println(claims);


    }

    @Test
    public void t2() throws Exception {
        Map<String, Object> stringObjectMap = new HashMap<>();
        String KEY = "AJAHDAHSDASDBASDJASJD";
        stringObjectMap.put("type", "1");
        String payload = "{\"user_id\":\"1341137\", \"expire_time\":\"2018-01-01 0:00:00\"}";
        String compactJws = Jwts.builder()
                .setHeader(stringObjectMap)
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();

        System.out.println("jwt key:" + KEY);
        System.out.println("jwt payload:" + payload);
        System.out.println("jwt encoded:" + compactJws);

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(compactJws);
        JwsHeader header = claimsJws.getHeader();
        Claims body = claimsJws.getBody();

        System.out.println("jwt header:" + header);
        System.out.println("jwt body:" + body);
        System.out.println("jwt body user-id:" + body.get("user_id", String.class));


    }


}
