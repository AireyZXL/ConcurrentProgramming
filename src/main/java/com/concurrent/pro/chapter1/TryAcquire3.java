package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * tryAcquire(long timeout,TimeUnit unit)的作用
 * 指定的时间内尝试获取一个许可，如果获取不到则返回false
 *
 * @author zxlei1
 * @date 2018/11/27 10:19
 */
public class TryAcquire3 {

    private Semaphore semaphore = new Semaphore(1);

    public void testMethod() {
        try {
            if (semaphore.tryAcquire(3, TimeUnit.SECONDS)) {

                System.out.println("ThreadName= " + Thread.currentThread().getName() + " 首先进入 ");
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    //注释则双双获取许可
//                    String newString = new String();
//                    Math.random();
                }
                semaphore.release();

            } else {
                System.out.println("ThreadName= " + Thread.currentThread().getName() + " 未成功进入 ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TryAcquire4 acquire3 = new TryAcquire4();
        TryAcquire4ThreadA t1 = new TryAcquire4ThreadA(acquire3);
        t1.setName("Thread----A");
        t1.start();
        TryAcquire4ThreadB t3 = new TryAcquire4ThreadB(acquire3);
        t3.setName("Thread----B");
        t3.start();

    }


}

class TryAcquire3ThreadA extends Thread {

    private TryAcquire4 acquire3;

    public TryAcquire3ThreadA(TryAcquire4 acquire3) {
        this.acquire3 = acquire3;
    }

    @Override
    public void run() {
        acquire3.testMethod();
    }
}

class TryAcquire3ThreadB extends Thread {
    private TryAcquire4 acquire3;

    public TryAcquire3ThreadB(TryAcquire4 acquire3) {
        this.acquire3 = acquire3;
    }

    @Override
    public void run() {
        acquire3.testMethod();
    }
}

