package interview.tree.binary;

import org.junit.jupiter.api.*;
import utils.StringUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for BinaryTree")
public class BinaryTreeTest {

    private static final String BINARY_TREE = BinaryTree.class.getSimpleName();
    private static final String BINARY_TREE_NODE = BinaryTreeNode.class.getSimpleName();

    @Test
    @Order(1)
    public void constructor_Equals_allArgsNull() {
        BinaryTree<Integer> binaryTree1 = new BinaryTree<>(new BinaryTreeNode<>(null, null, null));
        BinaryTree<Integer> binaryTree2 = new BinaryTree<>(new BinaryTreeNode<>(null, null, null));
        assertThat(binaryTree1, is(equalTo(binaryTree2)));
    }

    @Test
    @Order(2)
    public void constructor_Equals_onlyValueNonNull() {
        BinaryTree<Integer> binaryTree1 = new BinaryTree<>(new BinaryTreeNode<>(1, null, null));
        BinaryTree<Integer> binaryTree2 = new BinaryTree<>(new BinaryTreeNode<>(1, null, null));
        assertThat(binaryTree1, is(equalTo(binaryTree2)));
    }

    @Test
    @Order(3)
    public void constructorToString_1Leaf() {
        String expectedToString = BINARY_TREE + "{" + StringUtils.NL +
                StringUtils.TAB + "root=" + BINARY_TREE_NODE + "{" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "value=1" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "left=null" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "right=null" + StringUtils.NL +
                StringUtils.TAB + "}" + StringUtils.NL +
                "}";
        BinaryTree<Integer> binaryTree = new BinaryTree<>(
                new BinaryTreeNode<>(1, null, null));
        assertThat(binaryTree.toString(), is(equalTo(expectedToString)));
    }

    @Test
    @Order(4)
    public void constructorToString_1Root2Leafs() {
        String expectedToString = BINARY_TREE + "{" + StringUtils.NL +
                StringUtils.TAB + "root=" + BINARY_TREE_NODE + "{" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "value=1" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "left=" + BINARY_TREE_NODE + "{" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "value=2" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "left=null" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "right=null" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "}" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "right=" + BINARY_TREE_NODE + "{" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "value=3" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "left=null" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + StringUtils.TAB + "right=null" + StringUtils.NL +
                StringUtils.TAB + StringUtils.TAB + "}" + StringUtils.NL +
                StringUtils.TAB + "}" + StringUtils.NL +
                "}";
        BinaryTree<Integer> binaryTree = new BinaryTree<>(
                new BinaryTreeNode<>(1,
                        new BinaryTreeNode<>(2, null, null),
                        new BinaryTreeNode<>(3, null, null)));
        assertThat(binaryTree.toString(), is(equalTo(expectedToString)));
    }
}
