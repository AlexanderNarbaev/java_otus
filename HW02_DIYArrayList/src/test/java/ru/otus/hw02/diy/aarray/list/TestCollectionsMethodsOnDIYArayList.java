package ru.otus.hw02.diy.aarray.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("Проверяем собственную имплементацию интерфейса List")
public class TestCollectionsMethodsOnDIYArayList {

    @Test
    @DisplayName("Проверяем поддержку метода Collections.static <T> void sort(List<T> list, Comparator<? super T> c)")
    void testSort() {
        SecureRandom random = new SecureRandom();
        //Дефолтная реализация
        ArrayList<Integer> usualArray = new ArrayList<>();
        //Собственная реализация
        DIYArrayList selfArray = new DIYArrayList();
        //Заполняем обе коллекции одинаковыми случайными числами
        for (int i = 0; i < 30; i++) {
            int randomInt = random.nextInt(30);
            usualArray.add(randomInt);
            selfArray.add(randomInt);
        }
        try {
            Comparator<Integer> intComparator = new Comparator<>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            };
            //Отсортируем их
            Collections.sort(usualArray, intComparator);
            Collections.sort(selfArray, intComparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < usualArray.size(); i++) {
            Assertions.assertTrue(usualArray.get(i).equals(selfArray.get(i)),
                    MessageFormat.format("Элементы массивов после сортировки не одинаковы: {1} != {2}", usualArray.get(i), selfArray.get(i)));
        }
    }

    @Test
    @DisplayName("Проверяем поддержку метода Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)")
    void testCopy() {
        try {
            List<Integer> src = IntStream.range(0, 100).boxed().collect(Collectors.toList());
            List<Integer> dest = new DIYArrayList();
            for (int i = 0; i < src.size(); i++) {
                dest.add(i);
            }
            Collections.copy(dest, src);
            for (int i = 0; i < dest.size(); i++) {
                Assertions.assertTrue(src.get(i).equals(dest.get(i)),
                        MessageFormat.format("Элементы массива после копирования не одинаковы: {1} != {2}", src.get(i), dest.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Проверяем поддержку метода Collections.addAll(Collection<? super T> c, T... elements)")
    void testAddAll() {
        try {
            List<Integer> dest = new DIYArrayList();
            Integer one = 1;
            Integer two = 2;
            Integer three = 3;
            Collections.addAll(dest, one, two, three);
            Assertions.assertTrue(one.equals(dest.get(one - 1)),
                    MessageFormat.format("Элемент массива после добавления нескольких не соответствует ожидаемому: {1} != {2}", one, dest.get(one - 1)));
            Assertions.assertTrue(two.equals(dest.get(two - 1)),
                    MessageFormat.format("Элемент массива после добавления нескольких не соответствует ожидаемому: {1} != {2}", one, dest.get(two - 1)));
            Assertions.assertTrue(three.equals(dest.get(three - 1)),
                    MessageFormat.format("Элемент массива после добавления нескольких не соответствует ожидаемому: {1} != {2}", one, dest.get(three - 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
