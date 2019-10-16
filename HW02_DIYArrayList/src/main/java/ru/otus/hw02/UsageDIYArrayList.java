package ru.otus.hw02;

import ru.otus.hw02.diy.array.list.DIYArrayList;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UsageDIYArrayList {
    /**
     * Проверяем собственную имплементацию интерфейса List
     *
     * @param args
     */
    public static void main(String[] args) {
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
            Comparator<Integer> intComparator = Comparator.comparingInt(o -> o);
            //Отсортируем их
            Collections.sort(usualArray, intComparator);
            Collections.sort(selfArray, intComparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Подсчёт неверных элементов
        int unEqual = 0;
        //Пройдёмся по обеим коллекциям, сравним элементы и посчитаем сколько неверных
        for (int i = 0; i < usualArray.size(); i++) {
            System.out.println("USUAL array[" + i + "] is " + usualArray.get(i) + "\t SELF array[" + i + "] is " + selfArray.get(i) + " and its are equal?\t" + usualArray.get(i).equals(selfArray.get(i)));
            if (!usualArray.get(i).equals(selfArray.get(i))) {
                unEqual++;
            }
        }
        System.out.println("Total unEqual SIZE:\t" + unEqual);
        //Проверим добавление в коллекцию массива элементов
        try {
            Collections.addAll(selfArray, random.nextInt(1), random.nextInt(2));
            for (int i = 0; i < selfArray.size(); i++) {
                System.out.println("selfArray array[" + i + "] is " + selfArray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Проверим копирование одной коллекции в другую
        try {
            DIYArrayList destArray = new DIYArrayList(selfArray.size());
            Collections.copy(destArray, selfArray);
            for (int i = 0; i < destArray.size(); i++) {
                System.out.println("destArray array[" + i + "] is " + destArray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Увеличим ставки - имеем на входе массив из 100 элементов
        try {
            List<Integer> src = IntStream.range(0, 100).boxed().collect(Collectors.toList());
            List<Integer> dest = new DIYArrayList();
            for (int i = 0; i < src.size(); i++) {
                dest.add(i);
            }
            Collections.copy(dest, src);
            for (int i = 0; i < dest.size(); i++) {
                System.out.println("dest array[" + i + "] is " + dest.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
