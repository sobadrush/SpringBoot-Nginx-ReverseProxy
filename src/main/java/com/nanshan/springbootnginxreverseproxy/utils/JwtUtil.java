package com.nanshan.springbootnginxreverseproxy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;

/**
 * @author RogerLo
 * @date 2023/6/30
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt") // 自動將設定檔中 jwt 開頭的 key-value 注入宣告的屬性中(屬性名稱需完全相同)
public class JwtUtil {

    private String secretKey; // 密鑰
    private int lifeTime; // token 有效時間(second)

    /**
     * 將 userId 簽署到 token 裡面，以供之後 CRUD 之用
     */
    public String Sign(String userId) {
        Claims claims = Jwts.claims();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, lifeTime);
        claims.setExpiration(calendar.getTime()); // 有效時間
        claims.put("userId", userId);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 驗證 token
     */
    public String Verify(String token) {
        String userId;
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); // HMAC（Hash-based Message Authentication Code）是一種基於雜湊函數和密鑰的訊息認證碼演算法
            Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
            userId = claims.get("userId").toString();
        } catch (Exception e) { // 若業務中還需要根據是否過期進行後續邏輯，catch ExpiredJwtException
            userId = null;
        }
        return userId;
    }
}