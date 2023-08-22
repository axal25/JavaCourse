package collections.implementation.custom.tree.binary;

import collections.implementation.custom.tree.binary.BinaryTree.Node;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class GoodNodesTest {

    /**
     * Given Binary Tree Root.
     * A node X in the Tree is named 'Good' if in the path from Root to X there are no nodes with a value greater than X.
     * Return the number of 'Good' nodes in the Binary Tree.
     * ------
     * Good Node - Maximum Value Node on the Branch found so far (from Root to current Node) - count.
     * ------
     * 3
     * /   \
     * 1     4
     * / \     \
     * 3   1     5
     */
    GoodBinaryTree goodBinaryTree = new GoodBinaryTree(
            new Node<>(
                    3,
                    new Node<>(
                            1,
                            new Node<>(3, null, null),
                            new Node<>(1, null, null)),
                    new Node<>(
                            4,
                            null,
                            new Node<>(5, null, null))));

    @Test
    void getGoodNodeCount() {
        assertThat(goodBinaryTree).isNotNull();
        assertThat(goodBinaryTree.getGoodNodeCount()).isEqualTo(4);
    }

    @Test
    void getGoodNodes() {
        assertThat(goodBinaryTree).isNotNull();
        Set<Node<Integer>> expecteds = new LinkedHashSet<>();
        expecteds.add(goodBinaryTree.getRoot());
        expecteds.add(goodBinaryTree.getRoot().getLeft().getLeft());
        expecteds.add(goodBinaryTree.getRoot().getRight());
        expecteds.add(goodBinaryTree.getRoot().getRight().getRight());
        assertThat(goodBinaryTree.getGoodNodes()).isEqualTo(expecteds);
    }

    private static final class GoodBinaryTree extends BinaryTree<Integer> {

        public GoodBinaryTree(Node<Integer> root) {
            super(root);
        }

        private int getGoodNodeCountRecursive(Node<Integer> current, Node<Integer> previouslyWithMaxValue) {
            if (current == null) {
                return 0;
            }

            Node<Integer> currentWithMaxValue = current.getValue() >= previouslyWithMaxValue.getValue()
                    ? current : previouslyWithMaxValue;

            return (currentWithMaxValue == current ? 1 : 0)
                    + getGoodNodeCountRecursive(current.getLeft(), currentWithMaxValue)
                    + getGoodNodeCountRecursive(current.getRight(), currentWithMaxValue);
        }

        public int getGoodNodeCount() {
            return getGoodNodeCountRecursive(getRoot(), getRoot());
        }

        private Set<Node<Integer>> getGoodNodes() {
            return getGoodNodesRecursive(getRoot(), getRoot());
        }

        private Set<Node<Integer>> getGoodNodesRecursive(Node<Integer> current, Node<Integer> previouslyWithMaxValue) {
            if (current == null) {
                return Set.of();
            }

            Node<Integer> currentWithMaxValue = current.getValue() >= previouslyWithMaxValue.getValue()
                    ? current : previouslyWithMaxValue;

            Set<Node<Integer>> goodNodes = new LinkedHashSet<>();
            if (currentWithMaxValue == current) {
                goodNodes.add(currentWithMaxValue);
            }
            goodNodes.addAll(getGoodNodesRecursive(current.getLeft(), currentWithMaxValue));
            goodNodes.addAll(getGoodNodesRecursive(current.getRight(), currentWithMaxValue));
            return goodNodes;
        }
    }
}
