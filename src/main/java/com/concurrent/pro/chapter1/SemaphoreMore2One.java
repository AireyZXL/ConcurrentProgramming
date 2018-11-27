package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多进路-单处理-多出路实验
 * 允许多个线程同时处理任务，但执行任务的顺序却是同步的，也就是阻塞的所以也称单处理。
 *
 * @author zxlei1
 * @date 2018/11/27 11:29
 */
public class SemaphoreMore2One {
   private Semaphore semaphore=new Semaphore(3);
   private ReentrantLock lock=new ReentrantLock();

    public void sayHello() {
        try {
            semaphore.acquire();
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 准备 ");
            lock.lock();
            System.out.println("begin hello " + System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 打印 " + (i + 1));
            }
            System.out.println("end hello " + System.currentTimeMillis());
            lock.unlock();
            semaphore.release();
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 结束 ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SemaphoreMore2One semm=new SemaphoreMore2One();
        SemaphoreMore2OneThread [] t=new SemaphoreMore2OneThread[12];
        for (int i = 0; i < t.length; i++) {
            t[i]=new SemaphoreMore2OneThread(semm);
            t[i].start();
        }
    }



}


class SemaphoreMore2OneThread extends Thread{
    private SemaphoreMore2One semo;

    public SemaphoreMore2OneThread(SemaphoreMore2One semo) {
        this.semo = semo;
    }

    @Override
    public void run() {
        semo.sayHello();
    }
}
