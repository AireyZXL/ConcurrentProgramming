package com.concurrent.pro.chapter1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Semaphore实现多生产者/多消费者模式
 *
 * @author zxlei1
 * @date 2018/11/27 16:39
 */
public class ProducerAndConsumer {

    //厨师
    volatile private Semaphore setSemaphore = new Semaphore(10);
    //就餐者
    volatile private Semaphore getSemaphore = new Semaphore(20);
    volatile private ReentrantLock lock = new ReentrantLock();
    volatile private Condition setCondition = lock.newCondition();
    volatile private Condition getCondition = lock.newCondition();
    //最多有4个盒子存放菜品
    volatile private Object[] producePosition = new Object[4];

    private boolean isEmpty() {
        boolean isEmpty = true;
        for (int i = 0; i < producePosition.length; i++) {
            if (producePosition[i] != null) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFull() {
        boolean isFull = true;
        for (int i = 0; i < producePosition.length; i++) {
            if (producePosition[i] == null) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    public void set() {
        System.out.println("set");
        try {
            setSemaphore.acquire();
            lock.lock();
            while (isFull()) {
                System.out.println("生产者在等待");
                setCondition.await();
            }
            for (int i = 0; i < producePosition.length; i++) {
                if (producePosition[i] == null) {
                    producePosition[i] = "数据";
                    System.out.println(Thread.currentThread().getName() + " 生产了 " + producePosition[i]);
                    break;
                }
            }
            getCondition.signalAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            setSemaphore.release();
        }
    }

    public void get() {
        System.out.println("get");
        try {
            getSemaphore.acquire();
            lock.lock();
            while (isEmpty()) {
                System.out.println("消费者在等待");
                getCondition.await();
            }
            for (int i = 0; i < producePosition.length; i++) {
                if (producePosition[i] != null) {
                    System.out.println(Thread.currentThread().getName() + " 消费了 " + producePosition[i]);
                    producePosition[i] = null;
                    break;
                }
            }
            setCondition.signalAll();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            getSemaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerAndConsumer service = new ProducerAndConsumer();
        ThreadP[] threadPS = new ThreadP[60];
        ThreadC[] threadCS = new ThreadC[60];
        for (int i = 0; i < 60; i++) {
            threadPS[i] = new ThreadP(service);
            threadCS[i] = new ThreadC(service);
        }
        Thread.sleep(2000);
        for (int i = 0; i < 60; i++) {
            threadPS[i].start();
            threadCS[i].start();
        }
    }
}

class ThreadP extends Thread {
    private ProducerAndConsumer service;

    public ThreadP(ProducerAndConsumer service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.set();
    }
}

class ThreadC extends Thread {
    private ProducerAndConsumer service;

    public ThreadC(ProducerAndConsumer service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.get();
    }
}
