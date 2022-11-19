package interview.tree.binary;

import utils.ClassMethodUtils;
import utils.StringUtils;

import java.util.Objects;
import java.util.stream.IntStream;

public class BinaryTreeNode<T> {
    private final T value;
    private final BinaryTreeNode<T> left, right;

    BinaryTreeNode(T value, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int tabNumber) {
        StringBuilder tabs = new StringBuilder();
        IntStream.range(0, tabNumber).forEach(i -> tabs.append(StringUtils.TAB));
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")

                .append(StringUtils.NL)
                .append(tabs)
                .append(StringUtils.TAB)
                .append("value=")
                .append(StringUtils.toStringNullSafe(value))

                .append(StringUtils.NL)
                .append(tabs)
                .append(StringUtils.TAB)
                .append("left=")
                .append(toStringNullSafe(left, tabNumber + 1))

                .append(StringUtils.NL)
                .append(tabs)
                .append(StringUtils.TAB)
                .append("right=")
                .append(toStringNullSafe(right, tabNumber + 1))

                .append(StringUtils.NL)
                .append(tabs)
                .append("}")

                .toString();
    }

    static <T> String toStringNullSafe(BinaryTreeNode<T> binaryTreeNode, int tabNumber) {
        return binaryTreeNode == null ? null : binaryTreeNode.toString(tabNumber);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        BinaryTreeNode<T> binaryTreeNode = null;
        if (object instanceof BinaryTreeNode) {
            try {
                binaryTreeNode = (BinaryTreeNode<T>) object;
            } catch (ClassCastException e) {
            }
        }
        if (binaryTreeNode == null) return false;
        return Objects.equals(value, binaryTreeNode.value)
                && Objects.equals(left, binaryTreeNode.left)
                && Objects.equals(right, binaryTreeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }
}
