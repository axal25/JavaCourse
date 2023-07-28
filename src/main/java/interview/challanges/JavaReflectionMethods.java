package interview.challanges;

import lombok.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaReflectionMethods {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    @Data
    @Generated
    @ToString
    @EqualsAndHashCode
    private static class Student {
        private String name;
        private String id;
        private String email;
        private Integer integer;

        public void aaa() {
        }

        public void bbb() {
        }

        public void ccc() {
        }

        public void ddd() {
        }

        public void eee() {
        }

        public void fff() {
        }

        public void ggg() {
        }

        public void hhh() {
        }

        public void abc() {
        }

        public void def() {
        }

        public void ghi() {
        }
    }

    public static void main(String[] args) {
        Class<?> student = Student.class;
        Method[] methods = Student.class.getDeclaredMethods();

        List<String> methodList = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            methodList.add(methods[i].getName());
        }
        Collections.sort(methodList);
        for (String name : methodList) {
            System.out.println(name);
        }
    }
}
