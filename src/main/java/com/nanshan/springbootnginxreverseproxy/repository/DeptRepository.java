package com.nanshan.springbootnginxreverseproxy.repository;

import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<DeptVO, Long> {

    @Query("select d from DeptVO d where d.deptName = ?1 and d.deptLoc = ?2")
    Optional<DeptVO> findByDeptNameAndDeptLoc(String deptName, String deptLoc);

    Optional<DeptVO> findByDeptName(String deptName);

}
