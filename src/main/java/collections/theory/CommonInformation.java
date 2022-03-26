package collections.theory;

import utils.ClassMethodUtils;

import java.util.List;
import java.util.Locale;

class CommonInformation {

    static final List<String> UNSYNCHRONIZED = List.of(
            "Unsynchronized - need to be synchronized for concurrent operations."
    );

    static final List<String> FAIL_FAST = List.of(
            "fail-fast iterator - when concurrent operations causes structure modification after iteration creation a ConcurrentModificationException will be thrown. Does not result in non-deterministic behaviour. This trait should not be trusted. It is not guaranteed. Use only to detect bugs."
    );

    static final class Maps {

        static final List<String> STRUCTURE_MODIFICATION = List.of(
                "Structure modification: remove. Not set key's value."
        );

        static final class Hashes {

            static final List<String> CAPACITY = List.of(
                    "Capacity - number of buckets (initial - at the creation)."
            );

            static final List<String> PERFORMANCE = List.of(
                    "Performance is affected by: initial capacity, load factor."
            );

            static final List<String> LOAD_FACTOR = List.of(
                    "Load factor - how full hashtable can get before capacity is increased, then rehash occurs. Maximum allowed (entries_amount / capacity). Default: 0.75. Higher => smaller space overhead, more look-up time."
            );

            static final List<String> REHASHING = List.of(
                    "Rehashing - rebuilding internal structures so table has has ~2* number of buckets when load factor is exceeded <=> entries_amount > load_factor * capacity."
            );
        }

    }

    public static final class Lists {

        static final List<String> STRUCTURE_MODIFICATION = List.of(
                "Structure modification: add, delete, resize. Not set element's value."
        );
    }

    private static final List<List<String>> SYNCHRONIZATION = List.of(
            List.of("Synchronization - can be synchronized by wrapping using an object already synchronized."),
            List.of("\t", " synchronized", " = Collections.synchronized", "(", ");")
    );

    public static List<String> getSynchronization(Class<?> interfaze, Class<?> clazz) {
        String interfaceSimpleName = ClassMethodUtils.getClassSimpleName(interfaze);
        String classSimpleName = ClassMethodUtils.getClassSimpleName(clazz);
        String classSimpleNameLowerCaseFirstLetter = String.format(
                "%s%s",
                classSimpleName.substring(0, 1).toUpperCase(Locale.ROOT),
                classSimpleName.substring(1)
        );
        return List.of(
                SYNCHRONIZATION.get(0).get(0),
                String.format(
                        "%s%s%s%s%s%s%s%s%s",
                        SYNCHRONIZATION.get(1).get(0),
                        interfaceSimpleName,
                        SYNCHRONIZATION.get(1).get(1),
                        classSimpleName,
                        SYNCHRONIZATION.get(1).get(2),
                        interfaceSimpleName,
                        SYNCHRONIZATION.get(1).get(3),
                        classSimpleNameLowerCaseFirstLetter,
                        SYNCHRONIZATION.get(1).get(4)
                )
        );
    }
}
