package interview.tree.binary;

import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for MaximumDistinctNodesFinder")
public class MaximumDistinctNodesFinderTest {

    private MaximumDistinctNodesFinder<Integer> maximumDistinctNodesFinder;

    @BeforeEach
    void setUp() {
        BinaryTree<Integer> integerBinaryTree = new BinaryTree<>(
                new BinaryTreeNode<>(1,
                        new BinaryTreeNode<>(2,
                                new BinaryTreeNode<>(4, null, null),
                                new BinaryTreeNode<>(5, null, null)),
                        new BinaryTreeNode<>(3,
                                new BinaryTreeNode<>(6,
                                        null,
                                        new BinaryTreeNode<>(8, null, null)),
                                new BinaryTreeNode<>(3,
                                        null,
                                        new BinaryTreeNode<>(9, null, null))
                        )));
        maximumDistinctNodesFinder = new MaximumDistinctNodesFinder<>(integerBinaryTree);
    }

    @Test
    @Order(1)
    public void expectedBinaryTree() {
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getValue(),
                is(equalTo(1)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getValue(),
                is(equalTo(2)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getLeft().getValue(),
                is(equalTo(4)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getLeft().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getLeft().getRight(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getRight().getValue(),
                is(equalTo(5)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getRight().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getLeft().getRight().getRight(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getValue(),
                is(equalTo(3)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getValue(),
                is(equalTo(6)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getRight().getValue(),
                is(equalTo(8)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getRight().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getRight().getRight(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getRight().getValue(),
                is(equalTo(3)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getRight().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getRight().getRight().getValue(),
                is(equalTo(9)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getRight().getRight().getLeft(),
                is(equalTo(null)));
        assertThat(
                maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getRight().getRight().getRight(),
                is(equalTo(null)));
    }

    @Test
    @Order(1)
    public void expectedFindResult() {
        assertThat(
                maximumDistinctNodesFinder.find().size(),
                is(equalTo(4)));
        assertThat(
                maximumDistinctNodesFinder.find().stream().map(BinaryTreeNode::getValue).collect(toUnmodifiableList()),
                is(equalTo(List.of(1, 3, 6, 8))));
        assertThat(
                maximumDistinctNodesFinder.find(),
                is(equalTo(List.of(
                        maximumDistinctNodesFinder.getBinaryTree().getRoot(),
                        maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight(),
                        maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft(),
                        maximumDistinctNodesFinder.getBinaryTree().getRoot().getRight().getLeft().getRight()
                )))
        );
    }
}
