package ru.otus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MainApp {

    private static final AtomicBoolean isRunning = new AtomicBoolean(true);
    private static final AtomicInteger count = new AtomicInteger(1);
    private static final AtomicBoolean reverse = new AtomicBoolean(false);
    private static final AtomicBoolean printed = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(new NumberWriter(reverse, count, isRunning, System.out, printed));
        Thread second = new Thread(new NumberWriter(reverse, count, isRunning, System.err, printed));
        first.start();
        second.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        isRunning.set(false);
    }
}

class NumberWriter implements Runnable {
    private final AtomicBoolean reverse;
    private final AtomicInteger count;
    private final AtomicBoolean isRunning;
    private final OutputStream outputStream;
    private AtomicBoolean printed;

    NumberWriter(AtomicBoolean reverse, AtomicInteger count, AtomicBoolean isRunning, OutputStream outputStream, AtomicBoolean printed) {
        this.reverse = reverse;
        this.count = count;
        this.isRunning = isRunning;
        this.outputStream = outputStream;
        this.printed = printed;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            while (isRunning.get()) {
                writer.write(Thread.currentThread().getName());
                writer.write(": ");
                reverse.getAndSet(!reverse.get());
                if (printed.get()) {
                    writer.write(String.valueOf(reverse.get() ? count.decrementAndGet() : count.incrementAndGet()));
                    printed.set(false);
                } else {
                    writer.write(String.valueOf(count.get()));
                    printed.set(true);//Я так понимаю, это HappensBefore ребро
                }

                writer.write(" ");
                writer.newLine();
                writer.flush();
                if (count.get() >= 10) {
                    reverse.set(false);
                }
                if (count.get() <= 1) {
                    reverse.set(true);
                }
                try {
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(ThreadLocalRandom.current().nextInt(100, 600)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}