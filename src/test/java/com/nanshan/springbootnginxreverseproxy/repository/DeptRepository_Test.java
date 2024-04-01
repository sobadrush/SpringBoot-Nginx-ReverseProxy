package com.nanshan.springbootnginxreverseproxy.repository;

import com.nanshan.springbootnginxreverseproxy.BaseTest;
import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import lombok.Getter;
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
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @DisplayName("[test_005] 測試傳遞 Enum")
    @ParameterizedTest
    @EnumSource(Direction.class)
    @Disabled
    void test_005(Direction dir) {
        System.out.println(dir.getEnglishName() + " - " + dir.getDesc());
    }

    @DisplayName("[test_006] 複雜的構造算法，可以簡單地指定一個特殊的輔助方法")
    @ParameterizedTest
    @MethodSource(value = { "directionFactory" })
    @Disabled
    void test_006(String argument) {
        System.out.println("argument = \n" + argument);
    }

    static Stream<String> directionFactory() {
        String allStr = Arrays.stream(Direction.values())
                .map(dir -> dir.getEnglishName() + "@" + dir.getDesc())
                .collect(Collectors.joining("\n---\n"));
        return Stream.of(allStr);
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

@Getter
enum Direction {
    EAST("EAST", "東"),
    WEST("WEST", "西"),
    SOUTH("SOUTH", "南"),
    NORTH("NORTH", "北")
    ;

    private String englishName;
    private String desc;

    Direction(String englishName, String desc) {
        this.englishName = englishName;
        this.desc = desc;
    }
}
