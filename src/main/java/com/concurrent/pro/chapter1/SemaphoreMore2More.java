package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * 多进程-多处理-多出路实验：
 * 允许多个线程同时处理任务，更具体来讲，也就是每个线程都在处理自己的任务
 *
 * @author zxlei1
 * @date 2018/11/27 11:16
 */
public class SemaphoreMore2More {

    private Semaphore semaphore = new Semaphore(3);

    public void sayHello() {
        try {
            semaphore.acquire();
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 准备 ");
            System.out.println("begin hello " + System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 打印 " + (i + 1));
            }
            System.out.println("end hello " + System.currentTimeMillis());
            semaphore.release();
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 结束 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SemaphoreMore2More semm=new SemaphoreMore2More();
        SemaphoreMore2MoreThread [] t=new SemaphoreMore2MoreThread[12];
        for (int i = 0; i < t.length; i++) {
            t[i]=new SemaphoreMore2MoreThread(semm);
            t[i].start();
        }
    }
}

class SemaphoreMore2MoreThread extends Thread{
    private SemaphoreMore2More semm;

    public SemaphoreMore2MoreThread(SemaphoreMore2More semm) {
        this.semm = semm;
    }

    @Override
    public void run() {
        semm.sayHello();
    }
}
