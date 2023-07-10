package com.nanshan.springbootnginxreverseproxy.utils;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
public class EncUtil_Test extends BaseTest {


    @Test
    @DisplayName("[Test_001] 測試 encUtil 字串壓碼")
    @Disabled
    void test_001() {
        // 測試工具方法
        String key1 = EncUtil.compactKey("業務部", "美國紐約");
        System.out.println("Generated Key1: " + key1);

        String key2 = EncUtil.compactKey("業務部", "美國紐約");
        System.out.println("Generated Key1: " + key2);
        Assertions.assertEquals(key1, key2, "字串壓碼值不同!");
    }

}
