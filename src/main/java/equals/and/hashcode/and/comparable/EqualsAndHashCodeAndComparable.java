package equals.and.hashcode.and.comparable;

import utils.StaticUtils;
import utils.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class EqualsAndHashCodeAndComparable {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticUtils.printMainSignature();

        DefaultEqualsAndHashAndComparable default1 = new DefaultEqualsAndHashAndComparable(1);
        DefaultEqualsAndHashAndComparable default2 = new DefaultEqualsAndHashAndComparable(1);

        demo("Default", "default1", default1, "default2", default2);

        CustomEqualsAndHashAndComparable custom1 = new CustomEqualsAndHashAndComparable(1);
        CustomEqualsAndHashAndComparable custom2 = new CustomEqualsAndHashAndComparable(1);

        demo("Custom", "custom1", custom1, "custom2", custom2);
    }

    private static void demo(
            String exName,
            String exName1, IExampleEqualsAndHashAndComparable exVar1,
            String exName2, IExampleEqualsAndHashAndComparable exVar2
    ) {
        System.out.println(String.format("%s == %s ? %b", exName1, exName2, exVar1 == exVar2));
        System.out.println(String.format("%s.equals(%s) ? %b", exName1, exName2, exVar1.equals(exVar2)));
        System.out.println(String.format("Objects.equals(%s, %s) ? %b", exName1, exName2, Objects.equals(exVar1, exVar2)));
        System.out.println(String.format("Objects.equals(%s, %s) ? %b", exName1, exName2, exVar1.compareTo(exVar2)));

        comparatorDemo(exName1, exVar1, exName2, exVar2);

        Map<IExampleEqualsAndHashAndComparable, String> exHashMap = new HashMap<>();
        mapDemo(exName1, exVar1, exName2, exVar2, String.format("%sHashMap", exName), exHashMap);
        Map<IExampleEqualsAndHashAndComparable, String> exTreeMap = new TreeMap<>();
        mapDemo(exName1, exVar1, exName2, exVar2, String.format("%sHashMap", exName), exTreeMap);
    }

    private static void comparatorDemo(
            String exName1, IExampleEqualsAndHashAndComparable exVar1,
            String exName2, IExampleEqualsAndHashAndComparable exVar2
    ) {
        if (exVar1 instanceof DefaultEqualsAndHashAndComparable && exVar2 instanceof DefaultEqualsAndHashAndComparable) {
            System.out.println(String.format(
                    "Comparator.<DefaultEqualsAndHashAndComparable>naturalOrder().compare(%s, %s) ? %d",
                    exName1,
                    exName2,
                    Comparator.<DefaultEqualsAndHashAndComparable>naturalOrder().compare(
                            (DefaultEqualsAndHashAndComparable) exVar1,
                            (DefaultEqualsAndHashAndComparable) exVar2
                    )
            ));
            System.out.println(String.format("DefaultEqualsAndHashAndComparable.COMPARATOR.compare(%s, %s) ? %d",
                    exName1,
                    exName2,
                    DefaultEqualsAndHashAndComparable.COMPARATOR.compare(
                            (DefaultEqualsAndHashAndComparable) exVar1,
                            (DefaultEqualsAndHashAndComparable) exVar2
                    )
            ));
        } else if (exVar1 instanceof CustomEqualsAndHashAndComparable && exVar2 instanceof CustomEqualsAndHashAndComparable) {
            System.out.println(String.format(
                    "Comparator.<CustomEqualsAndHashAndComparable>naturalOrder().compare(%s, %s) ? %d",
                    exName1,
                    exName2,
                    Comparator.<CustomEqualsAndHashAndComparable>naturalOrder().compare(
                            (CustomEqualsAndHashAndComparable) exVar1,
                            (CustomEqualsAndHashAndComparable) exVar2
                    )
            ));
            System.out.println(String.format("CustomEqualsAndHashAndComparable.COMPARATOR.compare(%s, %s) ? %d",
                    exName1,
                    exName2,
                    CustomEqualsAndHashAndComparable.COMPARATOR.compare(
                            (CustomEqualsAndHashAndComparable) exVar1,
                            (CustomEqualsAndHashAndComparable) exVar2
                    )
            ));
        } else {
            System.out.println(String.format(
                    "Comparator.<IExampleEqualsAndHashAndComparable>naturalOrder().compare(%s, %s) ? %d",
                    exName1,
                    exName2,
                    Comparator.<IExampleEqualsAndHashAndComparable>naturalOrder().compare(
                            exVar1,
                            exVar2
                    )
            ));
            System.out.println(String.format(
                    "Comparator.<IExampleEqualsAndHashAndComparable>naturalOrder().compare(exVar1, exVar2) ? %d",
                    Comparator.<IExampleEqualsAndHashAndComparable>naturalOrder().compare(exVar1, exVar2)
            ));
        }
        System.out.println(String.format("IExampleEqualsAndHashAndComparable.COMPARATOR.compare(%s, %s) ? %d",
                exName1,
                exName2,
                IExampleEqualsAndHashAndComparable.COMPARATOR.compare(
                        exVar1,
                        exVar2
                )
        ));
    }

    private static void mapDemo(
            String exName1, IExampleEqualsAndHashAndComparable exVar1, String exName2, IExampleEqualsAndHashAndComparable exVar2,
            String exMapName,
            Map<IExampleEqualsAndHashAndComparable, String> exMap
    ) {
        exMap.put(exVar1, exName1);
        exMap.put(exVar2, exName2);

        StringBuilder sbDefaultHashMap = new StringBuilder();
        String prefixDefaultHashMap = String.format("%s HashMap: [\n", exMapName);
        sbDefaultHashMap.append(prefixDefaultHashMap);
        exMap.forEach((key, value) -> sbDefaultHashMap
                .append(String.format("%s%s(%s, %s)",
                        sbDefaultHashMap.length() != prefixDefaultHashMap.length() ? String.format(",%s", StringUtils.NL) : StringUtils.EMPTY,
                        StringUtils.TAB,
                        key.toString(),
                        value
                ))
        );
        sbDefaultHashMap.append(String.format("%s%s", StringUtils.NL, "]"));
        System.out.println(sbDefaultHashMap);
    }
}
