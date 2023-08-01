package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
public class DeptService_Test extends BaseTest {

    @Autowired
    private DeptService deptService;

    @Test
    @DisplayName("[Test_001] 測試使用 Example 查詢，By Id")
    @Disabled
    void test_001() throws InterruptedException {
        for (int i = 0; i < 60; i++) {
            DeptVO deptByDeptID = deptService.getDeptById(2L);
            System.out.println("deptByDeptID = " + deptByDeptID);
            Thread.sleep(1000);
        }
    }

    @Test
    @DisplayName("[Test_002] 測試使用 Example 查詢，By deptName 關鍵字")
    @Disabled
    void test_002() {
        List<DeptVO> deptList = deptService.getDeptByDeptName("部");
        System.out.println("deptList = " + deptList);
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRED)
    @Commit
    // @Rollback
    @DisplayName("[Test_003] 測試新增 dept")
    @Disabled
    void test_003() {
        DeptVO result = deptService.addDept(DeptVO.builder().deptNo(1001L).deptName("投資部").deptLoc("信義莊敬").build());
        System.out.println("result = " + result);
        Assertions.assertEquals(1001, result.getDeptNo(), "新增失敗！");
    }

    @Test
    @DisplayName("[Test_004] 測試更新 dept")
    @Transactional(Transactional.TxType.SUPPORTS)
    @Commit
    // @Rollback
    @Disabled
    void test_004() {
        // 先新增一筆 1001 的部門
        deptService.addDept(DeptVO.builder().deptNo(1001L).deptName("投資部").deptLoc("信義莊敬").build());
        // ---
        String deptLocNew = "信義區";
        DeptVO result = deptService.updateDept(
        DeptVO.builder().deptNo(1001L).deptLoc(deptLocNew).build());
        System.out.println("result = " + result);
        Assertions.assertEquals(deptLocNew, result.getDeptLoc(), "更新失敗！");
    }

}
