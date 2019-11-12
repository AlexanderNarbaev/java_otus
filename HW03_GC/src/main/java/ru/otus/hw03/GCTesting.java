package ru.otus.hw03;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

/**
 * -Xms10G
 * -Xmx10G
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:+UseG1GC
 */
public class GCTesting {

    private static HashMap<String, ArrayList<Long>> eventsMap = new HashMap<>();
    private static long minimalAddTime = 1000;
    private static long maximumAddTime = 0;
    private static long averageAddTime = 0;
    private static long addDelayCount = 0;
    private static long failAddCount = 0;
    private static long addThroughput = 0;

    private static long minimalTrimTime = 1000;
    private static long maximumTrimTime = 0;
    private static long averageTrimTime = 0;
    private static long trimDelayCount = 0;
    private static long failTrimCount = 0;
    private static long beginTime = 0;
    private static long endTime = 0;
    private static long initialGCEventCount = 0;
    private static long workTime = 0;
    private static long totalAddCount = 0;

    public static void main(String[] args) {
        switchOnMonitoring();
        beginTime = System.currentTimeMillis();
        endTime = beginTime + (15 * 60 * 1000);
        SimpleThread simpleThread = new SimpleThread();
        simpleThread.run();
        try {
            while (System.currentTimeMillis() <= endTime) {
                workTime = System.currentTimeMillis() - beginTime;
                for (int switcher = 0; switcher < 2_300_000; switcher++) {
                    long beginAddTime = System.currentTimeMillis();
                    try {
                        simpleThread.addObject("Yet Another Биг Руссиан And Spanish Very Big String Value For Index:\t" + switcher);
                        totalAddCount++;
                    } catch (Exception e) {
                        failAddCount++;
                    }
                    long duration = (System.currentTimeMillis() - beginAddTime);
                    if (duration < minimalAddTime) {
                        minimalAddTime = duration;
                    }
                    if (duration > maximumAddTime) {
                        maximumAddTime = duration;
                    }
                    averageAddTime = (maximumAddTime + minimalAddTime) / 2;
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
                long duration = (System.currentTimeMillis() - beginTrimTime);
                if (duration < minimalTrimTime) {
                    minimalTrimTime = duration;
                }
                if (duration > maximumTrimTime) {
                    maximumTrimTime = duration;
                }
                averageTrimTime = (maximumTrimTime + minimalTrimTime) / 2;
                if (duration > minimalTrimTime) {
                    trimDelayCount++;
                }
                Thread.sleep(1000);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private static void printDebugInfo() {
        System.err.println("minimalAddTime:\t" + minimalAddTime);
        System.err.println("maximumAddTime:\t" + maximumAddTime);
        System.err.println("averageAddTime:\t" + averageAddTime);
        System.err.println("addDelayCount:\t" + addDelayCount);
        System.err.println("failAddCount:\t" + failAddCount);
        System.err.println("addThroughput:\t" + (totalAddCount / workTime));
        System.err.println("minimalTrimTime:\t" + minimalTrimTime);
        System.err.println("maximumTrimTime:\t" + maximumTrimTime);
        System.err.println("averageTrimTime:\t" + averageTrimTime);
        System.err.println("trimDelayCount:\t" + trimDelayCount);
        System.err.println("failTrimCount:\t" + failTrimCount);
        System.err.println("time:" + workTime / 1000);
        for (String event : eventsMap.keySet()) {
            System.err.println("Event:\t" + event + ", duration:\t" + eventsMap.get(event).get(0) + ", count:\t" + eventsMap.get(event).get(1));
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcAction = info.getGcAction();
                    long duration = info.getGcInfo().getDuration();

                    if (eventsMap.get(gcAction) != null) {
                        eventsMap.get(gcAction).set(0, eventsMap.get(gcAction).get(0) + duration);
                        eventsMap.get(gcAction).set(1, eventsMap.get(gcAction).get(1) + 1L);
                    } else {
                        ArrayList<Long> gcEventParams = new ArrayList<>();
                        gcEventParams.add(duration);
                        gcEventParams.add(1L);
                        eventsMap.put(gcAction, gcEventParams);
                    }
                    initialGCEventCount++;
                    if (initialGCEventCount % 10 == 0) {
                        printDebugInfo();
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
            throw e;
        }
        return Boolean.TRUE;
    }
}
