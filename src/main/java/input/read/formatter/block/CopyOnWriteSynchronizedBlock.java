package input.read.formatter.block;

import utils.ClassMethodUtils;
import utils.StringUtils;

import java.util.Objects;

public class CopyOnWriteSynchronizedBlock extends CopyOnWriteBlock {

    private final Lock lock = new Lock();

    @Override
    public CopyOnWriteSynchronizedBlock append(String toBeAdded) {
        synchronized (lock) {
            return (CopyOnWriteSynchronizedBlock) super.append(toBeAdded);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")
                .append(StringUtils.NL)
                .append("block=")
                .append(getBlock().toString())
                .append(",")
                .append(StringUtils.NL)
                .append("lock=")
                .append(lock)
                .append(StringUtils.NL)
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof CopyOnWriteSynchronizedBlock) {
            CopyOnWriteSynchronizedBlock copyOnWriteSynchronizedBlock = (CopyOnWriteSynchronizedBlock) object;
            return getBlock().equals(copyOnWriteSynchronizedBlock.getBlock())
                    && lock.equals(copyOnWriteSynchronizedBlock.lock);
        }
        if (object instanceof CopyOnWriteBlock) {
            CopyOnWriteBlock copyOnWriteBlock = (CopyOnWriteBlock) object;
            return getBlock().equals(copyOnWriteBlock.getBlock());
        }
        if (object instanceof Block) {
            Block block = (Block) object;
            return getBlock().equals(block);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlock(), lock);
    }
}
