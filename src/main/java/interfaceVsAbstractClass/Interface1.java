package interfaceVsAbstractClass;

public interface Interface1 {

    /*******************************************************/
    public static final int variable1 = 0;
    // protected static final int variable2 = 0;
    // private static final int variable3 = 0;
    static final int variable4 = 0; // public

    static final int variable5 = 0; // public
    static int variable6 = 0; // public final
    int variable7 = 0; // public static final

    /*******************************************************/
    public void method1();

    void method2(); // public
    // protected void method3();
    // private void method4();

    /*******************************************************/
    default public void method5() {
    }

    default void method6() { // public
    }

    /*******************************************************/
    // default public static void method7() {}
    public static void method8() {
    }

    static void method9() { // public
    }
}
