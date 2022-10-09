package collections.theory;

import utils.ClassMethodUtils;

import java.util.Collections;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Sets {

    static Information setInterface() {
        Information setInterfaceInformation = new Information(Set.class);

        Set<?> set;

        setInterfaceInformation.appendln(String.format("%s class implements %s interface",
                ClassMethodUtils.getClassSimpleName(TreeSet.class),
                ClassMethodUtils.getClassSimpleName(Set.class)));
        set = new TreeSet<>();
        setInterfaceInformation.addChild(sortedSetInterface());

        setInterfaceInformation.appendln(String.format("%s class implements %s interface",
                ClassMethodUtils.getClassSimpleName(HashSet.class),
                ClassMethodUtils.getClassSimpleName(Set.class)));
        setInterfaceInformation.appendln(String.format("%s class implements %s interface",
                ClassMethodUtils.getClassSimpleName(LinkedHashSet.class),
                ClassMethodUtils.getClassSimpleName(Set.class)));
        set = new HashSet<>();
        set = new LinkedHashSet<>();
        setInterfaceInformation.addChild(hashSet());

        setInterfaceInformation.appendln(String.format("%s class implements %s interface",
                ClassMethodUtils.getClassSimpleName(CopyOnWriteArraySet.class),
                ClassMethodUtils.getClassSimpleName(Set.class)));
        set = new CopyOnWriteArraySet<>();
        setInterfaceInformation.addChild(copyOnWriteArraySet());

        setInterfaceInformation.appendln(String.format("%s class implements %s interface",
                "SynchronizedSet",
                ClassMethodUtils.getClassSimpleName(Set.class)));
        set = Collections.synchronizedSet(set);
        setInterfaceInformation.addChild(synchronizedSet());

        return setInterfaceInformation;
    }

    private static Information sortedSetInterface() {
        Information sortedSetInterfaceInformation = new Information(SortedSet.class);

        sortedSetInterfaceInformation.addChild(navigableSetInterface());

        return sortedSetInterfaceInformation;
    }

    private static Information navigableSetInterface() {
        Information navigableSetInterfaceInformation = new Information(NavigableSet.class);

        navigableSetInterfaceInformation.addChild(treeSet());

        return navigableSetInterfaceInformation;
    }

    private static Information treeSet() {
        Information treeSetInterfaceInformation = new Information(TreeSet.class);

        treeSetInterfaceInformation.appendln("Natural order, or Comparator.");
        treeSetInterfaceInformation.appendln("O(log(n)) for basic operations.");
        treeSetInterfaceInformation.appendln("TreeMap order and Elements' equal should be consistent to comply with Set interface.");
        treeSetInterfaceInformation.appendln("But do not have to because order is defined by compareTo/compare methods.");
        treeSetInterfaceInformation.appendln("Unsynchronized.");

        Set<?> set = new TreeSet<>();
        SortedSet<?> sortedSet = new TreeSet<>();
        NavigableSet<?> navigableSet = new TreeSet<>();
        AbstractSet<?> abstractSet = new TreeSet<>();
        TreeSet<?> treeSet = new TreeSet<>();

        return treeSetInterfaceInformation;
    }

    private static Information hashSet() {
        Information hashSetInterfaceInformation = new Information(HashSet.class);

        hashSetInterfaceInformation.appendln("Hashtable (Hashmap instance).");
        hashSetInterfaceInformation.appendln("No iteration order guarantee, no guarantee of constant iteration order.");
        hashSetInterfaceInformation.appendln("O(1) for basic operations.");
        hashSetInterfaceInformation.appendln("O(n_elements + m_buckets) for iteration.");
        hashSetInterfaceInformation.appendln("Iterator - fast-fail - only to debug.");
        hashSetInterfaceInformation.appendln("Unsynchronized.");

        Set<?> set = new HashSet<>();
        HashSet<?> hashSet = new HashSet<>();

        hashSetInterfaceInformation.addChild(linkedHashSet());

        return hashSetInterfaceInformation;
    }

    private static Information linkedHashSet() {
        Information linkedHashSetClassInformation = new Information(LinkedHashSet.class);

        linkedHashSetClassInformation.appendln("Hashtable + LinkedList (doubly-linked list).");
        linkedHashSetClassInformation.appendln("Predictable order, insertion-order, not affected by re-insertion.");
        linkedHashSetClassInformation.appendln("Constructor taking in set as parameter copies set with same order.");
        linkedHashSetClassInformation.appendln("O(1) for basic operations.");
        linkedHashSetClassInformation.appendln("O(n) for iteration.");
        linkedHashSetClassInformation.appendln("Unsynchronized.");

        Set<?> set = new LinkedHashSet<>();
        HashSet<?> hashSet = new LinkedHashSet<>();
        LinkedHashSet<?> linkedHashSet = new LinkedHashSet<>();

        return linkedHashSetClassInformation;
    }

    private static Information copyOnWriteArraySet() {
        Information copyOnWriteArraySetClassInformation = new Information(CopyOnWriteArraySet.class);

        copyOnWriteArraySetClassInformation.appendln("missing info?");

        Set<?> set = new CopyOnWriteArraySet<>();
        AbstractSet<?> abstractSet = new CopyOnWriteArraySet<>();
        CopyOnWriteArraySet<?> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        return copyOnWriteArraySetClassInformation;
    }

    private static Information synchronizedSet() {
        Information synchronizedClassInformation = new Information("SynchronizedSet(Set)");

        synchronizedClassInformation.appendln("All sets are Unsynchronized.");
        synchronizedClassInformation.appendln("Synchronization of Sets is achieved by wrapping any Set into SynchronizedSet.");

        Set<?> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        synchronizedSet = Collections.synchronizedSet(new TreeSet<>());
        synchronizedSet = Collections.synchronizedSet(new LinkedHashSet<>());

        return synchronizedClassInformation;
    }
}
