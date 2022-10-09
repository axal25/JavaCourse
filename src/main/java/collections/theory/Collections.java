package collections.theory;

import java.util.*;

public class Collections {

    static Information collectionInterface() {
        Information collectionInterfaceInformation = new Information(Collection.class);

        Collection<?> collection;

        collectionInterfaceInformation.appendln("Set interface is extending Collection interface");
        Set<?> set = new HashSet<>();
        collection = set;
        collectionInterfaceInformation.addChild(Sets.setInterface());

        collectionInterfaceInformation.appendln("List interface is extending Collection interface");
        List<?> list = new ArrayList<>();
        collection = list;
        collectionInterfaceInformation.addChild(Lists.listInterface());

        collectionInterfaceInformation.appendln("Queue interface is extending Collection interface");
        Queue<?> queue = new PriorityQueue<>();
        collection = queue;
        collectionInterfaceInformation.addChild(Queues.queueInterface());

        collectionInterfaceInformation.appendln("Deque interface is extending Collection interface");
        Deque<?> deque = new ArrayDeque<>();
        collection = deque;
        collectionInterfaceInformation.addChild(Deques.dequeInterface());

        return collectionInterfaceInformation;
    }
}
