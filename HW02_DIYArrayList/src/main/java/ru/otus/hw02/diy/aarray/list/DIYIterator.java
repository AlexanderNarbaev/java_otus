package ru.otus.hw02.diy.aarray.list;

import java.util.Iterator;
import java.util.function.Consumer;

public class DIYIterator implements Iterator {

    DIYIterator(Object[] internalArray) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer action) {

    }
}
