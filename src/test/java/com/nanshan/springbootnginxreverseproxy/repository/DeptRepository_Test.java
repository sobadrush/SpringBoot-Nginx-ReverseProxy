package com.nanshan.springbootnginxreverseproxy.repository;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

public class DeptRepository_Test extends BaseTest {

    @Autowired
    private DeptRepository deptRepo;

    @Test
    @DisplayName("[test_001] 測試 findAll")
    @Disabled
    void test_001() {
        long actualCount = deptRepo.findAll().stream()
            .filter(Objects::nonNull) // 強制執行 peek
            .peek(x -> { // 自從Java 9以後，如果JDK編譯器能夠直接從串流（Stream）中計算計數（Java 9中的優化），它將不會遍歷整個串流，因此根本不需要執行peek()。(ref. https://mkyong.com/java8/java-8-stream-the-peek-is-not-working-with-count/)
                System.out.println("x = " + x);
            }).count();
        Assertions.assertEquals(actualCount, 4, "Dept 資料數量不符");
    }

    @DisplayName("[test_002] 測試 findByDeptName")
    @ParameterizedTest(name = "{index} → 參數1 =''{0}''")
    @ValueSource(strings = { "研發部", "業務部" })
    @Disabled
    void test_002(String param_deptName) {
        Optional<DeptVO> deptOpt = deptRepo.findByDeptName(param_deptName);
        deptOpt.ifPresent(deptVO -> {
            System.out.println("deptVO = " + deptVO);
        });
        Assertions.assertEquals(param_deptName, deptOpt.get().getDeptName(), "Dept 錯誤");
    }

    @DisplayName("[test_003] 測試複合條件查詢(使用 @AggregateWith)")
    @ParameterizedTest(name = "case: {index} → param1={0}, param2=\"{1}\", param3=\"{2}\"")
    @CsvSource({
        "2, 研發部, 臺灣新竹",
        "3, 業務部, 美國紐約"
    })
    @Disabled
    void test_003(@AggregateWith(DeptArgAggregator.class) DeptVO paramDept) {
        System.out.println("paramDept = " + paramDept);
        deptRepo.findByDeptNameAndDeptLoc(paramDept.getDeptName(), paramDept.getDeptLoc())
            .stream().forEach(System.err::println);
    }

    @DisplayName("[test_004] 測試複合條件查詢-讀取CSV")
    @ParameterizedTest(name = "case: {index} → param1={0}, param2=\"{1}\", param3=\"{2}\"")
    @CsvFileSource(resources = { "/Junit_Test_Data/dept_test.csv" }, numLinesToSkip = 1)
    @Disabled
    void test_004(@AggregateWith(DeptArgAggregator.class) DeptVO paramDept) {
        System.out.println("paramDept = " + paramDept);
        deptRepo.findByDeptNameAndDeptLoc(paramDept.getDeptName(), paramDept.getDeptLoc())
                .ifPresent(System.err::println);
    }

}

/**
 * Junit 參數化測試：參數轉換聚合器
 *
 * 說明：
 *  1. 將測試參數依順序封裝成 ArgumentsAccessor
 *  2. 依序用 getXXX(index) 可取得對應型別參數
 *
 */
class DeptArgAggregator implements ArgumentsAggregator {
    @Override
    public DeptVO aggregateArguments(ArgumentsAccessor argAccessor, ParameterContext paramCtx) throws ArgumentsAggregationException {
        System.out.println("argAccessor = " + argAccessor);
        return DeptVO.builder()
            .deptNo(argAccessor.getLong(0))
            .deptName(argAccessor.getString(1))
            .deptLoc(argAccessor.getString(2))
            .build();
    }
}
