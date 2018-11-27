package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * tryAcquire(int permits,long timeout,TimeUnit unit)的作用
 * 指定的时间内尝试获取x个许可，如果获取不到则返回false
 *
 * @author zxlei1
 * @date 2018/11/27 10:19
 */
public class TryAcquire4 {

    private Semaphore semaphore = new Semaphore(3);

    public void testMethod() {
        try {
            if (semaphore.tryAcquire(3,3, TimeUnit.SECONDS)) {

                System.out.println("ThreadName= " + Thread.currentThread().getName() + " 首先进入 ");
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    //注释则双双获取许可
                    String newString = new String();
                    Math.random();
                }
                semaphore.release(3);

            } else {
                System.out.println("ThreadName= " + Thread.currentThread().getName() + " 未成功进入 ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TryAcquire4 acquire4 = new TryAcquire4();
        TryAcquire4ThreadA t1 = new TryAcquire4ThreadA(acquire4);
        t1.setName("Thread----A");
        t1.start();
        TryAcquire4ThreadB t3 = new TryAcquire4ThreadB(acquire4);
        t3.setName("Thread----B");
        t3.start();

    }


}

class TryAcquire4ThreadA extends Thread {

    private TryAcquire4 acquire4;

    public TryAcquire4ThreadA(TryAcquire4 acquire4) {
        this.acquire4 = acquire4;
    }

    @Override
    public void run() {
        acquire4.testMethod();
    }
}

class TryAcquire4ThreadB extends Thread {
    private TryAcquire4 acquire4;

    public TryAcquire4ThreadB(TryAcquire4 acquire4) {
        this.acquire4 = acquire4;
    }

    @Override
    public void run() {
        acquire4.testMethod();
    }
}

