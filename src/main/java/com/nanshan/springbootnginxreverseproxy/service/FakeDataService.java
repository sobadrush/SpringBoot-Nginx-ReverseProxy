package com.nanshan.springbootnginxreverseproxy.service;

import com.nanshan.springbootnginxreverseproxy.model.DeptVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

/**
 * @author RogerLo
 * @date 2023/8/5
 */
@Service
public class FakeDataService {

    private void sleepSeconds(int secs, String threadName) {
        for (int i = secs; i > 0 ; i--) {
            try {
                System.out.println(MessageFormat.format("[{0}] {1}s ...", threadName, i));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // @Async("my-executor")
    public DeptVO getFakeDept001() {
        String thName = Thread.currentThread().getName();
        System.out.println(MessageFormat.format("[{0}] running getFakeDept001()", thName));
        this.sleepSeconds(1, thName);
        return DeptVO.builder()
                .deptNo(2222L)
                .deptName("國防部")
                .deptLoc("林口")
                .build();
    }

    // @Async("my-executor")
    public DeptVO getFakeDept002() {
        String thName = Thread.currentThread().getName();
        System.out.println(MessageFormat.format("[{0}] running getFakeDept002()", thName));
        this.sleepSeconds(5, thName);
        return DeptVO.builder()
                .deptNo(3333L)
                .deptName("交通部")
                .deptLoc("北投")
                .build();
    }

    @Async("my-executor")
    public CompletableFuture<DeptVO> getFakeDeptFuture001() {
        String thName = Thread.currentThread().getName();
        System.out.println(MessageFormat.format("[{0}] running getFakeDept001()", thName));
        this.sleepSeconds(1, thName);
        return CompletableFuture.completedFuture(DeptVO.builder()
                .deptNo(2222L)
                .deptName("國防部")
                .deptLoc("林口")
                .build()
        );
    }

    @Async("my-executor")
    public CompletableFuture<DeptVO> getFakeDeptFuture002() {
        String thName = Thread.currentThread().getName();
        System.out.println(MessageFormat.format("[{0}] running getFakeDept002()", thName));
        this.sleepSeconds(5, thName);
        return CompletableFuture.completedFuture(DeptVO.builder()
                .deptNo(3333L)
                .deptName("交通部")
                .deptLoc("北投")
                .build()
        );
    }

}
