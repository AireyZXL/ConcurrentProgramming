package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * 无参方法tryAcquire()的作用是尝试地获得一个许可，
 * 如果获取不到则返回false，此方法通常与if语句结合使用，并具有无阻塞的特点。
 * 无阻塞的特点是可以使线程不至于在同步处于一直持续等待的状态，
 * 如果if语句判断不成立则线程会继续走else语句，程序会继续走下去。
 *
 * @author zxlei1
 * @date 2018/11/27 10:02
 */
public class TryAcquire {

    private Semaphore semaphore = new Semaphore(1);

    public void method() {
        if (semaphore.tryAcquire()) {
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 首先进入 ");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String newString = new String();
                Math.random();
            }
            semaphore.release();
        } else {
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 未成功进入 ");
        }
    }

    public static void main(String[] args) {
        TryAcquire acquire=new TryAcquire();
        TryAcquireThreadA t1=new TryAcquireThreadA(acquire);
        t1.setName("Thread-----A");
        t1.start();
        TryAcquireThreadB t2=new TryAcquireThreadB(acquire);
        t2.setName("Thread-----B");
        t2.start();
    }

}


class TryAcquireThreadA extends Thread {

    private TryAcquire acquire;

    public TryAcquireThreadA(TryAcquire acquire) {
        this.acquire = acquire;
    }

    @Override
    public void run() {
        acquire.method();
    }
}

class TryAcquireThreadB extends Thread {
    private TryAcquire acquire;

    public TryAcquireThreadB(TryAcquire acquire) {
        this.acquire = acquire;
    }

    @Override
    public void run() {
        acquire.method();
    }
}


