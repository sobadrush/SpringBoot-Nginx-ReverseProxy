CREATE SCHEMA IF NOT EXISTS DB_EMP_DEPT;
SET SCHEMA DB_EMP_DEPT;

----------------------------------------------------
DROP TABLE IF EXISTS EMP_TB;
DROP TABLE IF EXISTS DEPT_TB;
DROP TABLE IF EXISTS USER_TB;
----------------------------------------------------

CREATE TABLE DEPT_TB
(
    DEPTNO INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    DNAME  VARCHAR(14),
    LOC    VARCHAR(13)
);

CREATE TABLE EMP_TB
(
    EMPNO    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ENAME    VARCHAR(14),
    JOB      VARCHAR(13),
    HIREDATE DATE,
    DEPTNO   INT NOT NULL,
    FOREIGN KEY (DEPTNO) REFERENCES DEPT_TB (DEPTNO)
    -- Hibernate做關聯查詢其實可以不用在實體表格設定foreign-key
);

CREATE TABLE USER_TB
(
    ID UUID PRIMARY KEY,
    EMAIL VARCHAR(200) NOT NULL,
    PASSWORD_DIGEST VARCHAR(1000) NOT NULL,
    NAME VARCHAR(255) NOT NULL
);