package polymorphism;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

public class PolymorphicVariableParameterArgumentTest {
    private static class Parent1 {
        public String getInfo() {
            return Parent1.class.getSimpleName();
        }
    }
    private static class Child1 extends Parent1 {
        @Override
        public String getInfo() {
            return Child1.class.getSimpleName();
        }
    }

    @Test
    void polymorphicVariable() {
        Parent1 parent1 = new Parent1();
        assertThat(parent1.getInfo()).isEqualTo(Parent1.class.getSimpleName());
        parent1 = new Child1();
        assertThat(parent1.getInfo()).isEqualTo(Child1.class.getSimpleName());
        assertThat(((Parent1) parent1).getInfo()).isEqualTo(Child1.class.getSimpleName());
        assertThat(((Parent1) new Child1()).getInfo()).isEqualTo(Child1.class.getSimpleName());
    }

    private static class Parent2 {
        public String getData(int i) {
            return String.valueOf(i);
        }
    }

    private static class Child2 extends Parent2 {
        @Override
        public String getData(int i) {
            return "Hello World " + i;
        }
    }

    @Test
    void polymorphicParameterArgument() {
        Parent2 parent2 = new Parent2();
        assertThat(parent2.getData(1)).isEqualTo("1");
        parent2 = new Child2();
        assertThat(parent2.getData(1)).isEqualTo("Hello World 1");
        assertThat(new Child2().getData(1)).isEqualTo("Hello World 1");
    }

    private static class Parent3 {
        public String getInfo() {
            return Parent3.class.getSimpleName();
        }
    }

    private static class Child31 extends Parent3 {
        @Override
        public String getInfo() {
            return Child31.class.getSimpleName();
        }
    }

    private static class Child32 extends Parent3 {
        @Override
        public String getInfo() {
            return Child32.class.getSimpleName();
        }
    }

    private static class Child33 extends Parent3 {
        @Override
        public String getInfo() {
            return Child33.class.getSimpleName();
        }
    }

    @Test
    void subTypePolymorphism() {
        List<Parent3> parent3s = List.of(
                new Parent3(),
                new Child31(),
                new Child32(),
                new Child33());

        List<String> infos = parent3s.stream()
                .map(Parent3::getInfo)
                        .collect(Collectors.toList());

        assertThat(infos).isEqualTo(List.of(
                Parent3.class.getSimpleName(),
                Child31.class.getSimpleName(),
                Child32.class.getSimpleName(),
                Child33.class.getSimpleName()));
    }
}
