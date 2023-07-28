package iterator;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapIterator<K extends Comparable<K>, V extends Comparable<V>> implements Iterator<Map.Entry<K, V>> {
    private final MapIteratorEntry<K, V> head;
    private MapIteratorEntry<K, V> next;

    public MapIterator(Map<K, V> map) {
        List<Map.Entry<K, V>> mapEntryList = map.entrySet().stream()
                .sorted(Comparator.nullsFirst(
                        Comparator.<Map.Entry<K, V>, K>comparing(Map.Entry::getKey,
                                        Comparator.nullsFirst(Comparator.naturalOrder()))
                                .thenComparing(Comparator.comparing(Map.Entry::getValue,
                                        Comparator.nullsFirst(Comparator.naturalOrder())))))
                .collect(Collectors.toList());

        List<MapIteratorEntry<K, V>> mapIteratorEntryList = mapEntryList.stream()
                .map(mapEntry -> MapIteratorEntry.<K, V>builder()
                        .mapEntry(mapEntry)
                        .build())
                .collect(Collectors.toList());

        IntStream.range(0, mapIteratorEntryList.size() - 1).forEach(i ->
                mapIteratorEntryList.get(i)
                        .setNext(mapIteratorEntryList.get(i + 1)));

        head = mapIteratorEntryList.isEmpty() ? null : mapIteratorEntryList.get(0);
        reset();
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public MapIteratorEntry<K, V> next() {
        MapIteratorEntry<K, V> currentlyNext = next;
        next = next.next();
        return currentlyNext;
    }

    public void reset() {
        next = head;
    }
}
