package ru.otus.hw03;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class GCTesting {

    private static HashMap<String, ArrayList<Long>> eventsMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        long minimalAddTime = 1000;
        long maximumAddTime = 0;
        long averageAddTime = 0;
        long addDelayCount = 0;
        long failAddCount = 0;

        long minimalTrimTime = 0;
        long maximumTrimTime = 0;
        long averageTrimTime = 0;
        long trimDelayCount = 0;
        long failTrimCount = 0;
        SimpleThread simpleThread = new SimpleThread();
        simpleThread.run();
        try {
            while (true) {

                for (int switcher = 0; switcher < 2_306_000; switcher++) {
                    long beginAddTime = System.currentTimeMillis();
                    try {
                        simpleThread.addObject("My Very Big String Value For Index:\t" + switcher);
                    } catch (Exception e) {
                        failAddCount++;
                    }
                    long duration = (System.currentTimeMillis() - beginAddTime) / 10;
                    if (duration < minimalAddTime) {
                        minimalAddTime = duration;
                    }
                    if (duration > maximumAddTime) {
                        maximumAddTime = duration;
                    }
                    averageAddTime = (maximumAddTime - minimalAddTime) / 2;
                    if (duration > minimalAddTime) {
                        addDelayCount++;
                    }
                }
                long beginTrimTime = System.currentTimeMillis();
                try {
                    simpleThread.trimArray();
                } catch (Exception e) {
                    failTrimCount++;
                }
                long duration = (System.currentTimeMillis() - beginTrimTime) / 10;
                if (duration < minimalTrimTime) {
                    minimalTrimTime = duration;
                }
                if (duration > maximumTrimTime) {
                    maximumTrimTime = duration;
                }
                averageTrimTime = (maximumTrimTime - minimalTrimTime) / 2;
                if (duration > minimalTrimTime) {
                    trimDelayCount++;
                }
                Thread.sleep(1000);
            }
        } catch (Throwable e) {
            System.err.println("\n\n\n\n");
            System.err.println("minimalAddTime:\t" + minimalAddTime);
            System.err.println("maximumAddTime:\t" + maximumAddTime);
            System.err.println("averageAddTime:\t" + averageAddTime);
            System.err.println("addDelayCount:\t" + addDelayCount);
            System.err.println("minimalTrimTime:\t" + minimalTrimTime);
            System.err.println("maximumTrimTime:\t" + maximumTrimTime);
            System.err.println("averageTrimTime:\t" + averageTrimTime);
            System.err.println("trimDelayCount:\t" + trimDelayCount);
            System.out.println("failTrimCount:\t" + failTrimCount);
            System.err.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
            System.err.println("\n\n\n\n");
            e.printStackTrace();
            System.err.println("\n\n\n\n");
            for (String event : eventsMap.keySet()) {
                System.out.println("Event:\t" + event + ", duration:\t" + eventsMap.get(event).get(0) + ", count:\t" + eventsMap.get(event).get(1));
            }
            System.err.println("\n\n\n\n");
        }
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();


                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");

                    if (eventsMap.get(gcAction) != null) {
                        eventsMap.get(gcAction).set(0, eventsMap.get(gcAction).get(0) + duration);
                        eventsMap.get(gcAction).set(1, eventsMap.get(gcAction).get(1) + 1L);
                    } else {
                        ArrayList<Long> gcEventParams = new ArrayList<>();
                        gcEventParams.add(duration);
                        gcEventParams.add(1L);
                        eventsMap.put(gcAction, gcEventParams);
                    }
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }


}

class SimpleThread extends Thread {
    LinkedList<Object> linkedList = new LinkedList<>();

    public synchronized Object addObject(Object valueToAdd) throws Exception {
        try {
            return this.linkedList.add(valueToAdd);
        } catch (Exception e) {
            throw e;
        }
    }

    public synchronized Object trimArray() throws Exception {
        try {
            Iterator<Object> iterator = this.linkedList.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.next();
                iterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
            throw e;
        }
        return Boolean.TRUE;
    }
}
