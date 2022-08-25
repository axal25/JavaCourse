package interfaceVsAbstractClass;

public class MainInterfaceVsAbstractClass {
    // TODO: https://www.geeksforgeeks.org/difference-between-abstract-class-and-interface-in-java/

    public static void main(String[] args) {
        Interface1 interface1 = new Interface1() {
            @Override
            public void public_method() {

            }

            @Override
            public void method() {

            }
        };

        int var;
        var = Interface1.public_static_final_int;
        var = Interface1.static_final_int;
        var = Interface1.static_int;
        var = Interface1.final_int;
        var = Interface1.an_int;

        // Interface1.public_static_final_int++;
        // Interface1.static_final_int++; // is public by default
        // Interface1.static_int++; // is public, static by default
        // Interface1.final_int++; // is public, final by default
        // Interface1.an_int++; // is public, final, static by default

        interface1.public_method();
        interface1.method();

        interface1.default_public_method();
        interface1.default_method();

        Interface1.public_static_method();
        Interface1.static_method();
    }
}
