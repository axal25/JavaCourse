package collections.theory;

import java.util.*;

class Maps {

    static Information mapInterface() {
        Information mapInterfaceInformation = new Information(Map.class);

        Map<?, ?> map;
        NavigableMap<?, ?> navigableMap;
        SortedMap<?, ?> sortedMap = new TreeMap<>();
        AbstractMap<?, ?> abstractMap;
        Dictionary<?, ?> dictionary;

        mapInterfaceInformation.appendln("SortedMap interface extends Map interface");
        mapInterfaceInformation.addChild(sortedMapInterface());

        mapInterfaceInformation.appendln("Hashtable class implements Map interface");
        mapInterfaceInformation.addChild(hashtable());
        map = new Hashtable<>();

        mapInterfaceInformation.appendln("HashMap class implements Map interface");
        mapInterfaceInformation.addChild(hashMap());
        map = new HashMap<>();

        mapInterfaceInformation.appendln("LinkedHashMap class implements Map interface");
        // mapInterfaceInformation.addChild(linkedHashMap()); // Already added under hashMap
        map = new LinkedHashMap<>();

        mapInterfaceInformation.appendln("TreeMap class implements Map interface");
        // mapInterfaceInformation.addChild(treeMap()); // Already added under sortedMapInterface
        map = new TreeMap<>();

        return mapInterfaceInformation;
    }

    private static Information sortedMapInterface() {
        Information sortedMapInformation = new Information(SortedMap.class);

        sortedMapInformation.appendln(String.format(
                "%s interface extends %s interface", SortedMap.class.getSimpleName(), Map.class.getSimpleName()));

        sortedMapInformation.appendln(String.format(
                "%s class implements %s interface", TreeMap.class.getSimpleName(), SortedMap.class.getSimpleName()));
        SortedMap<?, ?> sortedMap = new TreeMap<>();

        sortedMapInformation.addChild(navigableMapInterface());

        return sortedMapInformation;
    }

    private static Information navigableMapInterface() {
        Information navigableMapInformation = new Information(NavigableMap.class);

        navigableMapInformation.appendln(String.format(
                "%s interface extends %s interface", NavigableMap.class.getSimpleName(), SortedMap.class.getSimpleName()));

        navigableMapInformation.appendln(String.format(
                "%s class implements %s interface", TreeMap.class.getSimpleName(), NavigableMap.class.getSimpleName()));

        NavigableMap<?, ?> navigableMap = new TreeMap<>();
        navigableMapInformation.addChild(treeMap());

        return navigableMapInformation;
    }

    private static Information treeMap() {
        Information treeMapInformation = new Information(TreeMap.class);

        treeMapInformation.appendln("Red-black tree - binary search tree, self-balancing");
        treeMapInformation.appendln("Sorted by keys, natural order or map's constructor's comparator");
        treeMapInformation.appendln("O(log(n)) for containsKey, get, put, remove");
        treeMapInformation.appendln("Natural order/comparator should be consistent with equals - correct Map interface implementation");
        treeMapInformation.appendln("TreeMap compares keys using compareTo/compare but Map uses equals");
        treeMapInformation.appendln("SortedMap's behavior is well defined even when ordering is inconsistent with equals. Just fails to obey Map interface's contract.");
        treeMapInformation.appendln("\tif(");
        treeMapInformation.appendln("\t\t(comparator.compare(element1, element2) == 0)");
        treeMapInformation.appendln("\t\t\t==");
        treeMapInformation.appendln("\t\t\t(element1.equals(element2))");
        treeMapInformation.appendln("\t) { System.out.println(\"Ordering is consistent with equals\"); }");

        treeMapInformation.appendln(CommonInformation.UNSYNCHRONIZED);
        treeMapInformation.appendln(CommonInformation.FAIL_FAST);

        treeMapInformation.appendln(CommonInformation.Maps.STRUCTURE_MODIFICATION);

        TreeMap<?, ?> treeMap = new TreeMap<>();
        treeMap.firstEntry();

        treeMapInformation.appendln(CommonInformation.getSynchronization(SortedMap.class, TreeMap.class));
        NavigableMap<?, ?> synchronizedTreeMap = java.util.Collections.synchronizedNavigableMap(treeMap);

        return treeMapInformation;
    }

    private static Information hashtable() {
        Information hashtableInformation = new Information(Hashtable.class);

        Hashtable<?, ?> hashtable;

        hashtableInformation.appendln("Maps keys to values");
        hashtableInformation.appendln("Non-null keys and values");
        hashtableInformation.appendln("Keys must implement hashCode, equals");
        hashtableInformation.appendln(CommonInformation.Maps.Hashes.PERFORMANCE);
        hashtableInformation.appendln(CommonInformation.Maps.Hashes.CAPACITY);
        hashtableInformation.appendln(CommonInformation.Maps.Hashes.LOAD_FACTOR);
        hashtableInformation.appendln(CommonInformation.Maps.Hashes.REHASHING);

        hashtableInformation.appendln("Synchronized - thread-safe - if thread-safe operations are not needed it is better to use HashMap.");
        hashtableInformation.appendln("\tIf thread-safe, highly-concurrent implementation is needed - use ConcurrentHashMap");
        hashtableInformation.appendln(CommonInformation.FAIL_FAST);
        hashtableInformation.appendln(CommonInformation.Maps.STRUCTURE_MODIFICATION);
        hashtable = new Hashtable<>();

        hashtableInformation.appendln("Hashtable class implements Map interface");
        Map<?, ?> map = new Hashtable<>();

        hashtableInformation.appendln("Hashtable class extends Dictionary abstract class");
        Dictionary<?, ?> dictionary = new Hashtable<>();

        return hashtableInformation;
    }

    private static Information hashMap() {
        Information hashMapInformation = new Information(HashMap.class);

        HashMap<?, ?> hashMap;

        hashMapInformation.appendln("Hash table based");
        hashMapInformation.appendln("Permits null keys and values");
        hashMapInformation.appendln("Order: not guaranteed, not constant");
        hashMapInformation.appendln("O(1) (constant) - for get, put (when no hash collisions occurs)");
        hashMapInformation.appendln("O(n) (linear proportionality to capacity) - for iterations");
        hashMapInformation.appendln(CommonInformation.Maps.Hashes.PERFORMANCE);
        hashMapInformation.appendln(CommonInformation.Maps.Hashes.CAPACITY);
        hashMapInformation.appendln(CommonInformation.Maps.Hashes.LOAD_FACTOR);
        hashMapInformation.appendln(CommonInformation.Maps.Hashes.REHASHING);
        hashMapInformation.appendln("May use comparison for Comparable keys in same bucket.");

        hashMapInformation.appendln("Unsynchronized");
        hashMap = new HashMap<>();

        hashMapInformation.appendln("LinkedHashMap class extends HashMap class");
        hashMap = new LinkedHashMap<>();

        hashMapInformation.addChild(linkedHashMap());

        return hashMapInformation;
    }

    private static Information linkedHashMap() {
        Information linkedHashMapInformation = new Information(LinkedHashMap.class);

        linkedHashMapInformation.appendln("Hash table + linked list (doubly-linked list)");
        linkedHashMapInformation.appendln("Predictable iteration order (defined by linked list), normally order of insertion, key re-insertion do not affect order");
        linkedHashMapInformation.appendln("Constructor taking on map as parameter keeps order of original map");
        LinkedHashMap<?, ?> linkedHashMap = new LinkedHashMap<>();

        return linkedHashMapInformation;
    }
}
