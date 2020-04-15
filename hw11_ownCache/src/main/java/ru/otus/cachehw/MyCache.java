package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    WeakHashMap<K, V> internalStore = new WeakHashMap<>();
    List<WeakReference<HwListener>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listeners
                .stream()
                .filter(hwListenerWeakReference -> hwListenerWeakReference.get() != null)
                .forEach(hwListenerWeakReference -> hwListenerWeakReference.get().notify(key, value, "PUT"));
        internalStore.put(key, value);
    }

    @Override
    public void remove(K key) {
        listeners
                .stream()
                .filter(hwListenerWeakReference -> hwListenerWeakReference.get() != null)
                .forEach(hwListenerWeakReference -> hwListenerWeakReference.get().notify(key, null, "REMOVE"));
        internalStore.remove(key);
    }

    @Override
    public V get(K key) {
        return internalStore.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        List<WeakReference<HwListener>> listenersToDelete = listeners.stream().filter(t -> t.get() != null && t.get().equals(listener)).collect(Collectors.toList());
        listeners.removeAll(listenersToDelete);
    }
}
