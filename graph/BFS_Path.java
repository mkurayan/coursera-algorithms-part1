import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class BFS_Path {
    private int root;
    private int[] path;
    private boolean[] isChecked;

    public BFS_Path(Graph G, int s) {
        root = s;
        path = new int[G.V()];
        isChecked = new boolean[G.V()];

        bfs(G, s);
    }

    public boolean hasPathTo(int v) {
        return isChecked[v];
    }

    Iterable<Integer> pathTo(int v) {
        Stack<Integer> stack = new Stack<>();

        if(hasPathTo(v)) {
            for(int p = v; p != root; p = path[p] ) {
                stack.push(p);
            }

            stack.push(root);
        }

        return stack;
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<>();
        q.Enqueue(s);
        isChecked[s] = true;
        path[s] = s;

        while (q.Count() > 0) {
            int v = q.Dequeue();

            for(int w: G.adj(v)) {
                if(!isChecked[w]) {
                    isChecked[w] = true;
                    path[w] = v;
                    q.Enqueue(w);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(4);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(1, 3);
        G.addEdge(2, 3);

        BFS_Path bfs = new BFS_Path(G, 0);

        Iterable<Integer> path =  bfs.pathTo(3);
    }
}
