package ru.otus.hw04.asm;

import java.util.Random;

/**
 * java -javaagent:HW04_LOG_ANNO-jar-with-dependencies.jar -jar HW04_LOG_ANNO-jar-with-dependencies.jar
 */
public class LoggingDemo {

    public static void main(String[] args) throws Exception {
        CalculationClassImpl sample = new CalculationClassImpl();
        sample.calculation(new Random().nextInt(100));
    }
}
