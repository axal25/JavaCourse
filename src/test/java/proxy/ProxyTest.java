package proxy;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProxyTest {

    @Test
    void testProxy() {
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        TestClass testClass = new TestClass(logs);
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
                if (method.getName().equals("toString") && method.getParameterCount() == 0) {
                    return method.invoke(testClass, arguments);
                }
                List<Object> argumentList = Arrays.stream(arguments).collect(Collectors.toList());
                logs.add(this.getClass().getSimpleName() +
                        "#invoke(" +
                        Object.class.getSimpleName() + " proxy: " + proxy + ", " +
                        Method.class.getSimpleName() + " method: " + method + ", " +
                        Object.class.getSimpleName() + "[] arguments: " + argumentList +
                        ")");
                return method.invoke(testClass, arguments);
            }
        };
        ClassLoader classLoader = ProxyTest.class.getClassLoader();
        Class<?>[] interfaces = new Class<?>[]{TestInterface.class};
        Object proxyObject = Proxy.newProxyInstance(
                classLoader,
                interfaces,
                invocationHandler
        );
        Proxy proxy = (Proxy) proxyObject;
        TestInterface testInterfaceProxy = (TestInterface) proxyObject;

        assertThat(Proxy.getInvocationHandler(proxyObject)).isEqualTo(invocationHandler);

        assertThat(Proxy.isProxyClass(proxyObject.getClass())).isTrue();
        assertThat(Proxy.isProxyClass(proxy.getClass())).isTrue();
        assertThat(Proxy.isProxyClass(testInterfaceProxy.getClass())).isTrue();
        assertThat(Proxy.isProxyClass(testClass.getClass())).isFalse();

        Class<?> proxyClass = Proxy.getProxyClass(classLoader, interfaces);
        assertThat(proxyClass).isEqualTo(proxyObject.getClass());
        assertThat(proxyClass).isEqualTo(Proxy.getProxyClass(ProxyTest.class.getClassLoader(), TestInterface.class));
        assertThat(proxyClass).isEqualTo(Proxy.getProxyClass(TestClass.class.getClassLoader(), TestInterface.class));
        assertThat(proxyClass).isEqualTo(Proxy.getProxyClass(TestInterface.class.getClassLoader(), TestInterface.class));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                Proxy.getProxyClass(Object.class.getClassLoader(), TestInterface.class));
        assertThat(illegalArgumentException).hasMessageThat().isEqualTo(
                TestInterface.class.getName() + " referenced from a method is not visible from class loader");

        String output = testInterfaceProxy.testMethod("inputValue");

        assertThat(output).isEqualTo("testMethod_returnValue");
        Method testMethod = null;
        try {
            testMethod = TestInterface.class.getDeclaredMethod("testMethod", String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        assertThat(logs).isEqualTo(List.of(
                invocationHandler.getClass().getSimpleName() + "#invoke(" +
                        Object.class.getSimpleName() + " proxy: " + proxyObject + ", " +
                        Method.class.getSimpleName() + " method: " + testMethod + ", " +
                        Object.class.getSimpleName() + "[] arguments: " + List.of("inputValue") +
                        ")",
                TestClass.class.getSimpleName() + "#testMethod(" +
                        String.class.getSimpleName() + " input: inputValue" +
                        ")"));
    }

    private interface TestInterface {
        String testMethod(String input);
    }

    @AllArgsConstructor
    private static class TestClass implements TestInterface {
        private final List<String> logs;

        @Override
        public String testMethod(String input) {
            logs.add(this.getClass().getSimpleName() + "#testMethod(" +
                    String.class.getSimpleName() + " input: " + input +
                    ")");
            return "testMethod_returnValue";
        }
    }
}
