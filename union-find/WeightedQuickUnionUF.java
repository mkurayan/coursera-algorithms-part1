public class WeightedQuickUnionUF {
    public static void main(String[] args) {

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);

        uf.Union(0, 1);
        uf.Union(2, 3);
        uf.Union(0, 3);

        uf.Union(5, 6);
        uf.Union(5, 7);
        uf.Union(5, 8);
        uf.Union(8, 9);


        uf.Union(2, 8);
    }

    private int[] arr;
    private int[] size;

    public WeightedQuickUnionUF(int N) {
        arr = new int[N];
        size = new int[N];
        for(int i = 0; i < N; i++) {
            arr[i] = i;
            size[i] = 1;
        }
    }

    public void Union(int p, int q) {
        int qRoot =  Root(q);
        int pRoot = Root(p);

        if(qRoot == pRoot)
            return;

        if(size[qRoot] > size[pRoot]) {
            arr[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            arr[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
    }

    public boolean IsConnected(int p, int q) {
        return Root(p) == Root(q);
    }

    private int Root(int p) {
        int root = p;
        while (root != arr[p]) {
            root = arr[p];
        }

        while(p != root) {
            int pNew = arr[p];
            arr[p] = root;
            p = pNew;
        }

        return root;
    }
}
