package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

class ThreadClassStaticAccessor extends Thread {

    ThreadClassStaticAccessor(String shortName) {
        super(
                () -> SynchronizedContainer.setStaticStringVar(String.format("Set by %s", getName(shortName))),
                getName(shortName));
    }

    private static String getName(String shortName) {
        return NameGenerator.getName(shortName, ThreadClassStaticAccessor.class);
    }
}
