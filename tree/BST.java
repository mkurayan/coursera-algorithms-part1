public class BST <Key extends Comparable<Key>, Value> {
    private Node root;

    public void put(Key key, Value val) {
        root = put(key, val, root);
    }

    public Value get(Key key) {
        return get(key, root);
    }

    public void delete(Key key) {
        root = delete(key, root);
    }

    public Value min() {
        if(root == null) {
            return null;
        }

        return min(root).value;
    }

    public Value max(){
        if(root == null) {
            return null;
        }

        Node n = root;
        while(n.right != null) {
            n = n.right;
        }

        return n.value;
    }

    private Node min(Node n) {
        while(n.left != null) {
            n = n.left;
        }

        return n;
    }

    private Node delete(Key key, Node node) {
        if(node == null) {
            return null;
        }

        if(key.compareTo(node.key) < 0) {
            node.left = delete(key, node.left);
        } else if(key.compareTo(node.key) > 0) {
            node.right = delete(key, node.right);
        } else {
            if(node.left == null && node.right == null) {
                return null;
            }

            if(node.left == null) {
                return node.right;
            }

            if(node.right == null){
                return node.left;
            }

            Node min = min(node.right);
            min.right = deleteMin(node.right);
            min.left = node.left;

            return min;
        }

        return node;
    }

    private Node deleteMin(Node node) {
        if(node.left == null) {
            return node.right;
        }

        node.left = deleteMin(node.left);

        return node;
    }

    private Value get(Key key, Node node) {
        if(node == null) {
            return null;
        }

        if(key.compareTo(node.key) < 0) {
            return get(key, node.left);
        } else if(key.compareTo(node.key) > 0) {
            return get(key, node.right);
        } else {
            return node.value;
        }
    }

    private Node put(Key key, Value val, Node node) {
        if(node == null) {
            return new Node(key, val);
        }

        if(key.compareTo(node.key) < 0) {
            node.left = put(key, val, node.left);
        } else if(key.compareTo(node.key) > 0) {
            node.right = put(key, val, node.right);
        } else {
            node.value = val;
        }

        return node;
    }

    private class Node {
        private final Key key;
        private Value value;

        private Node left;
        private Node right;

        private Node(Key k, Value val) {
            key = k;
            value = val;
        }
    }

    public static void main(String[] args) {
        BST<Integer, String> bst = new BST<>();

        bst.put(4, "some val");
        bst.put(2, "wefwfwf");
        bst.put(6, "aaaaa");
        bst.put(5, "ooooo");
        bst.put(7, "Max");
        bst.put(1, "Min");
        bst.put(3, "3333333333");

        System.out.println(bst.get(1));
        System.out.println(bst.min());

        System.out.println(bst.get(7));
        System.out.println(bst.max());

        bst.delete(4);
    }
}
