package collections;

import java.util.*;

class Maps {

    static Map<?, ?> mapInterface() {
        // Map interface

        // Map interface is not extending Collection interface
        // Collection<?, ?> colMap = new HashMap<>();

        Map<?, ?> mapHashTable = hashtable();
        Map<?, ?> mapHashMap = hashMap();
        Map<?, ?> mapTreeMap = treeMap();
        Map<?, ?> mapLinkedHashMap = linkedHashMap();

        return sortedMapInterface();
    }

    private static SortedMap<?, ?> sortedMapInterface() {
        // SortedMap interface

        // SortedMap interface is extending Map interface

        // HashMap class does NOT implement SortedMap interface
        try {
            // will throw ClassCastException
            SortedMap<?, ?> sortedMapIsNotHashMap = (SortedMap<?, ?>) hashMap();
            // will not be thrown
            throw new RuntimeException("Hashmap is SortedMap?!");
        } catch (ClassCastException e) {
        }

        // TreeMap class implements SortedMap interface
        SortedMap<?, ?> sortedMapTreeMap = treeMap();

        // LinkedHashMap class does not implement SortedMap interface
        try {
            // will throw ClassCastException
            SortedMap<?, ?> sortedMapIsNotLinkedHashMap = (SortedMap<?, ?>) linkedHashMap();
            // will not be thrown
            throw new RuntimeException("LinkedHashMap is SortedMap?!");
        } catch (ClassCastException e) {
        }

        return sortedMapTreeMap;
    }

    private static Hashtable<?, ?> hashtable() {
        // Hashtable class

        // Non-null keys to non-null values
        // key Object has to implement hashCode, equals

        return new Hashtable<>();
    }

    private static HashMap<?, ?> hashMap() {
        // HashMap class

        // Hash table - but unsynchronized, permits null value and keys
        // Order: not guaranteed, not constant
        // Get, put: constant
        // Iteration: proportional to number of buckets in hash table (capacity), key-value mappings
        // Initial capacity - amount of buckets in hash table at the moment of map creation
        // Load factor - how full hash table can get before capacity is increased
        // Rehashing - rebuilding internal structures so has table has ~2* number of buckets
        //      when number of entries > load factor * current capacity

        // LinkedHashMap class extends HashMap class
        HashMap<?, ?> hashMapLinkedHashMap = linkedHashMap();

        return new HashMap<>();
    }

    private static TreeMap<?, ?> treeMap() {
        // TreeMap class

        // Red-black tree - binary search tree, self-balancing
        // Sorted by keys, natural order or comparator
        return new TreeMap<>();
    }

    private static LinkedHashMap<?, ?> linkedHashMap() {
        // LinkedHashMap class

        // Hash table + linked list (doubly-linked list)
        // Predictable iteration order (defined by linked list), normally order of insertion, key re-insertion do not affect order
        // Constructor taking on map as parameter keeps order of original map
        return new LinkedHashMap<>();
    }
}
