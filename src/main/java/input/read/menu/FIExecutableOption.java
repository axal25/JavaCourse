package input.read.menu;

public interface FIExecutableOption {

    default public void execWrapper(Menu menu) {
        exec();
        menu.open();
    }

    public void exec();
}
