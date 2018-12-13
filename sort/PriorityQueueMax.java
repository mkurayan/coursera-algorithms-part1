public class PriorityQueueMax<Item extends Comparable<Item>> {
    private Item[] arr;
    private int itemCount;

    public PriorityQueueMax() {
        arr = (Item[])(new Comparable[2]);
        itemCount = 0;
    }

    public void insert(Item value) {
        if(itemCount >= arr.length - 1) {
            resizeArr(arr.length * 2);
        }

        arr[++itemCount] = value;

        swim(itemCount);
    }

    public int count() {
        return itemCount;
    }

    public Item max() {
        return arr[1];
    }

    public Item delMax() {
        Item max = arr[1];

        arr[1] = arr[itemCount];
        arr[itemCount] = null;
        itemCount--;

        sink(1);

        if(itemCount < arr.length / 4) {
            resizeArr(arr.length / 2);
        }

        return max;
    }

    private void resizeArr(int newCapacity) {
        Item[] newArr = (Item[])(new Comparable[newCapacity]);

        for(int i = 1; i <= itemCount; i++) {
            newArr[i] = arr[i];
        }

        arr = newArr;
    }

    private void sink(int k) {
        while(k * 2 <= itemCount) {
            k = k * 2;

            if(k < itemCount && less(arr[k], arr[k + 1])) {
                k++;
            }

            if(less(arr[k], arr[k / 2])) {
                break;
            }

            exch(k, k / 2);
        }
    }

    private void swim(int k) {
        while(k > 1 && less(arr[k / 2], arr[k])) {
            exch(k, k/2);
            k = k / 2;
        }
    }

    private void exch(int i, int j) {
        Item swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    private boolean less(Item v, Item w) {
        return v.compareTo(w) < 0;
    }


    public static void main(String[] args) {
        PriorityQueueMax<Integer> p = new PriorityQueueMax<>();

        p.insert(1);
        p.insert(3);
        p.insert(4);
        p.insert(2);
        p.insert(5);

        int n = p.count();
        for(int i = 0; i < n; i++) {
            System.out.print(p.delMax() + " ");
        }
    }

}
