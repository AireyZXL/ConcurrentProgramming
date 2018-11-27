package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * 公平信号量是获得锁的顺序与线程启动的顺序无关，但不代表100%地获得信号量，仅仅在在概率上得到保证。
 * 而非公平信号量就是无关的了
 *
 * @author zxlei1
 * @date 2018/11/22 15:30
 */
public class SemaphoreFair {

    private boolean isFair = false;
    private Semaphore semaphore = new Semaphore(1, isFair);

    public void testMethod1() {
        try {
            semaphore.acquire();
            System.out.println("ThreadName= " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    public static void main(String[] args) {
        SemaphoreFair semaphoreFair = new SemaphoreFair();
        FairThread thread = new FairThread(semaphoreFair);
        thread.start();
        FairThread[] fairThreads = new FairThread[4];
        for (int i = 0; i < 4; i++) {
            fairThreads[i] = new FairThread(semaphoreFair);
            fairThreads[i].start();
        }
    }
}

class FairThread extends Thread {
    private SemaphoreFair semaphoreFair;

    public FairThread(SemaphoreFair semaphoreFair) {
        this.semaphoreFair = semaphoreFair;
    }

    @Override
    public void run() {
        System.out.println("ThreadName= " + this.getName() + " 启动了！");
        semaphoreFair.testMethod1();
    }
}
