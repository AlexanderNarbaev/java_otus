package ru.otus.hw08;

import lombok.Data;

@Data
public class ClassToTestJsonSerialization {
    private boolean testBooleanPrimitive = true;
    private byte testBytePrimitive = 1;
    private short testShortPrimitive = 10;
    private int testIntPrimitive = 100;
    private long testLongPrimitive = 1000;
    private float testFloatPrimitive = 10.01f;
    private double testDoublePrimitive = 1000.0001d;
    private int[] testArrayOfPrimitives = new int[]{100, 1000, 10000, 1, 10};
    private String[] testArrayOfObjects = new String[]{"First", "Second", "Zero", "Two", "Five"};
    private String testObject = "TestValue";
}
