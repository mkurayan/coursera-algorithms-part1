import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private Step g;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Step> initialFrontier = new MinPQ<>(new ByPriorityComparator());
        initialFrontier.insert(new Step(initial, null, 0, 0));

        MinPQ<Step> twinFrontier = new MinPQ<>(new ByPriorityComparator());
        twinFrontier.insert(new Step(initial.twin(), null, 0, 0));

        while (true) {
            Step goal = processFrontier(initialFrontier);

            if (goal != null) {
                g = goal;
                break;
            }

            if (processFrontier(twinFrontier) != null) {
                // twin board solved, this mean that initial board unsolvable.
                break;
            }
        }
    }

    private Step processFrontier(MinPQ<Step> frontier) {
        Step current = frontier.delMin();

        if (current.board.isGoal()) {
            return current;
        }

        for (Board next : current.board.neighbors()) {
            if (current.previousStep != null
                    && current.previousStep.board.equals(next)) {
                continue;
            }

            int stepsSoFar = current.stepsSoFar + 1;
            int priority = next.manhattan() + stepsSoFar;

            frontier.insert(new Step(next, current, priority, stepsSoFar));
        }

        return null;
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return g != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (g == null) {
            return -1;
        }

        return g.stepsSoFar;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (g == null) {
            return null;
        }

        Stack<Board> s = new Stack<>();

        Step current = g;
        s.push(current.board);

        while (current.previousStep != null) {
            current = current.previousStep;
            s.push(current.board);
        }

        return s;
    }

    private class Step {
        private final Board board;
        private final int  priority;
        private final int stepsSoFar;
        private final Step previousStep;

        Step(Board current, Step prev, int p, int s) {
            board = current;
            previousStep = prev;
            priority = p;
            stepsSoFar = s;
        }
    }

    private class ByPriorityComparator implements Comparator<Step> {
        public int compare(Step a, Step b) {
            if (a.priority > b.priority) {
                return 1;
            } else if (a.priority < b.priority) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
