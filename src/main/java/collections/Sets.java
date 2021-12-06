package collections;

import java.util.Collections;
import java.util.*;

public class Sets {

    public static Set<?> setInterface() {
        // Set interface

        Set<?> setHashSet = hashSet();
        Set<?> setTreeSet = treeSet();
        Set<?> setLinkedHashSet = linkedHashSet();

        return setHashSet;
    }

    private static HashSet<?> hashSet() {
        // HashSet

        // Hashtable (Hashmap instance)
        // No iteration order guarantee, no guarantee of constant iteration order
        // O(1) for basic operations
        // O(n_elements + n_buckets) for iteration
        // Iterator - fast-fail - only to debug
        // Unsynchronized
        HashSet<?> hashSet = new HashSet<>();
        // Synchronized using wrapper
        Set<?> synchronizedHashSet = Collections.synchronizedSet(hashSet);
        return hashSet;
    }

    private static TreeSet<?> treeSet() {
        // TreeSet

        // TreeMap
        // Natural order, or Comparator
        // O(log(n)) for basic operations
        // TreeMap order and Elements' equal should be consistent to comply with Set interface
        // But do not have to because order is defined by compareTo/compare methods

        // Unsynchronized
        TreeSet<?> treeSet = new TreeSet<>();
        // Synchronized
        Set<?> synchronizedTreeSet = Collections.synchronizedSet(treeSet);
        return treeSet;
    }

    private static LinkedHashSet<?> linkedHashSet() {
        // LinkedHashSet

        // Hashtable + LinkedList (doubly-linked list)
        // Predictable order, insertion-order, not affected by re-insertion
        // Constructor taking in set as parameter copies set with same order
        // O(1) for basic operations
        // O(n) for iteration
        // Unsynchronized
        LinkedHashSet<?> linkedHashSet = new LinkedHashSet<>();
        // Synchronized
        Set<?> synchronizedLinkedHashSet = Collections.synchronizedSet(linkedHashSet);
        return linkedHashSet;
    }
}
