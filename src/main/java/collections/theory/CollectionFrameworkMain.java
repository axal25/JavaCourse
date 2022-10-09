package collections.theory;

import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CollectionFrameworkMain {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");
        getCollectionFrameworkInformation().print();
    }

    private static Information getCollectionFrameworkInformation() {
        Information collectionFrameworkInformation = new Information("Collections framework");
        collectionFrameworkInformation.appendln("Collection framework consists of 2 interfaces: Collection and Map.");

        Collection<?> col;

        col = (Collection<?>) new ArrayList<>();
        try {
            // will throw ClassCastException
            col = (Collection<?>) new HashMap<>();
            // will not be thrown
            throw new RuntimeException("Map EXTENDS Collection interface?!");
        } catch (ClassCastException e) {
            collectionFrameworkInformation.appendln("Map interface does NOT extend Collection interface");
        } catch (RuntimeException e) {
            collectionFrameworkInformation.appendln(e.getMessage());
        }

        collectionFrameworkInformation.appendln("Collection interface is member of Collection framework");
        collectionFrameworkInformation.addChild(Collections.collectionInterface());

        collectionFrameworkInformation.appendln("Map interface is member of Collection framework");
        collectionFrameworkInformation.addChild(Maps.mapInterface());

        return collectionFrameworkInformation;
    }
}
