package ru.otus.hw02.diy.aarray.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DIYArrayList implements List {

    private static final int DEFAULT_INITIAL_SIZE = 10;
    private Object[] internalArray;
    private int size = 0;

    public DIYArrayList(int size) {
        if (size > 0) {
            this.size = size;
            internalArray = new Object[size];
        } else {
            internalArray = new Object[DEFAULT_INITIAL_SIZE];
        }
    }

    public DIYArrayList() {
        internalArray = new Object[DEFAULT_INITIAL_SIZE];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object containsValue) {
        for (int i = 0; i < size; i++) {
            if (internalArrayEquality(containsValue, internalArray[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return size == 0 ? null : new DIYArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] temporalArray = new Object[size];
        System.arraycopy(internalArray, 0, temporalArray, 0, size);
        return temporalArray;
    }

    @Override
    public boolean add(Object valueToAdd) {
        if (internalArray.length >= size) {
            extendInternalArray();
        }
        internalArray[size] = valueToAdd;
        size++;
        return true;
    }


    @Override
    public boolean remove(Object valueToRemove) {
        Object[] temporalArray = new Object[internalArray.length];
        int insertionIndex = 0;
        if (contains(valueToRemove)) {
            for (int i = 0; i < size; i++) {
                if (!internalArrayEquality(valueToRemove, internalArray[i])) {
                    temporalArray[insertionIndex] = internalArray[i];
                    insertionIndex++;
                }
            }
            internalArray = temporalArray;
            size--;
            return true;
        }
        return false;
    }


    @Override
    public void clear() {
        internalArray = new Object[DEFAULT_INITIAL_SIZE];
        size = 0;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return internalArray[index];
    }

    @Override
    public Object set(int index, Object element) {
        checkIndex(index);
        internalArray[index] = element;
        return internalArray[index];
    }

    @Override
    public void add(int index, Object element) {
        checkIndex(index);
        Object[] temporalArray = new Object[internalArray.length + (internalArray.length / 2)];
        int insertionIndex = 0;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                temporalArray[insertionIndex] = element;
                insertionIndex++;
            }
            temporalArray[insertionIndex] = internalArray[i];
            insertionIndex++;
        }
        internalArray = temporalArray;
        size--;
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object returnValue = null;
        Object[] temporalArray = new Object[internalArray.length];
        int insertionIndex = 0;
        for (int i = 0; i < size; i++) {
            if (i != index) {
                temporalArray[insertionIndex] = internalArray[i];
                insertionIndex++;
            } else {

                returnValue = internalArray[i];
            }
        }
        internalArray = temporalArray;
        size--;
        return returnValue;
    }

    @Override
    public int indexOf(Object valueToIndexSearch) {
        for (int i = 0; i < size; i++) {
            if (internalArrayEquality(valueToIndexSearch, internalArray[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object valueToIndexSearch) {
        int lastIndex = -1;
        for (int i = 0; i < size; i++) {
            if (internalArrayEquality(valueToIndexSearch, internalArray[i])) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    private boolean internalArrayEquality(Object objectToEqual, Object internalArrayObject) {
        return (objectToEqual == null && internalArrayObject == null)
                || (internalArrayObject != null && internalArrayObject.equals(objectToEqual))
                || (internalArrayObject == objectToEqual);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void extendInternalArray() {
        Object[] temporalArray = new Object[internalArray.length + 1];
        System.arraycopy(internalArray, 0, temporalArray, 0, size);
        internalArray = temporalArray;
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator listIterator() {
        return size == 0 ? null : new DIYArrayListIterator();
    }


    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException();
    }

    private class DIYArrayListIterator implements ListIterator {

        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < DIYArrayList.this.size;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                Object o = DIYArrayList.this.get(position);
                position++;
                return o;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean hasPrevious() {
            return position <= 0;
        }

        @Override
        public Object previous() {
            if (hasPrevious()) {
                Object o = DIYArrayList.this.get(previousIndex());
                return o;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int nextIndex() {
            return position + 1;
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void remove() {
            DIYArrayList.this.remove(position);
            position--;
        }

        @Override
        public void set(Object o) {
            DIYArrayList.this.set(position - 1, o);
        }

        @Override
        public void add(Object o) {
            DIYArrayList.this.add(position, o);
            position++;
        }
    }
}