package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
public class DeptService_CacheTest extends BaseTest {

    @Autowired
    private DeptService deptService;

    private void queryDeptInSeconds(int secs, long deptId) throws InterruptedException {
        for (int i = 1; i <= secs; i++) {
            DeptVO deptByDeptID = deptService.getDeptById(deptId);
            System.out.println("deptByDeptID = " + deptByDeptID);
            Thread.sleep(1000);
        }
    }

    @Test
    @DisplayName("[Test_001] 測試 @Cacheable：連續查詢，使用 Cache") // 因為有Cache設置，每3秒才會真正進DB訪問一次
    @Disabled
    void test_001() throws InterruptedException {
        // 連續查詢15秒
        this.queryDeptInSeconds(15, 2L);
    }

    @Test
    @DisplayName("[Test_002] 測試 @CachePut：更新緩存(不會影響到方法的運行)")
    @Disabled
    void test_002() throws InterruptedException {
        // 執行 update 前，先連續查詢3秒
        this.queryDeptInSeconds(3, 2L);
        // 執行更新
        String deptLocNew = "萬華區";
        DeptVO result = deptService.updateDept(
        DeptVO.builder().deptNo(2L).deptLoc(deptLocNew).build());
        System.out.println("result = " + result);
        // 連續查詢15秒
        this.queryDeptInSeconds(15, 2L);
    }

    @Test
    @DisplayName("[Test_003] 測試 @CacheEvict：觸發緩存清除")
    @Disabled
    void test_003() throws InterruptedException {
        DeptVO dept1001 = deptService.addDept(DeptVO.builder().deptNo(1001L)
                .deptName("教育部").deptLoc("博愛區").build());
        System.out.println("dept_1001 = " + dept1001);

        // 執行 update 前，先連續查詢5秒
        this.queryDeptInSeconds(5, 1001L);
        // 執行刪除
        int result = deptService.deleteDept(1001L);
        System.out.println("result = " + result);
        // 連續查詢15秒
        this.queryDeptInSeconds(15, 1001L);
    }

    @Test
    @DisplayName("[Test_004] 測試 @Caching：同時進行 @Cacheable 及 @CachePut")
    @Disabled
    void test_004() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            var result = deptService.getDeptByDeptNameAndLoc(
            DeptVO.builder().deptName("業務部").deptLoc("美國紐約").build());
            System.out.println("result = " + result);
            Thread.sleep(1000);
        }
    }

}
