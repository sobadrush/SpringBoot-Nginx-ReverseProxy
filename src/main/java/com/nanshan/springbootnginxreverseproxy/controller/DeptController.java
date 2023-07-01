package com.nanshan.springbootnginxreverseproxy.controller;

import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import com.nanshan.springbootnginxreverseproxy.repository.DeptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RogerLo
 * @date 2023/7/1
 */
@RestController
@Slf4j
public class DeptController {

    @Autowired
    private DeptRepository deptRepository;

    @PostMapping("/getDept/{dname}")
    public DeptVO getDept(@PathVariable("dname") String deptName) {
        log.info(" === 進入 DeptController.getDept，取得所有部門 === ");
        return deptRepository.findByDeptName(deptName).orElse(null);
    }

}
