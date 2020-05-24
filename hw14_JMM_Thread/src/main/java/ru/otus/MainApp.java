package ru.otus;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainApp {
    private final Object monitor = new Object();
    private boolean isRunning = true;
    private boolean reverse = false;
    private boolean printed = true;
    private int count = 0;

    public void printCount() {
        while (isRunning) {
            synchronized (monitor) {
                if (printed) {
                    System.out.println(Thread.currentThread().getName() + ": " + (reverse ? --count : ++count));
                    printed = false;
                } else {
                    System.out.println(Thread.currentThread().getName() + ": " + count);
                    printed = true;
                }
                if (count >= 10) {
                    reverse = true;
                }
                if (count <= 1) {
                    reverse = false;
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(400, 800));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MainApp app = new MainApp();
        Thread t1 = new Thread(app::printCount);
        Thread t2 = new Thread(app::printCount);
        t1.setName("Thread_ONE");
        t2.setName("Thread_TWO");
        t1.start();
        t2.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        app.isRunning = false;
    }
}