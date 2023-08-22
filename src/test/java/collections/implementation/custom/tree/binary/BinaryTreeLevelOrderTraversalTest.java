package collections.implementation.custom.tree.binary;

import collections.implementation.custom.tree.binary.BinaryTree.Node;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.truth.Truth.assertThat;

public class BinaryTreeLevelOrderTraversalTest {
    /**
     * Given the Root of Binary Tree return Level Order Traversal of it's Node Values.
     * -----
     * Level Order Traversal => Breadth First Search
     * 3
     * / \
     * 9   20
     * / \
     * 15   7
     */

    LevelOrderTraversalBinaryTree lotBinaryTree = new LevelOrderTraversalBinaryTree(
            new Node<>(3,
                    new Node<>(9, null, null),
                    new Node<>(20,
                            new Node<>(15, null, null),
                            new Node<>(7, null, null))));

    @Test
    void getLevelOrderTraversalNodes() {
        assertThat(lotBinaryTree).isNotNull();
        LinkedList<Node<Integer>> expecteds = new LinkedList<>();
        expecteds.add(lotBinaryTree.getRoot());
        expecteds.add(lotBinaryTree.getRoot().getLeft());
        expecteds.add(lotBinaryTree.getRoot().getRight());
        expecteds.add(lotBinaryTree.getRoot().getRight().getLeft());
        expecteds.add(lotBinaryTree.getRoot().getRight().getRight());
        assertThat(lotBinaryTree.getLevelOrderTraversalNodes_original_ish()).isEqualTo(expecteds);
        assertThat(lotBinaryTree.myLevelOrderTraversalNodes_myVersion()).isEqualTo(expecteds);
    }

    private static final class LevelOrderTraversalBinaryTree extends BinaryTree<Integer> {
        public LevelOrderTraversalBinaryTree(Node<Integer> root) {
            super(root);
        }

        public Queue<Node<Integer>> getLevelOrderTraversalNodes_original_ish() {
            if (getRoot() == null) {
                return new LinkedList<>();
            }

            Queue<Node<Integer>> toBeProcessedLevel = new LinkedList<>();
            toBeProcessedLevel.offer(getRoot());
            Queue<Node<Integer>> result = new LinkedList<>();

            while (!toBeProcessedLevel.isEmpty()) {
                Queue<Node<Integer>> processedLevel = new LinkedList<>();
                int currentLevelSize = toBeProcessedLevel.size();

                for (int i = 0; i < currentLevelSize; i++) {
                    Node<Integer> currentNode = toBeProcessedLevel.poll();
                    processedLevel.offer(currentNode);

                    if (currentNode.getLeft() != null) {
                        toBeProcessedLevel.offer(currentNode.getLeft());
                    }
                    if (currentNode.getRight() != null) {
                        toBeProcessedLevel.offer(currentNode.getRight());
                    }
                }

                result.addAll(processedLevel);
            }

            return result;
        }

        public Queue<Node<Integer>> myLevelOrderTraversalNodes_myVersion() {
            LinkedList<Node<Integer>> levelOrderTraversalQueue = new LinkedList<>();
            if (getRoot() == null) {
                return levelOrderTraversalQueue;
            }
            levelOrderTraversalQueue.offer(getRoot());

            int current = 0;
            while (current < levelOrderTraversalQueue.size()) {
                int levelFinish = levelOrderTraversalQueue.size();
                while (current < levelFinish) {

                    if (levelOrderTraversalQueue.get(current).getLeft() != null) {
                        levelOrderTraversalQueue.offer(levelOrderTraversalQueue.get(current).getLeft());
                    }
                    if (levelOrderTraversalQueue.get(current).getRight() != null) {
                        levelOrderTraversalQueue.offer(levelOrderTraversalQueue.get(current).getRight());
                    }

                    current++;
                }
            }

            return levelOrderTraversalQueue;
        }
    }
}
