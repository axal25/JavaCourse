package classloader;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.Nulls;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.http.HttpRequest;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassLoaderTest {
    private static final String PACKAGE_NAME = "jdk.internal.loader";
    private static final String CLASS_NAME = "ClassLoaders";
    private static final String INNER_CLASS_NAME_APP = "AppClassLoader";
    private static final String INNER_CLASS_NAME_PLATFORM = "PlatformClassLoader";

    @Test
    void appClassLoader() {
        ClassLoader appClassLoader = this.getClass().getClassLoader();
        assertThat(appClassLoader.getClass().getPackageName()).isEqualTo(PACKAGE_NAME);
        assertThat(appClassLoader.getClass().getName())
                .isEqualTo(String.format("%s.%s$%s", PACKAGE_NAME, CLASS_NAME, INNER_CLASS_NAME_APP));
        assertThat(appClassLoader.toString())
                .isEqualTo(String.format("%s.%s$%s@%s", PACKAGE_NAME, CLASS_NAME, INNER_CLASS_NAME_APP, Integer.toHexString(appClassLoader.hashCode())));

        assertThat(ClassLoaderTest.class.getClassLoader()).isEqualTo(appClassLoader);
        assertThat(JacksonAnnotation.class.getClassLoader()).isEqualTo(appClassLoader);
        assertThat(Nulls.class.getClassLoader()).isEqualTo(appClassLoader);

        ClassLoader platformClassLoader = DriverManager.class.getClassLoader();
        assertThat(appClassLoader.getParent()).isEqualTo(platformClassLoader);
        assertThat(appClassLoader.getParent().getParent()).isNull();
    }

    @Test
    void threadContextClassLoader() {
        Thread thread = new Thread();
        ClassLoader threadContextClassLoader = thread.getContextClassLoader();

        ClassLoader appClassLoader = this.getClass().getClassLoader();
        assertThat(threadContextClassLoader).isEqualTo(appClassLoader);
    }

    @Test
    void platformClassLoader() {
        ClassLoader platformClassLoader = DriverManager.class.getClassLoader();
        assertThat(platformClassLoader.getClass().getPackageName()).isEqualTo(PACKAGE_NAME);
        assertThat(platformClassLoader.getClass().getName())
                .isEqualTo(String.format("%s.%s$%s", PACKAGE_NAME, CLASS_NAME, INNER_CLASS_NAME_PLATFORM));
        assertThat(platformClassLoader.toString())
                .isEqualTo(String.format("%s.%s$%s@%s", PACKAGE_NAME, CLASS_NAME, INNER_CLASS_NAME_PLATFORM, Integer.toHexString(platformClassLoader.hashCode())));

        assertThat(HttpRequest.class.getClassLoader()).isEqualTo(platformClassLoader);
    }

    @Test
    void bootstrapClassLoader() {
        assertThat(List.class.getClassLoader()).isNull();

        assertThat(ArrayList.class.getClassLoader()).isNull();
        assertThat(Iterator.class.getClassLoader()).isNull();
        assertThat(Iterable.class.getClassLoader()).isNull();
        assertThat(Object.class.getClassLoader()).isNull();
        assertThat(TimeUnit.class.getClassLoader()).isNull();
        assertThat(Calendar.class.getClassLoader()).isNull();
        assertThat(Method.class.getClassLoader()).isNull();
    }

    @Test
    void appClassLoader_loadClass_passesClassesToLoadToParent() {
        ClassLoader appClassLoader = this.getClass().getClassLoader();
        assertDoesNotThrow(() -> {
            Class<?> loadedClass = appClassLoader.loadClass(this.getClass().getName());
            assertThat(loadedClass).isEqualTo(this.getClass());
        });
        assertDoesNotThrow(() -> {
            Class<?> loadedClass = appClassLoader.loadClass(DriverManager.class.getName());
            assertThat(loadedClass).isEqualTo(DriverManager.class);
        });
        assertDoesNotThrow(() -> {
            Class<?> loadedClass = appClassLoader.loadClass(Object.class.getName());
            assertThat(loadedClass).isEqualTo(Object.class);
        });
    }

    @Test
    void platformClassLoader_loadClass_passesClassesToLoadToParent() {
        ClassLoader platformClassLoader = DriverManager.class.getClassLoader();
        assertThrows(ClassNotFoundException.class, () -> platformClassLoader.loadClass(this.getClass().getName()));
        assertDoesNotThrow(() -> {
            Class<?> loadedClass = platformClassLoader.loadClass(DriverManager.class.getName());
            assertThat(loadedClass).isEqualTo(DriverManager.class);
        });
        assertDoesNotThrow(() -> {
            Class<?> loadedClass = platformClassLoader.loadClass(Object.class.getName());
            assertThat(loadedClass).isEqualTo(Object.class);
        });
    }
}
