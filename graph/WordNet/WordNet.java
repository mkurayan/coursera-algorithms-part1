import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private final HashMap<String, ArrayList<Integer>> dictionary;
    private final List<String> al;

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new IllegalArgumentException();
        }

        if (hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In sIn = new In(synsets);
        In hIn = new In(hypernyms);

        dictionary = new HashMap<>();
        al = new ArrayList<>();

        int counter = 0;
        while (!sIn.isEmpty()) {
            String line = sIn.readLine();

            String[] fields = line.split("\\,");

            String[] nouns =  fields[1].split(" ");

            for (String noun : nouns) {
                if (!dictionary.containsKey(noun)) {
                    dictionary.put(noun, new ArrayList<>());
                }

                dictionary.get(noun).add(Integer.parseInt(fields[0]));
            }

            al.add(fields[1]);
            counter++;
        }

        Digraph g = new Digraph(counter);

        while (!hIn.isEmpty()) {
            String line = hIn.readLine();
            String[] fields = line.split("\\,");

            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);

                g.addEdge(v, w);
            }
        }

        int rootCount = 0;
        for (int i = 0; i < g.V(); i++) {
            if (!g.adj(i).iterator().hasNext()) {
                rootCount++;
            }
        }

        if (rootCount != 1) {
            // The input to the constructor does not correspond to a rooted DAG.
            throw new IllegalArgumentException();
        }

        if (!new DAGValidaor(g).isAcyclic()) {
            // The input to the constructor does not correspond to a rooted DAG.
            throw new IllegalArgumentException();
        }


        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return dictionary.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return dictionary.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException();
        }

        if (!isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return sap.length(dictionary.get(nounA), dictionary.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA)) {
            throw new IllegalArgumentException();
        }

        if (!isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int anc = sap.ancestor(dictionary.get(nounA), dictionary.get(nounB));

        if (anc == -1) {
            return null;
        }

        return al.get(anc);
    }

    private class DAGValidaor {
        private final boolean[] marked;
        private final boolean[] inProgress;

        private boolean hasCycle = false;

        DAGValidaor(Digraph g) {
            marked = new boolean[g.V()];
            inProgress = new boolean[g.V()];

            for (int i = 0; i < g.V(); i++) {
                dfs(g, i);
            }

        }

        public boolean isAcyclic() {
            return !hasCycle;
        }

        private void dfs(Digraph g, int s) {
            marked[s] = true;
            inProgress[s] = true;

            for (int v : g.adj(s)) {
                if (inProgress[v]) {
                    hasCycle = true;
                }

                if (!marked[v]) {
                    dfs(g, v);
                }
            }

            inProgress[s] = false;
        }
    }
}
