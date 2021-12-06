package collections;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lists {

    public static List<?> listInterface() {
        // List interface

        // Ordered collection
        // May contain duplicates

        List<?> listArrayList = arrayList();
        List<?> listLinkedList = linkedList();
        List<?> listCopyOnWriteArrayList = copyOnWriteArrayList();
        List<?> listVector = vector();
        List<?> listStack = stack();

        return listArrayList;
    }

    private static ArrayList<?> arrayList() {
        // ArrayList

        // Usually better performance (than LinkedList)
        // Resizeable array
        // Equivalent to Vector but unsynchronized
        // O(n)/O(1)+ (amortized constant time) for adding
        // O(1) for other operations
        return new ArrayList<>();
    }

    static LinkedList<?> linkedList() {
        // LinkedList

        // Better performance under certain circumstances (than ArrayList)
        // Doubly-linked list
        // Implements Deque Interface
        // Index operations traverse from beginning or end - O(n) ?
        // Unsynchronized
        // CANNOT DEPEND ON:
        //      Can depend only when detecting bugs
        //      Iterator's methods are fail-fast - iterator fails quickly and cleanly
        LinkedList<?> linkedList = new LinkedList<>();
        Deque<?> deque = linkedList;
        // Synchronized by wrapping
        List<?> synchronizedLinkedList = java.util.Collections.synchronizedList(linkedList);
        return linkedList;
    }

    private static CopyOnWriteArrayList<?> copyOnWriteArrayList() {
        // CopyOnWriteArrayList

        // ArrayList + thread-safe (no ConcurrentModificationException without synchro)
        // creates snapshot copy of source array
        // iterator will not reflect mutative operations, array never changes during iterator's lifetime
        // mutative operations are not supported on iterators themselves - will throw UnsupportedOperationException
        return new CopyOnWriteArrayList<>();
    }

    private static Vector<?> vector() {
        // Vector

        // Growable array
        // Synchronized

        // Equivalent to ArrayList but synchronized?
        Vector<?> vector = stack();
        return new Vector<>();
    }

    private static Stack<?> stack() {
        // Stack

        // Stack class implements Collection interface
        // FILO / LIFO
        Stack<?> stack = new Stack<>();
        Vector<?> vector = stack;
        return stack;
    }
}
