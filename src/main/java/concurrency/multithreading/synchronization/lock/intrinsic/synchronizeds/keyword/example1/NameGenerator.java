package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

public class NameGenerator {

    public static <T extends Thread> String getName(String shortName, Class<T> clazz) {
        return String.format("%s - %s",
                shortName,
                clazz.getSimpleName());
    }
}
