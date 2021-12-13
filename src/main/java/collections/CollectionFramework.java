package collections;

import utils.StaticUtils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CollectionFramework {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticUtils.printMethodSignature("main");
        collectionFramework().print();
    }

    private static Information collectionFramework() {
        Information collectionFrameworkInformation = new Information("Collections framework");
        collectionFrameworkInformation.appendln("Collection framework consists of 2 interfaces: Collection and Map.");

        Collection<?> col;

        collectionFrameworkInformation.appendln("Collection interface is member of Collection framework");
        collectionFrameworkInformation.addChild(Collections.collectionInterface());

        collectionFrameworkInformation.appendln("Map interface is member of Collection framework");
        collectionFrameworkInformation.addChild(Maps.mapInterface());

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

        return collectionFrameworkInformation;
    }

}
