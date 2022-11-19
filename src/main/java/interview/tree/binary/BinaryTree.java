package interview.tree.binary;

import utils.ClassMethodUtils;
import utils.StringUtils;

import java.util.Objects;

public class BinaryTree<T> {
    private final BinaryTreeNode<T> root;

    BinaryTree(BinaryTreeNode<T> root) {
        this.root = root;
    }

    public BinaryTreeNode<T> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")
                .append(StringUtils.NL)
                .append("root=")
                .append(BinaryTreeNode.toStringNullSafe(root, 1))
                .append(StringUtils.NL)
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        BinaryTree<T> binaryTree = null;
        if (object instanceof BinaryTree) {
            try {
                binaryTree = (BinaryTree<T>) object;
            } catch (ClassCastException e) {
            }
        }
        if (binaryTree == null) return false;
        return Objects.equals(root, binaryTree.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}
