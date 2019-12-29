package ru.otus.hw04.asm;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * java -javaagent:HW04_LOG_ANNO-jar-with-dependencies.jar -jar HW04_LOG_ANNO-jar-with-dependencies.jar
 */
public class LoggingDemo {

    public static void main(String[] args) throws Exception {
        int i = 1;
        short sh = 1;
        byte bt = 1;
        long lg = 1;
        float ft = 1.0f;
        double dl = 1.0d;
        char ch = 1;
        boolean bl = true;
        Object obj = new Object();
        String sg = "I'am string";
        Calendar cr = new GregorianCalendar();
        CalculationClassImpl sample = new CalculationClassImpl();
        sample.calculation(i);
        sample.calculation(sh);
        sample.calculation(bt);
        sample.calculation(lg);
        sample.calculation(ft);
        sample.calculation(dl);
        sample.calculation(ch);
        sample.calculation(bl);
        sample.calculation(obj);
        sample.calculation(sg);
        sample.calculation(cr);
        sample.calculation(new Random().nextInt(100), new Random().nextInt(100));
        Short.valueOf(sh).toString();
    }
}
