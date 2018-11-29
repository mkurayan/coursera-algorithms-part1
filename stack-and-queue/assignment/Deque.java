import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<>();
        newNode.item = item;

        if (last == null) {
            last = newNode;
        }

        if (first != null) {
            newNode.prev = first;
            first.next = newNode;
        }

        first = newNode;
        size++;
    }


    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<>();
        newNode.item = item;

        if (first == null) {
            first = newNode;
        }

        if (last != null) {
            newNode.next = last;
            last.prev = newNode;
        }

        last = newNode;
        size++;
    }


    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Node<Item> oldFirst = first;

        if (first.prev != null) {
            first = first.prev;
            first.next = null;
        } else {
            first = null;
            last = null;
        }

        size--;

        return oldFirst.item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Node<Item> oldLast = last;

        if (last.next != null) {
            last = last.next;
            last.prev = null;
        } else {
            first = null;
            last = null;
        }

        size--;

        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    // helper linked list class
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }


    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        ListIterator(Node<Item> h) {
            current = h;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.prev;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<>();

        d.addFirst("b");
        d.addFirst("a");
        d.addLast("c");

        StdOut.printf(d.removeFirst());
        StdOut.printf(d.removeLast());
        StdOut.printf(d.removeFirst());
    }
}
