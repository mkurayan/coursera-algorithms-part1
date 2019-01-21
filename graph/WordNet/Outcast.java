public class Outcast {
    private final WordNet wordnet;
    public Outcast(WordNet w) {
        wordnet = w;
    }

    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < nouns.length; i++) {
            if (!wordnet.isNoun(nouns[i])) {
                throw new IllegalArgumentException();
            }
        }

        int[] distance = new int[nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    distance[i] += wordnet.distance(nouns[i], nouns[j]);
                }
            }
        }

        int outcastIndex = 0;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] > distance[outcastIndex]) {
                outcastIndex = i;
            }
        }

        return nouns[outcastIndex];
    }
}
