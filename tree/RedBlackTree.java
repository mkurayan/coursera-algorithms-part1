public class RedBlackTree <Key extends Comparable<Key>, Value> {
    private Node root;

    public void put(Key key, Value value) {
        root = put(key, value, root);
    }

    private Node put(Key key, Value value, Node node) {
        if(node == null) {
            return new Node(key, value, true);
        }

        if(key.compareTo(node.key) < 0) {
            node.left = put(key, value, node.left);
        } else if(key.compareTo(node.key) > 0) {
            node.right = put(key, value, node.right);
        } else {
            node.value = value;
        }

        if(isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        if(isRed(node.left) && isRed(node.right)) {
            flipColor(node);
        }

        // todo: in lecture this was a first check, but I do not see a reason why we cannot put it here.
        if(isRed(node.right)) {
            node = rotateLeft(node);
        }

        return node;
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;

        node.right = right.left;
        right.left = node;

        right.isRed = node.isRed;
        node.isRed = true;

        return right;
    }

    private Node rotateRight(Node node) {
        Node left = node.left;

        node.left = left.right;
        left.right = node;

        left.isRed = node.isRed;
        node.isRed = true;

        return left;
    }

    private void flipColor(Node parent) {
        parent.left.isRed = false;
        parent.right.isRed = false;

        parent.isRed = true;
    }

    private boolean isRed(Node node) {
        if(node == null) {
            return false;
        }

        return node.isRed;
    }

    private class Node {
        private Key key;
        private Value value;
        private boolean isRed;

        private Node left;
        private Node right;

        public Node(Key k, Value val, boolean red) {
            key = k;
            value = val;
            isRed = red;
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Double, String> bst = new RedBlackTree<>();

        bst.put(4., "some val");
        bst.put(2., "wefwfwf");
        bst.put(6., "aaaaa");
        bst.put(5., "ooooo");
        bst.put(7., "Max");
        bst.put(1., "Min");
        bst.put(3., "3333333333");
        bst.put(2.8, "xxxxx");
        bst.put(6.8, "xxxxx");
    }
}
