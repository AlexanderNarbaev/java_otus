package ru.otus;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainApp {
    private boolean isRunning = true;
    private boolean reverse = false;
    private boolean printed = true;
    private int count = 0;

    public synchronized void printCount(boolean initialState) {
        while (isRunning) {
            try {
                while (printed == initialState) {
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + ": " + (printed ? (reverse ? --count : ++count) : count));
                printed = !printed;
                if (count >= 10) {
                    reverse = true;
                }
                if (count <= 1) {
                    reverse = false;
                }
                Thread.sleep(ThreadLocalRandom.current().nextLong(300, 800));
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MainApp app = new MainApp();
        Thread t1 = new Thread(() -> app.printCount(false));
        Thread t2 = new Thread(() -> app.printCount(true));
        t1.setName("Thread_ONE");
        t2.setName("Thread_TWO");
        t1.start();
        t2.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(40));
        app.isRunning = false;
    }
}