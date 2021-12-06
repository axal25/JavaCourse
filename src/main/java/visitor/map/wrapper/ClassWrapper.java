package visitor.map.wrapper;

import java.util.Objects;

public class ClassWrapper<T> {
    private final Class<T> wrappedClass;
    private final String differentiator;

    public ClassWrapper(Class<T> clazz) {
        this.differentiator = new String(clazz.getName());
        this.wrappedClass = clazz;
    }

    public Class<T> getWrappedClass() {
        return wrappedClass;
    }

    public String getDifferentiator() {
        return differentiator;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (wrappedClass != null) {
            result = 19 * result + wrappedClass.hashCode();
        }
        if (differentiator != null) {
            result = 23 * result + differentiator.hashCode();
        }
        result = 29 * result + super.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof ClassWrapper)) {
            return false;
        }
        ClassWrapper other = null;
        try {
            other = (ClassWrapper<T>) obj;
        } catch (ClassCastException ex) {
            return false;
        }
        return Objects.equals(this.wrappedClass, other.wrappedClass)
                && Objects.equals(this.differentiator, other.differentiator)
                && this.differentiator == other.differentiator
                && super.equals(obj);
    }
}
