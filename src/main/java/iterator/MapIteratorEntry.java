package iterator;

import lombok.*;

import java.util.Map;

@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
public class MapIteratorEntry<K, V> implements Map.Entry<K, V> {
    private Map.Entry<K, V> mapEntry;
    private MapIteratorEntry<K, V> next;

    public MapIteratorEntry<K, V> next() {
        return next;
    }

    @Override
    public K getKey() {
        return mapEntry.getKey();
    }

    @Override
    public V getValue() {
        return mapEntry.getValue();
    }

    @Override
    public V setValue(V v) {
        return mapEntry.setValue(v);
    }
}
