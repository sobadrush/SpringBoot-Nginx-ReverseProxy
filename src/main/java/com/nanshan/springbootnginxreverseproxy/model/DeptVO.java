package com.nanshan.springbootnginxreverseproxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author RogerLo
 * @date 2023/6/12
 */
@Data
@Builder
@Entity
@Table(name = "DEPT_TB", schema = "DB_EMP_DEPT")
@NoArgsConstructor
@AllArgsConstructor
public class DeptVO implements Serializable {

    @Id
    @Column(name = "DEPTNO", nullable = false, unique = true)
    private Long deptNo;

    @Column(name = "DNAME", nullable = true, unique = false)
    private String deptName;

    @Column(name = "LOC", nullable = true, unique = false)
    private String deptLoc;

}
