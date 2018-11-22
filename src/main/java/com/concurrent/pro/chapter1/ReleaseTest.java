package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * 如果多次调用Semaphore类的relase()或者relase(int)方法时，还可以动态增加permits的个数
 * 5并不是最终许可的数量，仅仅是初始的状态值
 *
 * @author zxlei1
 * @date 2018/11/20 15:23
 */
public class ReleaseTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        try {
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            System.out.println(semaphore.availablePermits());
            semaphore.release();
            semaphore.release();
            semaphore.release();
            semaphore.release();
            semaphore.release();
            semaphore.release();
            System.out.println(semaphore.availablePermits());
            semaphore.release(4);
            System.out.println(semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
