import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int count;

    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        count = 0;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (count == arr.length) {
            resize(arr.length * 2);
        }

        arr[count] = item;

        count++;
    }

    public Item dequeue() {
        if (count == 0) {
            throw new NoSuchElementException();
        }

        if (arr.length / 4 == count) {
            resize(arr.length / 2);
        }

        int dequeueItemIndex = StdRandom.uniform(count);

        Item t = arr[dequeueItemIndex];
        arr[dequeueItemIndex] = arr[count - 1];
        arr[count - 1] = null;

        count--;
        return t;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Item sample() {
        if (count == 0) {
            throw new NoSuchElementException();
        }

        return arr[StdRandom.uniform(count)];
    }

    public Iterator<Item> iterator() {
        Item[] randomArr = (Item[]) new Object[count];
        for (int i = 0; i < count; i++) {
            randomArr[i] = arr[i];
        }

        StdRandom.shuffle(randomArr);

        return new ArrayIterator<>(randomArr);
    }


    private void resize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];

        for (int i = 0; i < count; i++) {
            newArr[i] = arr[i];
        }

        arr = newArr;
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator<Item> implements Iterator<Item> {
        private Item[] arr;
        private int current;

        ArrayIterator(Item[] randomArray) {
            arr = randomArray;
            current = 0;
        }

        public boolean hasNext() {
            return current < arr.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return arr[current++];
        }
    }


    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();

        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        q.enqueue("d");


        StdOut.printf("first iteration: ");
        for (String item : q) {
            StdOut.printf(item);
        }


        StdOut.printf("  second iteration:  ");
        for (String item : q) {
            StdOut.printf(item);
        }


        StdOut.printf("  dequeue:  ");
        StdOut.printf(q.dequeue());
        StdOut.printf(q.dequeue());
        StdOut.printf(q.dequeue());
        StdOut.printf(q.dequeue());
    }
}
