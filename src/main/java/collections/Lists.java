package collections;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lists {

    public static Information listInterface() {
        Information listInterfaceInformation = new Information(List.class);

        listInterfaceInformation.appendln("Ordered collection");
        listInterfaceInformation.appendln("May contain duplicates");

        List<?> list;

        listInterfaceInformation.appendln("ArrayList class implements List interface");
        listInterfaceInformation.addChild(arrayList());
        list = new ArrayList<>();

        listInterfaceInformation.appendln("LinkedList class implements List interface");
        listInterfaceInformation.addChild(linkedList());
        list = new LinkedList<>();

        listInterfaceInformation.appendln("CopyOnWriteArrayList class implements List interface");
        listInterfaceInformation.addChild(copyOnWriteArrayList());
        list = new CopyOnWriteArrayList<>();

        listInterfaceInformation.appendln("Vector class implements List interface");
        listInterfaceInformation.addChild(vector());
        list = new Vector<>();

        listInterfaceInformation.appendln("Stack class implements List interface");
        // listInterfaceInformation.addChild(stack()); // Already added under vector
        list = new Stack<>();

        return listInterfaceInformation;
    }

    private static Information arrayList() {
        Information arrayListInformation = new Information(ArrayList.class);

        arrayListInformation.appendln("Usually better performance (than LinkedList)");

        arrayListInformation.appendln("Resizeable array");
        arrayListInformation.appendln("Allows null elements");
        arrayListInformation.appendln("Equivalent to Vector but unsynchronized");
        arrayListInformation.appendln("O(1) (constant time) for size, isEmpty, get, set, iterator, listIterator");
        arrayListInformation.appendln("O(1)+ (amortized constant time) for adding (1) element");
        arrayListInformation.appendln("O(k)+ (amortized constant time) for adding K elements (to ArrayList having N elements - is NOT proportional to amount of elements already in array)");
        arrayListInformation.appendln("O(n) (linear time) for other operations - to ArrayList having N elements");
        arrayListInformation.appendln("Constant time [O(1)+/O(k)+] is better compared to LinkedList");
        arrayListInformation.appendln("Capacity - size of array ready to store elements. At least as big as number of elements already stored. Grows automatically [O(1)+].");
        arrayListInformation.appendln("ensureCapacity - adding large amount of capacity. Before adding large amount of elements. Reduces incremental allocation time.");

        arrayListInformation.appendln(CommonInformation.UNSYNCHRONIZED);
        arrayListInformation.appendln(CommonInformation.FAIL_FAST);

        arrayListInformation.appendln(CommonInformation.Lists.STRUCTURE_MODIFICATION);

        ArrayList<?> arrayList = new ArrayList<>();

        arrayListInformation.appendln(CommonInformation.getSynchronization(List.class, ArrayList.class));
        List<?> synchronizedLinkedList = java.util.Collections.synchronizedList(arrayList);

        return arrayListInformation;
    }

    private static Information linkedList() {
        Information linkedListInformation = new Information(LinkedList.class);

        linkedListInformation.appendln("Better performance under certain circumstances (than ArrayList)");

        linkedListInformation.appendln("Doubly-linked list");
        linkedListInformation.appendln("Implements List & Deque interfaces");
        LinkedList<?> linkedList = new LinkedList<>();
        List<?> list = linkedList;
        Deque<?> deque = linkedList;

        linkedListInformation.appendln("Allows null elements");
        linkedListInformation.appendln("Index operations traverse from beginning or end (whichever is closer) - O(n) ?");

        linkedListInformation.appendln(CommonInformation.UNSYNCHRONIZED);
        linkedListInformation.appendln(CommonInformation.FAIL_FAST);

        linkedListInformation.appendln(CommonInformation.Lists.STRUCTURE_MODIFICATION);

        linkedListInformation.appendln(CommonInformation.getSynchronization(List.class, LinkedList.class));
        List<?> synchronizedLinkedList = java.util.Collections.synchronizedList(linkedList);

        return linkedListInformation;
    }

    private static Information copyOnWriteArrayList() {
        Information copyOnWriteArrayListInformation = new Information(CopyOnWriteArrayList.class);

        copyOnWriteArrayListInformation.appendln("ArrayList + thread-safe (no interference or ConcurrentModificationException without synchro)");
        copyOnWriteArrayListInformation.appendln("creates snapshot copy of source array");
        copyOnWriteArrayListInformation.appendln("iterator will not reflect mutative operations, array never changes during iterator's lifetime");
        copyOnWriteArrayListInformation.appendln("mutative operations are not supported on iterators themselves - will throw UnsupportedOperationException");

        CopyOnWriteArrayList<?> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        return copyOnWriteArrayListInformation;
    }

    private static Information vector() {
        Information vectorInformation = new Information(Vector.class);

        vectorInformation.appendln("Growable array");
        vectorInformation.appendln("Capacity - vector size. Maximum amount of elements that currently can be stored. Usually larger than current amount of elements stored. Is increased before inserting large amount of elements to reduce incremental reallocation time.");
        vectorInformation.appendln("CapacityIncrement - chunk size by which capacity is increased.");
        vectorInformation.appendln("Equivalent to ArrayList but synchronized - thread-safe - if thread-safe operations are not needed it is better to use ArrayList");
        vectorInformation.appendln(CommonInformation.FAIL_FAST);
        vectorInformation.appendln(CommonInformation.Lists.STRUCTURE_MODIFICATION);

        Vector<?> vector = new Vector<>();

        vectorInformation.appendln("Stack extends Vector class");
        vector = new Stack<>();

        vectorInformation.addChild(stack());

        return vectorInformation;
    }

    private static Information stack() {
        Information stackInformation = new Information(Stack.class);

        stackInformation.appendln("Stack class implements Collection interface");
        stackInformation.appendln("FILO / LIFO");
        Stack<?> stack = new Stack<>();

        stackInformation.appendln("Stack extends Vector class");
        Vector<?> vector = stack;

        stackInformation.appendln("Deque (ArrayDequeue) should be used in preference to Stack. Deque and Stack have no close class hierarchy relation (other than Collection, AbstractCollection).");

        return stackInformation;
    }
}
