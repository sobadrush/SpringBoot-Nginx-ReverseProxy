package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @author RogerLo
 * @date 2023/8/05
 */
public class FakeDataService_Test extends BaseTest {

    @Autowired
    @Qualifier("my-executor")
    private Executor myExecutor;
    
    @Autowired
    private FakeDataService fakeDataService;

    @Test
    @DisplayName("[Test_001] 測試 @Async (標註 @Async 的方法，必須回傳 CompletableFuture 才會生效！)")
    @Disabled
    void test_001() throws ExecutionException, InterruptedException {
        CompletableFuture<DeptVO> future1 = fakeDataService.getFakeDeptFuture001(); // wait 1s
        CompletableFuture<DeptVO> future2 = fakeDataService.getFakeDeptFuture002(); // wait 5s
        CompletableFuture.allOf(future1, future2).join();
        System.out.println(MessageFormat.format("deptVO_1: {0} , deptVO_2: {1}", future1.get(), future2.get()));
    }

    @Test
    @DisplayName("[Test_002] 測試 supplyAsync (若搭配 @Async 會失效！) ")
    @Disabled
    void test_002() throws ExecutionException, InterruptedException {
        CompletableFuture<DeptVO> future1 = CompletableFuture.supplyAsync(() -> fakeDataService.getFakeDept001(), myExecutor); // wait 1s
        CompletableFuture<DeptVO> future2 = CompletableFuture.supplyAsync(() -> fakeDataService.getFakeDept002(), myExecutor); // wait 5s
        CompletableFuture.allOf(future1, future2).join();
        DeptVO deptVO1 = future1.get();
        DeptVO deptVO2 = future2.get();
        System.out.println(MessageFormat.format("deptVO_1: {0} , deptVO_2: {1}", deptVO1, deptVO2));
    }

}
