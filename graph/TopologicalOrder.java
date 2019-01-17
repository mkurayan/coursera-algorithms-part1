import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class TopologicalOrder {
    private boolean[] marked;
    private boolean[] cycle;
    private Queue<Integer> queue;

    public TopologicalOrder(Digraph G) {
        marked = new boolean[G.V()];
        cycle = new boolean[G.V()];
        queue = new Queue<>();

        for(int v = 0; v < G.V(); v++) {
            if(!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int s) {
        marked[s] = true;
        cycle[s] = true;

        for(int v : G.adj(s)) {
            if(cycle[v]) {
                throw new IllegalArgumentException("Cyclic dependency in graph");
            }

            if(!marked[v]) {
                dfs(G, v);
            }
        }

        cycle[s] = false;
        queue.enqueue(s);
    }

    public Iterable<Integer> postorder() {
        return queue;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(1, 3);
        G.addEdge(2, 3);
        G.addEdge(4, 0);

        //G.addEdge(3, 0); // Cyclic dependency.

        TopologicalOrder t = new TopologicalOrder(G);

        Iterable<Integer> path =  t.postorder();

    }
}
