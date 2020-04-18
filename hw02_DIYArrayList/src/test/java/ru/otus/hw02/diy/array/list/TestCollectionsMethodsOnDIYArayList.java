package ru.otus.hw02.diy.array.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("Проверяем собственную имплементацию интерфейса List")
class TestCollectionsMethodsOnDIYArayList {

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
        Comparator<Integer> intComparator = Comparator.comparingInt(o -> o);
        //Отсортируем их
        Collections.sort(usualArray, intComparator);
        Collections.sort(selfArray, intComparator);
        for (int i = 0; i < usualArray.size(); i++) {
            Assertions.assertEquals(usualArray.get(i), selfArray.get(i));
        }
    }

    @Test
    @DisplayName("Проверяем поддержку метода Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)")
    void testCopy() {
        List<Integer> src = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        DIYArrayList dest = new DIYArrayList();
        for (int i = 0; i < src.size(); i++) {
            dest.add(i);
        }
        Collections.copy(dest, src);
        for (int i = 0; i < dest.size(); i++) {
            Assertions.assertEquals(src.get(i), dest.get(i));
        }
    }

    @Test
    @DisplayName("Проверяем поддержку метода Collections.addAll(Collection<? super T> c, T... elements)")
    void testAddAll() {
        DIYArrayList dest = new DIYArrayList();
        Collections.addAll(dest, 1, 2, 3);
        Assertions.assertEquals(1, dest.get(0));
        Assertions.assertEquals(2, dest.get(1));
        Assertions.assertEquals(3, dest.get(2));
    }
}
