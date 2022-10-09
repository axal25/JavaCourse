package input.read.formatter.block;

public interface Blocky extends Cloneable {

    Blocky append(String toBeAdded);

    public int getLines();

    public StringBuilder getContents();

    int getLinesToPrint();

    StringBuilder getContentsToPrint();

    boolean isNlMissingAtTheEnd();

    Blocky clone();

    @Override
    String toString();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
}
