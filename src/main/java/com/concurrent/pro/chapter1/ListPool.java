package com.concurrent.pro.chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类Semaphore可以有效的对并发执行任务的线程数量进行限制
 * 本实验的功能是同时有若干个线程可以访问池中的数据，但同时只有一个线程可以取得数据，使用完毕后再放回池中
 *
 * @author zxlei1
 * @date 2018/11/27 14:28
 */
public class ListPool {

    private int poolMaxSize = 3;
    private int semaphorePermits = 5;
    private List<String> list = new ArrayList<String>();
    private Semaphore concurrencySemaphore = new Semaphore(semaphorePermits);
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public ListPool() {
        super();
        for (int i = 0; i < poolMaxSize; i++) {
            list.add("高洪岩" + (i + 1));
        }
    }

    public String get() {
        String getString = null;

        try {
            concurrencySemaphore.acquire();
            lock.lock();
            while (list.size() == 0) {
                condition.await();
            }
            getString = list.remove(0);
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getString;
    }

    public void put(String stringValue) {
        lock.lock();
        list.add(stringValue);
        condition.signalAll();
        lock.unlock();
        concurrencySemaphore.release();
    }

    public static void main(String[] args) {
        ListPool listPool = new ListPool();

        ListPoolThread[] threads = new ListPoolThread[12];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ListPoolThread(listPool);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

    }
}


class ListPoolThread extends Thread {
    private ListPool listPool;

    public ListPoolThread(ListPool listPool) {
        this.listPool = listPool;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String getString = listPool.get();
            System.out.println(Thread.currentThread().getName() + " 取的值 " + getString);
            listPool.put(getString);
        }
    }
}