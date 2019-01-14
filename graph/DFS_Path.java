import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class DFS_Path {
    private int root;
    private int[] path;
    private boolean[] isChecked;

    public DFS_Path(Graph G, int s) {
        root = s;
        path = new int[G.V()];
        isChecked = new boolean[G.V()];

        dfs(G, s);
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

    private void dfs(Graph G, int s) {
        isChecked[s] = true;
        for(int v : G.adj(s)) {
            if(!isChecked[v]) {
                path[v] = s;

                dfs(G, v);
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(4);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(1, 3);
        G.addEdge(2, 3);

        DFS_Path dfs = new DFS_Path(G, 0);

        Iterable<Integer> path =  dfs.pathTo(3);
    }
}
