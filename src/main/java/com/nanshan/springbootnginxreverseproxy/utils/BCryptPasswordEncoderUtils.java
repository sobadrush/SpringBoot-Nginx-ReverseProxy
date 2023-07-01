package com.nanshan.springbootnginxreverseproxy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author RogerLo
 * @date 2023/7/1
 *
 * 因為我們開啟了加密，所以數據庫中只存儲了密文
 * 使用下面的代碼，計算出密文，然後將數據庫中密碼改為密文
 * 否則會發生錯誤： org.springframework.security.authentication.BadCredentialsException: Bad credentials
 *
 * ref. https://blog.csdn.net/weixin_43649997/article/details/116140361
 */
public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "qweasd123";
        String pwd = encodePassword(password);
        System.out.println(pwd);
    }
}