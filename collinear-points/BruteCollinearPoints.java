import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Array contains a repeated point");
                }
            }
        }

        Point[] ps = points.clone();

        Arrays.sort(ps);

        for (int i = 0; i < ps.length - 1; i++) {
            if (ps[i].compareTo(ps[i + 1]) == 0) {
                // Array contains a repeated point.
                throw new IllegalArgumentException();
            }
        }

        Bag<LineSegment> bag = new Bag<>();
        for (int i = 0; i < ps.length; i++) {
            for (int j = i + 1; j < ps.length; j++) {
                for (int k = j + 1; k < ps.length; k++) {
                    for (int m = k + 1; m < ps.length; m++) {

                        Point p = ps[i];
                        double slope = p.slopeTo(ps[j]);
                        if (p.slopeTo(ps[k]) == slope && p.slopeTo(ps[m]) == slope) {
                            bag.add(new LineSegment(p, ps[m]));
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[bag.size()];

        int index = 0;
        for (LineSegment lineSegment : bag) {
            lineSegments[index++] = lineSegment;
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}
