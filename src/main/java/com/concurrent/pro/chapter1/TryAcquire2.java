package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * tryAcquire(int permits)的作用
 * 尝试地获得x个许可，如果获取不到则返回false
 *
 * @author zxlei1
 * @date 2018/11/27 10:19
 */
public class TryAcquire2 {

    private Semaphore semaphore = new Semaphore(3);

    public void testMethod() {
        if (semaphore.tryAcquire(3)) {

            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 首先进入 ");
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String newString = new String();
                Math.random();
            }
            //方法release对应的permits的值也要修改
            semaphore.release(3);

        } else {
            System.out.println("ThreadName= " + Thread.currentThread().getName() + " 未成功进入 ");
        }
    }

    public static void main(String[] args) {
        TryAcquire2 acquire2=new TryAcquire2();
        TryAcquire2ThreadA t1=new TryAcquire2ThreadA(acquire2);
        t1.setName("Thread----A");
        t1.start();
        TryAcquire2ThreadB t2=new TryAcquire2ThreadB(acquire2);
        t2.setName("Thread----B");
        t2.start();

    }


}

class TryAcquire2ThreadA extends Thread{

    private TryAcquire2 acquire2;

    public TryAcquire2ThreadA(TryAcquire2 acquire2) {
        this.acquire2 = acquire2;
    }

    @Override
    public void run() {
        acquire2.testMethod();
    }
}

class TryAcquire2ThreadB extends Thread{
    private TryAcquire2 acquire2;

    public TryAcquire2ThreadB(TryAcquire2 acquire2) {
        this.acquire2 = acquire2;
    }

    @Override
    public void run() {
        acquire2.testMethod();
    }
}

