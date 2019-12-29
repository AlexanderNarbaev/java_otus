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

    public static void main(String[] args) {
        switchOnMonitoring();
        Metrica.beginTime = System.currentTimeMillis();
        Metrica.endTime = Metrica.beginTime + (15 * 60 * 1000);
        SimpleThread simpleThread = new SimpleThread();
        simpleThread.run();
        try {
            while (System.currentTimeMillis() <= Metrica.endTime) {
                Metrica.workTime = System.currentTimeMillis() - Metrica.beginTime;
                for (int switcher = 0; switcher < 2_300_000; switcher++) {
                    long beginAddTime = System.currentTimeMillis();
                    try {
                        simpleThread.addObject("Yet Another Биг Руссиан And Spanish Very Big String Value For Index:\t" + switcher);
                        Metrica.totalAddCount++;
                    } catch (Exception e) {
                        Metrica.failAddCount++;
                    }
                    long duration = (System.currentTimeMillis() - beginAddTime);
                    if (duration < Metrica.minimalAddTime) {
                        Metrica.minimalAddTime = duration;
                    }
                    if (duration > Metrica.maximumAddTime) {
                        Metrica.maximumAddTime = duration;
                    }
                    Metrica.averageAddTime = (Metrica.maximumAddTime + Metrica.minimalAddTime) / 2;
                    if (duration > Metrica.minimalAddTime) {
                        Metrica.addDelayCount++;
                    }
                }
                long beginTrimTime = System.currentTimeMillis();
                try {
                    simpleThread.trimArray();
                } catch (Exception e) {
                    Metrica.failTrimCount++;
                }
                long duration = (System.currentTimeMillis() - beginTrimTime);
                if (duration < Metrica.minimalTrimTime) {
                    Metrica.minimalTrimTime = duration;
                }
                if (duration > Metrica.maximumTrimTime) {
                    Metrica.maximumTrimTime = duration;
                }
                Metrica.averageTrimTime = (Metrica.maximumTrimTime + Metrica.minimalTrimTime) / 2;
                if (duration > Metrica.minimalTrimTime) {
                    Metrica.trimDelayCount++;
                }
                Thread.sleep(1000);
            }
        } catch (Throwable e) {
            e.printStackTrace();
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

                    if (Metrica.eventsMap.get(gcAction) != null) {
                        Metrica.eventsMap.get(gcAction).set(0, Metrica.eventsMap.get(gcAction).get(0) + duration);
                        Metrica.eventsMap.get(gcAction).set(1, Metrica.eventsMap.get(gcAction).get(1) + 1L);
                    } else {
                        ArrayList<Long> gcEventParams = new ArrayList<>();
                        gcEventParams.add(duration);
                        gcEventParams.add(1L);
                        Metrica.eventsMap.put(gcAction, gcEventParams);
                    }
                    Metrica.initialGCEventCount++;
                    if (Metrica.initialGCEventCount % 10 == 0) {
                        Metrica.printDebugInfo();
                    }
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }


}

class Metrica {
    static long minimalAddTime = 1000;
    static long maximumAddTime = 0;
    static long averageAddTime = 0;
    static long addDelayCount = 0;
    static long failAddCount = 0;
    static long addThroughput = 0;

    static long minimalTrimTime = 1000;
    static long maximumTrimTime = 0;
    static long averageTrimTime = 0;
    static long trimDelayCount = 0;
    static long failTrimCount = 0;
    static long beginTime = 0;
    static long endTime = 0;
    static long initialGCEventCount = 0;
    static long workTime = 0;
    static long totalAddCount = 0;

    static HashMap<String, ArrayList<Long>> eventsMap = new HashMap<>();

    static void printDebugInfo() {
        System.err.println("minimalAddTime:\t" + Metrica.minimalAddTime);
        System.err.println("maximumAddTime:\t" + Metrica.maximumAddTime);
        System.err.println("averageAddTime:\t" + Metrica.averageAddTime);
        System.err.println("addDelayCount:\t" + Metrica.addDelayCount);
        System.err.println("failAddCount:\t" + Metrica.failAddCount);
        System.err.println("addThroughput:\t" + (Metrica.totalAddCount / Metrica.workTime));
        System.err.println("minimalTrimTime:\t" + Metrica.minimalTrimTime);
        System.err.println("maximumTrimTime:\t" + Metrica.maximumTrimTime);
        System.err.println("averageTrimTime:\t" + Metrica.averageTrimTime);
        System.err.println("trimDelayCount:\t" + Metrica.trimDelayCount);
        System.err.println("failTrimCount:\t" + Metrica.failTrimCount);
        System.err.println("time:" + Metrica.workTime / 1000);
        for (String event : eventsMap.keySet()) {
            System.err.println("Event:\t" + event + ", duration:\t" + eventsMap.get(event).get(0) + ", count:\t" + eventsMap.get(event).get(1));
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
                iterator.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return Boolean.TRUE;
    }
}
