package iterator;

import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.Map;

@AllArgsConstructor
public class MapIterable<K extends Comparable<K>, V extends Comparable<V>> implements Iterable<Map.Entry<K, V>> {
    private final Map<K,V> map;

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new MapIterator<>(map);
    }
}
