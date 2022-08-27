package interfaceVsAbstractClass;

import static interfaceVsAbstractClass.MainInterfaceVsAbstractClass.FORMAT_MSG_OBLIGATORY_METHOD;

public abstract class ExampleAbstractClass {
    // @formatter:off

    /*******************************************************/
    private static final int PUBLIC_STATIC_FINAL_INT = 0;
    protected static final int protected_static_final_int = 0;
    private static final int private_static_final_int = 0;
    static final int staticFinalInt = 0;
    static int staticInt = 0;
    final int finalInt = 0;
    int anInt = 0;

    /*******************************************************/

    public static void public_static_method() {
    }

    static void static_method() { // default access
    }

    protected static void protected_static_method() {
    }

    private static void private_static_method() { // default access
    }

    /*******************************************************/

    // abstract static void abstract_static() {}

    /*******************************************************/
    public abstract void public_abstract_method();
    // private abstract void private_abstract_method();

    protected abstract void protected_abstract_method();

    abstract void abstract_method(); // default access

    /*******************************************************/
    public void public_method() {
    }

    void method() {
    }

    protected void protected_method() {
    }

    private void private_method() {
    }

    /*******************************************************/

    public static final class ExampleClassExtendingClass extends ExampleAbstractClass {

        @Override
        public void public_abstract_method() {
            System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "public_abstract_method"));
        }

        @Override
        protected void protected_abstract_method() {
            System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "protected_abstract_method"));
        }

        @Override
        void abstract_method() {
            System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "abstract_method"));
        }
    }

    /*******************************************************/

    static void trySomeThingsWithAbstractClass() {
        System.out.println("\tAbstract class");
        ExampleAbstractClass exampleAbstractClass = new ExampleAbstractClass() {
            @Override
            public void public_abstract_method() {
                System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "public_abstract_method"));
            }

            @Override
            protected void protected_abstract_method() {
                System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "protected_abstract_method"));
            }

            @Override
            void abstract_method() {
                System.out.println(String.format(FORMAT_MSG_OBLIGATORY_METHOD, "abstract_method"));
            }
        };

        int var;

        var = ExampleAbstractClass.PUBLIC_STATIC_FINAL_INT;
        var = ExampleAbstractClass.protected_static_final_int;
        var = ExampleAbstractClass.private_static_final_int;
        var = ExampleAbstractClass.staticFinalInt;
        var = ExampleAbstractClass.staticInt;

        var = exampleAbstractClass.finalInt;
        var = exampleAbstractClass.anInt;

        System.out.println("\t\tCan have non-final variables");
        System.out.println("\t\tFinal variables cannot modified");
        // ExampleAbstractClass.PUBLIC_STATIC_FINAL_INT++;
        // ExampleAbstractClass.protected_static_final_int++;
        // ExampleAbstractClass.private_static_final_int++;
        // ExampleAbstractClass.staticFinalInt++;
        ExampleAbstractClass.staticInt++;

        // exampleAbstractClass.finalInt++;
        exampleAbstractClass.anInt++;
        var++;

        ExampleAbstractClass.public_static_method();
        ExampleAbstractClass.static_method();
        ExampleAbstractClass.protected_static_method();
        ExampleAbstractClass.private_static_method();

        exampleAbstractClass.public_abstract_method();
        exampleAbstractClass.protected_abstract_method();
        exampleAbstractClass.abstract_method();

        exampleAbstractClass.public_method();
        exampleAbstractClass.method();
        exampleAbstractClass.protected_method();
        exampleAbstractClass.private_method();

        ExampleClassExtendingClass.public_static_method();
        ExampleClassExtendingClass.static_method();
        ExampleClassExtendingClass.protected_static_method();
        // ExampleClassExtendingClass.private_static_method();
    }

    // @formatter:on
}
