package input.read.formatter.block;

import utils.ClassMethodUtils;
import utils.StringUtils;

import java.util.Objects;

public class CopyOnWriteBlock implements Blocky {

    private volatile Block block;

    public CopyOnWriteBlock() {
        block = new Block();
    }

    private CopyOnWriteBlock(CopyOnWriteBlock copyOnWriteBlock) {
        block = new Block(copyOnWriteBlock.block);
    }

    @Override
    public CopyOnWriteBlock append(String toBeAdded) {
        block = block.clone().append(toBeAdded);
        return this;
    }

    @Override
    public CopyOnWriteBlock clone() {
        return new CopyOnWriteBlock(this);
    }

    @Override
    public int getLines() {
        return block.getLines();
    }

    @Override
    public StringBuilder getContents() {
        return block.getContents();
    }

    @Override
    public int getLinesToPrint() {
        return block.getLinesToPrint();
    }

    @Override
    public StringBuilder getContentsToPrint() {
        return block.getContentsToPrint();
    }

    @Override
    public boolean isNlMissingAtTheEnd() {
        return block.isNlMissingAtTheEnd();
    }

    Block getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")
                .append(StringUtils.NL)
                .append("block=")
                .append(block.toString())
                .append(StringUtils.NL)
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object instanceof CopyOnWriteBlock) {
            CopyOnWriteBlock copyOnWriteBlock = (CopyOnWriteBlock) object;
            return block.equals(copyOnWriteBlock.block);
        }
        if (object instanceof Block) {
            Block block = (Block) object;
            return this.block.equals(block);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }
}
