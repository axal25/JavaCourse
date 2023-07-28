package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

class ThreadBlockObjectInstanceAccessor extends Thread {

    ThreadBlockObjectInstanceAccessor(
            String shortName, SynchronizedContainer synchronizedContainer) {
        super(
                () -> synchronizedContainer.setBlockStringVar(String.format("Set by %s", getName(shortName))),
                getName(shortName));
    }

    private static String getName(String shortName) {
        return NameGenerator.getName(shortName, ThreadBlockObjectInstanceAccessor.class);
    }
}
