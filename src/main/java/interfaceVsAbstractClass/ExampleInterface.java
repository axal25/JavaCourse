package interfaceVsAbstractClass;

import static interfaceVsAbstractClass.MainInterfaceVsAbstractClass.FORMAT_MSG_OBLIGATORY_METHOD;

public interface ExampleInterface {

    /*******************************************************/
    public static final int PUBLIC_STATIC_FINAL_INT = 0;
    // protected static final int protected_static_final_int = 0;
    // private static final int private_static_final_int = 0;
    static final int STATIC_FINAL_INT = 0;
    static int STATIC_INT = 0;
    final int FINAL_INT = 0;
    int INT = 0;

    /*******************************************************/

    public static void public_static_method() {
    }

    static void static_method() {
    }

    // protected static void protected_static_method() {}

    private static void private_static_method() {
    }

    /*******************************************************/

    // default public static void default_public_static_method() {}
    // public static void public_unimplemented_method();
    // static void static_unimplemented_method();

    /*******************************************************/

    // protected void protected_method();
    // private void private_method();

    /*******************************************************/
    public void public_method();

    void method(); // public

    /*******************************************************/

    // public void public_implemented_method() {}

    // protected void protect_implemented_method() {}

    private void private_implemented_method() {}

    /*******************************************************/
    default public void default_public_method() {
    }

    default void default_method() { // public
    }

    // default protected void default_protect_method() {}

    // default private void default_private_method() {}

    /*******************************************************/

    public static final class ExampleClassImplementingInterface implements ExampleInterface {

        @Override
        public void public_method() {
            System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "public_method"));
        }

        @Override
        public void method() {
            System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "method"));
        }
    }

    /*******************************************************/

    static void trySomeThingsWithInterface() {
        System.out.println("\tInterface");
        ExampleInterface exampleInterface = new ExampleInterface() {
            @Override
            public void public_method() {
                System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "public_method"));
            }

            @Override
            public void method() {
                System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "method"));
            }
        };

        int var;
        var = ExampleInterface.PUBLIC_STATIC_FINAL_INT;
        var = ExampleInterface.STATIC_FINAL_INT;
        var = ExampleInterface.STATIC_INT;
        var = ExampleInterface.FINAL_INT;
        var = ExampleInterface.INT;

        var = exampleInterface.PUBLIC_STATIC_FINAL_INT;
        var = exampleInterface.STATIC_FINAL_INT;
        var = exampleInterface.STATIC_INT;
        var = exampleInterface.FINAL_INT;
        var = exampleInterface.INT;

        System.out.println("\t\tAll variables are public, static, final by default");
        System.out.println("\t\tCannot have non-static, non-final variables");
        // Interface1.public_static_final_int++;
        // Interface1.static_final_int++;
        // Interface1.static_int++;
        // Interface1.final_int++;
        // Interface1.an_int++;

        System.out.println("\t\tVariable that was assigned value from Interface's variable can be modified because it is separate variable.");
        System.out.println("\t\tPassing parameters (to methods) in Java is always done by value and NOT by reference.");
        var++;

        ExampleInterface.public_static_method();
        ExampleInterface.static_method();
        ExampleInterface.private_static_method();

        // ExampleClassImplementingInterface.public_static_method();
        // ExampleClassImplementingInterface.static_method();
        // ExampleClassImplementingInterface.private_static_method();

        exampleInterface.public_method();
        exampleInterface.method();

        exampleInterface.private_implemented_method();

        ExampleClassImplementingInterface exampleClassImplementingInterface = new ExampleClassImplementingInterface();

        exampleClassImplementingInterface.public_method();
        exampleClassImplementingInterface.method();

        // exampleClassImplementingInterface.private_implemented_method();

        exampleInterface.default_public_method();
        exampleInterface.default_method();
    }
}
