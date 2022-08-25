package interfaceVsAbstractClass;

public interface Interface1 {

    /*******************************************************/
    public static final int public_static_final_int = 0;
    // protected static final int protected_static_final_int = 0;
    // private static final int private_static_final_int = 0;
    static final int static_final_int = 0;
    static int static_int = 0;
    final int final_int = 0;
    int an_int = 0;

    /*******************************************************/
    public void public_method();
    // protected void protected_method();
    // private void private_method();

    void method(); // public

    /*******************************************************/
    default public void default_public_method() {
    }

    default void default_method() { // public
    }

    /*******************************************************/
    // default public static void method7() {}
    public static void public_static_method() {
    }

    static void static_method() { // public
    }
}
