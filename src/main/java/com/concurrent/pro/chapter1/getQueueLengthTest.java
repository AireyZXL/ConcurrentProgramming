package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;

/**
 * getQueueLength()：取得等待许可的线程个数
 * hasQueuedThreads()：判断有没有线程在等待这个许可
 *
 * @author zxlei1
 * @date 2018/11/22 15:17
 */
public class getQueueLengthTest {

    private Semaphore semaphore = new Semaphore(1);

    public void testMethod() {
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println("还有大约 " + semaphore.getQueueLength() + " 个线程在等待");
            System.out.println("是否有线程在等待信号量呢？ " + semaphore.hasQueuedThreads());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        getQueueLengthTest queuelenth = new getQueueLengthTest();
        QueueLenthThread t = new QueueLenthThread(queuelenth);
        t.start();

        QueueLenthThread[] threadArr = new QueueLenthThread[4];
        for (int i = 0; i < 4; i++) {
            threadArr[i] = new QueueLenthThread(queuelenth);
            threadArr[i].start();
        }
    }

}

class QueueLenthThread extends Thread {
    private getQueueLengthTest getQueueLengthTest;

    public QueueLenthThread(com.concurrent.pro.chapter1.getQueueLengthTest getQueueLengthTest) {
        this.getQueueLengthTest = getQueueLengthTest;
    }

    @Override
    public void run() {
        getQueueLengthTest.testMethod();
    }
}
