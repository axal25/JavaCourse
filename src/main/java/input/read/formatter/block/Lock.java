package input.read.formatter.block;

import utils.ClassMethodUtils;

import java.util.Objects;

public class Lock {

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Lock)) return false;
        Lock lock = (Lock) object;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
