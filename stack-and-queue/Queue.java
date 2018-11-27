public class Queue<Item> {
    public static void main(String[] args) {
        Queue<String> q = new Queue<>();

        q.Enqueue("a");
        q.Enqueue("b");
        q.Enqueue("c");
        q.Enqueue("d");

        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());

        q.Enqueue("e");
        System.out.print(q.Dequeue());

        q.Enqueue("f");
        System.out.print(q.Dequeue());

        q.Enqueue("j");
        q.Enqueue("h");
        q.Enqueue("i");
        q.Enqueue("g");


        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());
        System.out.print(q.Dequeue());
    }

    private Item[] arr;
    private int head;
    private int tail;
    private int count;

    public Queue() {
        arr = (Item[])new Object[2];
        count = 0;
    }

    public void Enqueue(Item item) {
        if(count == arr.length) {
            resize(arr.length * 2);
        }

        count++;

        arr[head] = item;
        head = ++head % arr.length;
    }

    public Item Dequeue() {
        if(arr.length / 4 == count) {
            resize(arr.length / 2);
        }

        count--;

        Item t = arr[tail];
        arr[tail] = null; // in order to avoid loitering.

        tail = ++tail % arr.length;
        return t;
    }

    public int Count() {
        return count;
    }

    private void resize(int newSize) {
        Item[] newArr = (Item[])new Object[newSize];

        for(int i = 0; i < count; i++) {
            newArr[i] = arr[(tail + i) % arr.length ];
        }

        arr = newArr;
        tail = 0;
        head = count;
    }
}
