package input.read.menu;

public class Option {
    private final String name;
    private final FIExecutableOption fiExecutableOption;

    public Option(String name, FIExecutableOption fiExecutableOption) {
        this.name = name;
        this.fiExecutableOption = fiExecutableOption;
    }

    public String getName() {
        return name;
    }

    FIExecutableOption getFiOption() {
        return fiExecutableOption;
    }
}
