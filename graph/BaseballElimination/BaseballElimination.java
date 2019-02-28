import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseballElimination {
    private final int nOfTeams;
    private Team[] t;
    private HashMap<String, Integer> teamsMap;
    private int[][] g;

    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(filename);

        nOfTeams = in.readInt();
        t = new Team[nOfTeams];
        g = new int[nOfTeams][nOfTeams];
        teamsMap = new HashMap<>();

        for (int i = 0; i < nOfTeams; i++) {
            t[i] = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt());
            teamsMap.put(t[i].name, i);

            for (int j = 0; j < nOfTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() {
        return nOfTeams;
    }

    public Iterable<String> teams() {
        return teamsMap.keySet();
    }

    public int wins(String team) {
        checkTeam(team);

        return t[teamsMap.get(team)].wins;
    }

    public int losses(String team) {
        checkTeam(team);

        return t[teamsMap.get(team)].losses;
    }

    public int remaining(String team) {
        checkTeam(team);

        return t[teamsMap.get(team)].remainingGames;
    }

    public int against(String team1, String team2)  {
        checkTeam(team1);
        checkTeam(team2);

        return g[teamsMap.get(team1)][teamsMap.get(team2)];
    }

    public boolean isEliminated(String team) {
        checkTeam(team);

        return certOfElimination(team).size() > 0;
    }

    public Iterable<String> certificateOfElimination(String team)  {
        checkTeam(team);

        List<String> teams = certOfElimination(team);

        return teams.size() > 0 ? teams : null;
    }

    private List<String> certOfElimination(String team) {
        checkTeam(team);

        ArrayList<String> teams = new ArrayList<>();

        if (mathematicallyEliminated(team) != null) {
            teams.add(mathematicallyEliminated(team));
        } else {
            FordFulkerson ff = fordFulkersonForTeam(team);

            for (int i = 0; i < t.length; i++) {
                if (t[i].name != team && ff.inCut(i)) {
                    teams.add(t[i].name);
                }
            }
        }

        return teams;
    }

    private String mathematicallyEliminated(String team) {
        Team currentTeam = t[teamsMap.get(team)];

        for (int i = 0; i < t.length; i++) {
            if (t[i] != currentTeam && t[i].wins > currentTeam.wins + currentTeam.remainingGames) {
                return t[i].name;
            }
        }

        return null;
    }

    private FordFulkerson fordFulkersonForTeam(String team) {
        ArrayList<Game> games = getOtherTeamsRemainingGames(team);

        //  teams, games, source, target
        FlowNetwork f = new FlowNetwork(games.size() + t.length + 2);

        int sourceVertex = games.size() + t.length;
        int targetVertex = sourceVertex + 1;

        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            int gameVertex = t.length + i;

            f.addEdge(new FlowEdge(sourceVertex, gameVertex, game.gamesLeft));
            f.addEdge(new FlowEdge(gameVertex, teamsMap.get(game.teamA.name),  Double.POSITIVE_INFINITY));
            f.addEdge(new FlowEdge(gameVertex, teamsMap.get(game.teamB.name),  Double.POSITIVE_INFINITY));
        }

        Team wTeam = t[teamsMap.get(team)];

        for (int i = 0; i < t.length; i++) {
            Team vTeam = t[i];
            if (vTeam != wTeam) {
                f.addEdge(new FlowEdge(i, targetVertex, wTeam.wins + wTeam.remainingGames - vTeam.wins));
            }
        }

        return new FordFulkerson(f, f.V() - 2, f.V() - 1);
    }

    private ArrayList<Game> getOtherTeamsRemainingGames(String team) {
        int index = teamsMap.get(team);

        ArrayList<Game> games = new ArrayList<>();
        for (int i = 0; i < g.length; i++) {
            if (i == index) {
                continue;
            }

            for (int j = i; j < g.length; j++) {
                if (j == index) {
                    continue;
                }

                if (g[i][j] > 0) {
                    games.add(new Game(t[i], t[j], g[i][j]));
                }
            }
        }

        return games;
    }

    private void checkTeam(String team) {
        if (team == null || !teamsMap.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    private class Team {
        private String name;
        private int wins;
        private int losses;
        private int remainingGames;

        Team(String n, int w, int l, int r) {
            name = n;
            wins = w;
            losses = l;
            remainingGames = r;
        }
    }

    private class Game {
        private Team teamA;
        private Team teamB;
        private int gamesLeft;

        Game(Team aTeam, Team bTeam, int gLeft) {
            teamA = aTeam;
            teamB = bTeam;

            gamesLeft = gLeft;
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("C:\\Users\\maxim\\Downloads\\baseball\\teams54.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
