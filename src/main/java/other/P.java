package other;

class P {
    abstract class A {
        abstract void test();
    }

    abstract class B extends A {
        @Override
        void test() {
        }
    }

    class C extends B {
        @Override
        void test() {
        }
    }
}
