package collections.theory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Collections {

    static Information collectionInterface() {
        Information collectionInterfaceInformation = new Information(Collection.class);

        Collection<?> collection;

        collectionInterfaceInformation.appendln("Set interface is extending Collection interface");
        collection = Sets.setInterface();

        collectionInterfaceInformation.appendln("List interface is extending Collection interface");
        List<?> list = new ArrayList<>();
        collection = list;
        collectionInterfaceInformation.addChild(Lists.listInterface());

        collectionInterfaceInformation.appendln("Queue interface is extending Collection interface");
        collection = Queues.queueInterface();

        collectionInterfaceInformation.appendln("Deque interface is extending Collection interface");
        collection = Deques.dequeInterface();

        return collectionInterfaceInformation;
    }

}
