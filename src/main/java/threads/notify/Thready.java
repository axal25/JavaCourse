package threads.notify;

public class Thready extends Thread {
    private Runnably runnably;

    Thready(Runnably runnably) {
        super(runnably);
        this.runnably = runnably;
    }

    public Runnably getRunnably() {
        return this.runnably;
    }

    int getValue() {
        return this.runnably.getValue();
    }
}
