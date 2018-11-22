package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * 方法acquire(int permits)参数作用及动态添加permits许可数量
 *
 * @author zxlei1
 * @date 2018/11/20 14:47
 */
public class AcquireTest {
    private Semaphore semaphore = new Semaphore(10);

    public void testMethod() {
        try {
            semaphore.acquire(2);
            System.out.println(Thread.currentThread().getName() + " begin timer= " + System.currentTimeMillis());
            int sleepValue = (int) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() + " 停止了 " + sleepValue + " 秒");
            Thread.sleep(sleepValue);
            System.out.println(Thread.currentThread().getName() + " end timer= " + System.currentTimeMillis());
            semaphore.release(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AcquireTest act = new AcquireTest();
        AcquireThread[] acquireThreads = new AcquireThread[10];
        for (int i = 0; i < 10; i++) {
            acquireThreads[i] = new AcquireThread(act);
            acquireThreads[i].start();
        }

    }
}


class AcquireThread extends Thread {
    private AcquireTest acquireTest;

    public AcquireThread(AcquireTest acquireTest) {
        this.acquireTest = acquireTest;
    }

    @Override
    public void run() {
        acquireTest.testMethod();
    }
}