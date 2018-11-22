package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * Semaphore的入门程序
 * permits是许可的意思，代表同一时间内，最多允许多少个线程同时执行acquire()和release()之间的代码
 *
 * @author zxlei1
 * @date 2018/11/19 17:03
 */
public class SemaphoreTest1 {
    private Semaphore semaphore = new Semaphore(1);
    //private Semaphore semaphore = new Semaphore(2);

    public void testMethod() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " begin timer= " + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " end timer= " + System.currentTimeMillis());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SemaphoreTest1 semaphoreTest1 = new SemaphoreTest1();
        ServiceThreadA t1 = new ServiceThreadA(semaphoreTest1);
        t1.setName("Thread----A");
        ServiceThreadB t2 = new ServiceThreadB(semaphoreTest1);
        t2.setName("Thread----B");
        ServiceThreadC t3 = new ServiceThreadC(semaphoreTest1);
        t3.setName("Thread----C");
        t1.start();
        t2.start();
        t3.start();
    }


}


class ServiceThreadA extends Thread {
    private SemaphoreTest1 semaphoreTest1;

    public ServiceThreadA(SemaphoreTest1 semaphoreTest1) {
        this.semaphoreTest1 = semaphoreTest1;
    }

    @Override
    public void run() {
        semaphoreTest1.testMethod();
    }
}

class ServiceThreadB extends Thread {
    private SemaphoreTest1 semaphoreTest1;

    public ServiceThreadB(SemaphoreTest1 semaphoreTest1) {
        this.semaphoreTest1 = semaphoreTest1;
    }

    @Override
    public void run() {
        semaphoreTest1.testMethod();
    }
}

class ServiceThreadC extends Thread {
    private SemaphoreTest1 semaphoreTest1;

    public ServiceThreadC(SemaphoreTest1 semaphoreTest1) {
        this.semaphoreTest1 = semaphoreTest1;
    }

    @Override
    public void run() {
        semaphoreTest1.testMethod();
    }
}