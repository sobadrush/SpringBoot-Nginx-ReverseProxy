package com.nanshan.springbootnginxreverseproxy;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author RogerLo
 * @date 2023/6/12
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BaseTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("... Before All ...");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println(String.format(" =================== [%s] START ===================", testInfo.getTestMethod().get().getName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println(String.format(" =================== [%s] END ===================", testInfo.getTestMethod().get().getName()));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("... After All ...");
    }

}
