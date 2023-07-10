package com.nanshan.springbootnginxreverseproxy.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author RogerLo
 * @date 2023/7/10
 *
 * 壓碼加密工具類別
 */
@Slf4j
public class EncUtil {

    /**
     * 字串壓碼方法(生成32位英數)
     */
    public static String compactKey(String... values) {
        log.info("...call compactKey...");
        try {
            // 使用 SHA-256 散列算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 將所有引數串接成一個字串
            StringBuilder sb = new StringBuilder();
            for (String value : values) {
                sb.append(value);
            }
            String input = sb.toString();

            // 將字串轉換為位元組陣列
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

            // 計算散列值
            byte[] hash = digest.digest(bytes);

            // 取前 32 位散列值
            byte[] truncatedHash = new byte[16];
            System.arraycopy(hash, 0, truncatedHash, 0, 16);

            // 將散列值轉換為十六進制字串
            StringBuilder hexString = new StringBuilder();
            for (byte b : truncatedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 返回固定長度的 key
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 處理 NoSuchAlgorithmException 錯誤
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
