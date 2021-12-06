package threads;

public interface IMainThread extends IMessager {

    default public void runThreadsDemo() {
        System.out.println("\n" + getBreakerMessage());
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "runThreadsDemo", "start"));
        runThreads();
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "runThreadsDemo", "finish"));
        System.out.println(getBreakerMessage() + "\n");
    }

    default public String getBreakerMessage() {
        return "-----------------------------------------------------------------------------------------";
    }

    public void runThreads();
}
