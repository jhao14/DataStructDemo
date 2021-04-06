package com.jhao.threaddemo.ch2.tools;

import com.jhao.threaddemo.tools.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * @author JiangHao
 * @date 2021/4/6
 * @describe CountDownLatch用法
 * 共5个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class UseCountDownLatch {

    static CountDownLatch latch = new CountDownLatch(6);

    static class InitThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread_" + Thread.currentThread().getId()
                    + " ready init work......");
            //扣减点
            latch.countDown();
            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " ........continue do its work");
            }
        }
    }

    /**
     * 业务线程等待latch的计数器为0  完成
     */
    private static class BusinessThread implements Runnable {
        @Override
        public void run() {
            try {
                //等待其他线程执行完毕
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("BusinessThread" + Thread.currentThread().getId()
                        + " do business-----");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " ready init work step 1st......");
                latch.countDown();
                System.out.println("begin step 2nd.......");
                SleepTools.ms(1);
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + " ready init work step 2nd......");
                latch.countDown();
            }
        }).start();
        new Thread(new BusinessThread()).start();
        for (int i = 0; i <= 3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();
        System.out.println("Main do ites work........");
    }
}