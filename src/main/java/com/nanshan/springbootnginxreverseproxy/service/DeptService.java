package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import com.nanshan.springbootnginxreverseproxy.repository.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author RogerLo
 * @date 2023/7/10
 */
@Service
@Slf4j
@org.springframework.cache.annotation.Cacheable(cacheNames = "deptCached")
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;

    /**
     * 新增 dept
     */
    public DeptVO addDept(DeptVO deptVO) {
        return deptRepository.saveAndFlush(deptVO);
    }

    /**
     * 更新 dept
     */
    @CachePut(cacheNames = { "deptCached" }, key = "#result.deptNo")
    public DeptVO updateDept(DeptVO deptVO) {
        Optional<DeptVO> deptOpt = deptRepository.findById(deptVO.getDeptNo());
        if (deptOpt.isPresent()) {
            if (StringUtils.isNotBlank(deptVO.getDeptName())) {
                deptOpt.get().setDeptName(deptVO.getDeptName());
            }
            if (StringUtils.isNotBlank(deptVO.getDeptLoc())) {
                deptOpt.get().setDeptLoc(deptVO.getDeptLoc());
            }
        } else {
            throw new RuntimeException("Data not found.");
        }
        log.info(">>> 執行更新，deptId：{}", deptVO.getDeptNo());
        return deptRepository.saveAndFlush(deptOpt.get());
    }

    /**
     * 刪除 dept
     * <p>
     * beforeInvocation = true：
     * 方法調用之前執行緩存清除操作
     * 這意味著無論方法執行是否成功，都會先清除緩存中的數據，然後再執行方法
     */
    @CacheEvict(value = "deptCached", beforeInvocation = true)
    public int deleteDept(Long deptId) {
        try {
            deptRepository.deleteById(deptId);
            log.info(">>> 執行刪除，deptId：{}", deptId);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Example 查詢：By DeptId
     */
    @Cacheable(value = { "deptCached" }, key = "#deptId")
    public DeptVO getDeptById(long deptId) {
        log.info("... 呼叫 getDeptById ...");
        DeptVO deptExample = new DeptVO();
        deptExample.setDeptNo(deptId);
        Example<DeptVO> example = Example.of(deptExample);
        return deptRepository.findOne(example).orElse(null);
    }

    // Caching: 可同時對一個方法進行 cachable / cachePut 等多種操作
    @Caching(
            // cacheable = { @Cacheable(cacheNames = "deptCached", key = "#deptVO.deptNo") } // Fail! Null key returned for cache operation (因為參數沒有傳 deptNo)
            // cacheable = { @Cacheable(cacheNames = "deptCached", key = "#deptVO.deptName") } // OK! 因為參數有傳 deptName / deptLoc
            // cacheable = { @Cacheable(cacheNames = "deptCached", key = "#deptVO") } // OK!
            // cacheable = { @Cacheable(cacheNames = "deptCached", key = "'dept_'.concat(#deptVO.hashCode())") } // OK! 使用物件的 HashCode
            // cacheable = { @Cacheable(cacheNames = "deptCached", key = "T(java.util.Objects).hash(#deptVO.deptName, #deptVO.deptLoc)") } // OK!
            // cacheable = { @Cacheable(cacheNames = "deptCached", keyGenerator = "myKeyGenerator") } // OK! 使用自訂 keyGenerator
            cacheable = {
                    // OK! 使用參數壓碼，因為參數有傳 deptName / deptLoc
                    @Cacheable(cacheNames = "deptCached",
                            key = "T(com.nanshan.springbootnginxreverseproxy.utils.EncUtil).compactKey(#deptVO.deptName, #deptVO.deptLoc)", // 與參數列的 deptVO 參數對應
                            condition = "#deptVO.deptName.contains('部')" // 條件式緩存
                    )
            }
            ,
            put = {
                    @CachePut(cacheNames = "deptCached", key = "#result.deptNo"), // #result 表示函數完成後的返回結果(完成後有 deptNo)
                    @CachePut(cacheNames = "deptCached", key = "#result.deptName") // #result 表示函數完成後的返回結果
            }
    )
    public DeptVO getDeptByDeptNameAndLoc(DeptVO deptVO) {
        log.info("... 呼叫 getDeptByDeptNameAndLoc ...");
        DeptVO deptExample = new DeptVO();
        deptExample.setDeptName(deptVO.getDeptName());
        deptExample.setDeptLoc(deptVO.getDeptLoc());
        Example<DeptVO> example = Example.of(deptExample);
        return deptRepository.findOne(example).orElse(null);
    }

    /**
     * Example 查詢：By deptName 關鍵字
     *
     * 返回 List:
     *     使用 myKeyGenerator： 邏輯：【method.getName() + "[" + Arrays.asList(params) + "]"】
     */
    @Cacheable(cacheNames = "deptListCached", keyGenerator = "myKeyGenerator")
    public List<DeptVO> getDeptByDeptName(String dName) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase() // 忽略大小寫
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // 設定匹配條件，這裡使用包含的方式進行匹配
        DeptVO deptExample = DeptVO.builder().deptName(dName).build();
        Example<DeptVO> example = Example.of(deptExample, matcher);
        return deptRepository.findAll(example);
    }
}