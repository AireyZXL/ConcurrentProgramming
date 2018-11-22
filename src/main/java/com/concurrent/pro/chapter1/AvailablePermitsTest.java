package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * availablePermits()和drainPermits()
 * availablePermits()返回此Semaphore对象中当前可用的许可数，此方法通常用于调试，
 * 因为许可的数量有可能实时在改变，并不是固定的数量。
 * drainPermits()可获取并返回立即可用的所有许可个数，并将可用许可置为0。
 *
 * @author zxlei1
 * @date 2018/11/20 15:47
 */
public class AvailablePermitsTest {
    private Semaphore semaphore=new Semaphore(10);

    public void testMethod(){
        try {
            semaphore.acquire();
            System.out.println(semaphore.availablePermits());
            System.out.println(semaphore.availablePermits());
            System.out.println(semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String [] args){
        AvailablePermitsTest avi=new AvailablePermitsTest();
        avi.testMethod();
    }
}
