package com.jhao.threaddemo.ch1;

import sun.applet.Main;

/**
 * @author JiangHao
 * @date 2021/4/5
 * @describe 测试Sleep对锁的影响
 */
public class SleepLock {
    private Object lock = new Object();

    public static void main(String[] args) {
        SleepLock sleepLock = new SleepLock();
        Thread threadA = sleepLock.new ThreadSleep();
        threadA.setName("ThreadSleep");
        Thread threadB = sleepLock.new ThreadNotSleep();
        threadB.setName("ThreadNotSleep");
        //拿锁后休眠，不会释放锁
        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();

    }

    private class ThreadSleep extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock");
            try {

                synchronized (lock) {
                    System.out.println(threadName + " taking the lock");
                    Thread.sleep(5000);
                    System.out.println("Finish the work: " + threadName);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    private class ThreadNotSleep extends Thread {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock time=" + System.currentTimeMillis());
            synchronized (lock) {
                System.out.println(threadName + " taking the lock time=" + System.currentTimeMillis());
                System.out.println("Finish the work: " + threadName);
            }
        }
    }
}
