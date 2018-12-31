public class BST <Key extends Comparable<Key>, Value> {
    private Node root;

    public void put(Key key, Value val) {
        root = put(key, val, root);
    }

    public Value get(Key key) {
        Node n =  get(key, root);

        return n == null ? null : n.value;
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

    public int size(Key key) {
        Node n = get(key, root);

        return size(n);
    }

    // number of keys that less than given key
    public int rank(Key key) {
        return rank(key, root);
    }

    // maximum key in tree that less or equal to the current key
    public Key floor(Key key) {
        Node n = floor(key, root);

        return n == null ? null : n.key;
    }

    public Key ceiling(Key key) {
        Node n = ceiling(key, root);

        return n == null ? null : n.key;
    }

    private int rank(Key key, Node node) {
        if(node == null) {
            return 0;
        }

        if(key.compareTo(node.key) < 0) {
            return rank(key, node.left);
        }

        if(key.compareTo(node.key) > 0) {
            return size(node.left) + 1 + rank(key, node.right);
        }

        return size(node.left);
    }

    private Node floor(Key key, Node node) {
        if(node == null) {
            return null;
        }

        if(key.compareTo(node.key) < 0) {
            return floor(key, node.left);
        }
        else if(key.compareTo(node.key) > 0) {
            Node f = floor(key, node.right);

            return f == null ? node : f;
        }

        return node;
    }

    private Node ceiling(Key key, Node node) {
        if(node == null) {
            return null;
        }

        if(key.compareTo(node.key) < 0) {
            Node f = ceiling(key, node.left);

            return f == null ? node : f;
        }
        else if(key.compareTo(node.key) > 0) {
            return ceiling(key, node.right);
        }

        return node;
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

    private Node get(Key key, Node node) {
        if(node == null) {
            return null;
        }

        if(key.compareTo(node.key) < 0) {
            return get(key, node.left);
        } else if(key.compareTo(node.key) > 0) {
            return get(key, node.right);
        } else {
            return node;
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

        node.size = size(node.left) + size(node.right) + 1;

        return node;
    }

    private int size(Node node) {
        if(node == null) {
            return 0;
        }

        return node.size;
    }

    private class Node {
        private final Key key;
        private Value value;
        private int size;

        private Node left;
        private Node right;

        private Node(Key k, Value val) {
            key = k;
            value = val;
            size = 1;
        }
    }

    public static void main(String[] args) {
        BST<Double, String> bst = new BST<>();

        bst.put(4., "some val");
        bst.put(2., "wefwfwf");
        bst.put(6., "aaaaa");
        bst.put(5., "ooooo");
        bst.put(7., "Max");
        bst.put(1., "Min");
        bst.put(3., "3333333333");
        bst.put(2.8, "xxxxx");
        bst.put(6.8, "xxxxx");

        System.out.println(bst.get(1.));
        System.out.println(bst.min());

        System.out.println(bst.get(7.));
        System.out.println(bst.max());

        System.out.println("size of key 4 is 7 " + (bst.size(4.) == 7));
        System.out.println("size of key 2 is 3 " + (bst.size(2.) == 3));

        System.out.println("size of key 2 is 3 " + (bst.size(2.) == 3));

        System.out.println("floor of 2.9: " + bst.floor(2.9));
        System.out.println("ceiling of 2.9: " + bst.ceiling(2.9));

        System.out.println("floor of 6.5: " + bst.floor(6.5));
        System.out.println("ceiling of 6.5: " + bst.ceiling(6.5));

        System.out.println("rank of 6.5: " + bst.rank(6.5));

        bst.delete(4.);
    }
}
