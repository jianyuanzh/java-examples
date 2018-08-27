package cc.databus.springboot.demo.tasks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Component
public class TestAsyncTask {

    @Async
    public Future<Boolean> doTask11(final CountDownLatch countDownLatch) throws Exception {
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("任务1耗时:" + (end - start) + "毫秒");
        countDownLatch.countDown();
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask22(final CountDownLatch countDownLatch) throws Exception {
        long start = System.currentTimeMillis();
        Thread.sleep(700);
        long end = System.currentTimeMillis();
        System.out.println("任务2耗时:" + (end - start) + "毫秒");
        countDownLatch.countDown();
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask33(final CountDownLatch latch) throws Exception {
        long start = System.currentTimeMillis();
        Thread.sleep(600);
        long end = System.currentTimeMillis();
        System.out.println("任务3耗时:" + (end - start) + "毫秒");
        latch.countDown();
        return new AsyncResult<>(true);
    }
}
