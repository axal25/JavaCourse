package collections.implementation.custom.tree.binary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class BinaryTree<T> {
    private Node<T> root;

    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Getter
    public static class Node<T> {
        private final T value;
        private final Node<T> left;
        private final Node<T> right;
    }
}
