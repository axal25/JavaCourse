package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

class ThreadObjectInstanceAccessor extends Thread {

    ThreadObjectInstanceAccessor(
            String shortName, SynchronizedContainer synchronizedContainer) {
        super(
                () -> synchronizedContainer.setStringVar(String.format("Set by %s", getName(shortName))),
                getName(shortName));
    }

    private static String getName(String shortName) {
        return NameGenerator.getName(shortName, ThreadObjectInstanceAccessor.class);
    }
}
