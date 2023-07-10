package com.nanshan.springbootnginxreverseproxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DEPTNO", nullable = false, unique = true)
    @NotNull(message = "部門編號不可為空")
    @Min(value = 0, message = "部門編號需 > 0")
    private Long deptNo;

    @Column(name = "DNAME", nullable = true, unique = false)
    @NotBlank(message = "部門名稱不可為空")
    @Size(min = 2, max = 3)
    private String deptName;

    @Column(name = "LOC", nullable = true, unique = false)
    @NotBlank(message = "部門地址不可為空")
    private String deptLoc;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
