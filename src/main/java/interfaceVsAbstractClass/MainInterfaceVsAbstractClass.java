package interfaceVsAbstractClass;

public class MainInterfaceVsAbstractClass {
    // TODO: https://www.geeksforgeeks.org/difference-between-abstract-class-and-interface-in-java/

    static final String FORMAT_MSG_OBLIGATORY_METHOD =
            "\t\t\t\"%s\" method had to be implement even when it does nothing.";

    public static void main(String[] args) {
        System.out.printf("%s - started", MainInterfaceVsAbstractClass.class.getSimpleName());
        ExampleInterface.trySomeThingsWithInterface();
        ExampleAbstractClass.trySomeThingsWithAbstractClass();
        printDifferences();
        System.out.printf("%s - finished", MainInterfaceVsAbstractClass.class.getSimpleName());
    }

    private static void printDifferences() {
        System.out.println("\tDifferences - Interface vs. Abstract class");
        int sideColumnsLength = 85;
        int midColumnLength = 30;
        String format = String.format("\t%%%ds | %%%ds | %%%ds\n", sideColumnsLength, midColumnLength, sideColumnsLength);

        printDash(format);
        printDash(format);
        System.out.printf(format, "Interface", "vs.", "Abstract class");
        printDash(format);
        printDash(format);

        System.out.printf(format, "", "Inheritance keyword", "");
        System.out.printf(format, "Class implements interfaces.", "", "Class extends class - also abstract class.");
        System.out.printf(format, "Interface extends interfaces.", "", "");

        printDash(format);

        System.out.printf(format, "", "Inheritance", "");
        System.out.printf(format, "Class can implement multiple interfaces.", "", "Class can extend only 1 class - also abstract class.");
        System.out.printf(format, "Interface can extend multiple interfaces.", "", "Abstract class can implement interfaces and extend class.");
        System.out.printf(format, "Interface cannot extend abstract class.", "", "");

        printDash(format);

        System.out.printf(format, "", "Fields", "");
        System.out.printf(format, "All interface's fields are public static final.", "", "Can have non-public, non-static, non-final fields.");

        printDash(format);

        System.out.printf(format, "", "Static Methods", "");
        System.out.printf(format, "Can have static methods.", "", "Can have static methods.");
        System.out.printf(format, "Static methods have to have a body.", "", "Static methods have to have a body.");
        System.out.printf(format, "Cannot have static default methods (default and static keywords don't mix).", "", "");
        System.out.printf(format, "", "", "Cannot have static abstract methods.");
        System.out.printf(format, "Static methods can be public, package, private. Cannot be protected.", "", "Static methods can have all access modifiers.");

        printDash(format);

        System.out.printf(format, "", "Static Methods Inheritance", "");
        System.out.printf(format, "Static methods are NOT inherited.", "", "Child class inherits static methods.");
        System.out.printf(format, "Cannot be called from ChildClass level like so:", "", "Can be called from ChildClass level like so:");
        System.out.printf(format, "ChildClass.interfaceStaticMethod();", "", "ChildClass.abstractClassStaticMethod();");
        System.out.printf(format, "Must be called from ParentInterface level like so:", "", "");
        System.out.printf(format, "ParentInterface.interfacePublicStaticMethod();", "", "");

        printDash(format);

        System.out.printf(format, "", "Non-Static Methods", "");
        System.out.printf(format, "", "", "Can have non-static (non-abstract) methods - implemented methods.");
        System.out.printf(format, "Can have public non-static default methods - implemented methods.", "", "");
        System.out.printf(format, "", "", "Can have abstract methods - methods unimplemented.");
        System.out.printf(format, "Can have public non-static (non-default) methods - methods unimplemented.", "", "");
        System.out.printf(format, "Can have private non-static non-default methods - private-use implemented methods.", "", "");
        System.out.printf(format, "", "", "All non-static methods can have all access modifiers.");
    }

    private static void printDash(String format) {
        String dashSide = "-------------------------------------------------------------------------------------";
        String dashMid = "------------------------------";
        System.out.printf(format, dashSide, dashMid, dashSide);
    }
}
