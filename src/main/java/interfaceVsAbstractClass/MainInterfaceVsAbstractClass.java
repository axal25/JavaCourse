package interfaceVsAbstractClass;

public class MainInterfaceVsAbstractClass {
    // TODO: https://www.geeksforgeeks.org/difference-between-abstract-class-and-interface-in-java/
    
    public static void main(String[] args) {
        Interface1 interface1 = new Interface1() {
            @Override
            public void method1() {

            }

            @Override
            public void method2() {

            }
        };

        int var;
        var = Interface1.variable1;
        var = Interface1.variable4;
        var = Interface1.variable5;
        var = Interface1.variable6;
        var = Interface1.variable7;
        // Interface1.variable6++; // final
        // Interface1.variable7++; // static final


        interface1.method1();
        interface1.method2();
        interface1.method5();
        interface1.method6();
        Interface1.method8();
        Interface1.method9();
    }
}
