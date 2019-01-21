import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }

        digraph = new Digraph(g);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(new BreadthFirstDirectedPaths(digraph, v), new BreadthFirstDirectedPaths(digraph, w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(new BreadthFirstDirectedPaths(digraph, v), new BreadthFirstDirectedPaths(digraph, w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkForNull(v);
        checkForNull(w);

        return length(new BreadthFirstDirectedPaths(digraph, v), new BreadthFirstDirectedPaths(digraph, w));
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkForNull(v);
        checkForNull(w);

        return ancestor(new BreadthFirstDirectedPaths(digraph, v), new BreadthFirstDirectedPaths(digraph, w));
    }

    private void checkForNull(Iterable<Integer> itr) {
        if (itr == null) {
            throw new IllegalArgumentException();
        }

        for (Integer i : itr) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private int length(BreadthFirstDirectedPaths vPath, BreadthFirstDirectedPaths wPath) {
        int min = Integer.MAX_VALUE;
        for (int s = 0; s < digraph.V(); s++) {
            if (vPath.hasPathTo(s) && wPath.hasPathTo(s)) {
                int distToV = vPath.distTo(s);
                int distToW = wPath.distTo(s);

                if (distToV + distToW < min) {
                    min = distToV + distToW;
                }
            }
        }

        if (min == Integer.MAX_VALUE) {
            return -1;
        }

        return min;
    }

    private int ancestor(BreadthFirstDirectedPaths vPath, BreadthFirstDirectedPaths wPath) {
        int min = Integer.MAX_VALUE;
        int anc = -1;

        for (int s = 0; s < digraph.V(); s++) {
            if (vPath.hasPathTo(s) && wPath.hasPathTo(s)) {
                int distToV = vPath.distTo(s);
                int distToW = wPath.distTo(s);

                if (distToV + distToW < min) {
                    min = distToV + distToW;
                    anc = s;
                }
            }
        }

        return anc;
    }
}
