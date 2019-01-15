import edu.princeton.cs.algs4.Graph;

public class ConnectedComponents {
    private boolean[] isChecked;
    private int[] componentId;
    private int componentsCount;

    public ConnectedComponents(Graph G) {
        isChecked = new boolean[G.V()];
        componentId = new int[G.V()];

        for(int v = 0; v < G.V(); v++) {
            if(!isChecked[v]) {
                componentsCount++;
                dfs(G, v);
            }
        }
    }

    boolean connected(int v, int w) {
        return componentId[v] == componentId[w];
    }

    // number of connected components
    int count() {
        return componentsCount;
    }

    // component identifier for v
    int id(int v) {
        return componentId[v];
    }

    private void dfs(Graph G, int s) {
        isChecked[s] = true;
        componentId[s] = componentsCount;

        for(int v: G.adj(s)) {
            if(!isChecked[v]) {
                dfs(G, v);
            }
        }
    }
}
