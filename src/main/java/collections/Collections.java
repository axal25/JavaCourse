package collections;

import utils.StaticUtils;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Map;

public class Collections {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticUtils.printMethodSignature("main");

        collections();
    }

    private static void collections() {
        // Collections (Collection framework - not interface)

        Collection<?> col = collectionInterface();
        Map<?, ?> map = Maps.mapInterface();
        // Map interface does not extend Collection interface
        try {
            // will throw ClassCastException
            Collection<?> colIsNotMap = (Collection<?>) map;
            // will not be thrown
            throw new RuntimeException("Map is Collection?!");
        } catch (ClassCastException e) {
        }
    }

    private static Collection<?> collectionInterface() {
        // Collection interface

        // Set interface is extending Collection interface
        Collection<?> colSet = Sets.setInterface();

        // List interface is extending Collection interface
        Collection<?> colList = Lists.listInterface();

        // Queue interface is extending Collection interface
        Collection<?> colQueue = Queues.queueInterface();

        // Deque interface is extending Collection interface
        Collection<?> colDeque = Deques.dequeInterface();

        return colList;
    }


}
