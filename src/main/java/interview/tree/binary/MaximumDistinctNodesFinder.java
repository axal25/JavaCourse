package interview.tree.binary;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * Finds maximum distinct nodes in a Root to leaf path
 */
public class MaximumDistinctNodesFinder<T> {
    private final BinaryTree<T> binaryTree;

    MaximumDistinctNodesFinder(BinaryTree<T> binaryTree) {
        this.binaryTree = binaryTree;
    }

    public List<BinaryTreeNode<T>> find() {
        List<BinaryTreeNode<T>> inverted = find(binaryTree.getRoot());
        return IntStream.range(0, inverted.size())
                .map(i -> inverted.size() - 1 - i)
                .mapToObj(inverted::get)
                .collect(toUnmodifiableList());
    }

    public List<BinaryTreeNode<T>> find(final BinaryTreeNode<T> binaryTreeNode) {
        if (binaryTreeNode == null) {
            return List.of();
        }
        final List<BinaryTreeNode<T>> lefts = find(binaryTreeNode.getLeft());
        final List<BinaryTreeNode<T>> rights = find(binaryTreeNode.getRight());
        final List<BinaryTreeNode<T>> leftsConcated = concatIfNeeded(lefts, binaryTreeNode);
        final List<BinaryTreeNode<T>> rightsConcated = concatIfNeeded(rights, binaryTreeNode);
        return leftsConcated.size() < rightsConcated.size() ? rightsConcated : leftsConcated;
    }

    private List<BinaryTreeNode<T>> concatIfNeeded(
            final List<BinaryTreeNode<T>> binaryTreeNodes,
            final BinaryTreeNode<T> toBeAdded) {
        return containsValue(binaryTreeNodes, toBeAdded) ? binaryTreeNodes : concat(binaryTreeNodes, toBeAdded);
    }

    private boolean containsValue(
            final List<BinaryTreeNode<T>> binaryTreeNodes,
            final BinaryTreeNode<T> matchedAgainst) {
        return binaryTreeNodes.stream().anyMatch(binaryTreeNode -> match(binaryTreeNode, matchedAgainst));
    }

    private boolean match(BinaryTreeNode<T> binaryTreeNode1, BinaryTreeNode<T> binaryTreeNode2) {
        if (binaryTreeNode1 == binaryTreeNode2) {
            return true;
        }
        if (binaryTreeNode1 == null || binaryTreeNode2 == null) {
            return false;
        }
        return Objects.equals(binaryTreeNode1.getValue(), binaryTreeNode2.getValue());
    }

    private List<BinaryTreeNode<T>> concat(
            final List<BinaryTreeNode<T>> binaryTreeNodes,
            final BinaryTreeNode<T> toBeAdded) {
        return Stream.of(binaryTreeNodes, List.of(toBeAdded))
                .flatMap(Collection::stream)
                .collect(toUnmodifiableList());
    }

    BinaryTree<T> getBinaryTree() {
        return binaryTree;
    }
}
