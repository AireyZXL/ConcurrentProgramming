package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * drainPermits()的测试类
 * drainPermits()可获取并返回立即可用的所有许可个数，并将可用许可置为0。
 * @author zxlei1
 * @date 2018/11/20 15:59
 */
public class DrainPermitsTest {

    private Semaphore semaphore=new Semaphore(10);

    public void testMethod(){
        try {
            semaphore.acquire();
            System.out.println(semaphore.availablePermits());
            System.out.println(semaphore.drainPermits()+"----"+semaphore.availablePermits());
            System.out.println(semaphore.drainPermits()+"----"+semaphore.availablePermits());
            System.out.println(semaphore.drainPermits()+"----"+semaphore.availablePermits());
            System.out.println(semaphore.drainPermits()+"----"+semaphore.availablePermits());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }

    }


    public static void main(String [] args){
         DrainPermitsTest drt=new DrainPermitsTest();
         drt.testMethod();
    }
}
