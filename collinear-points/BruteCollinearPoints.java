import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;


public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                // Array contains a repeated point.
                throw new IllegalArgumentException();
            }
        }

        Bag<LineSegment> bag = new Bag<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {

                        Point p = points[i];
                        double slope = p.slopeTo(points[j]);
                        if (p.slopeTo(points[k]) == slope
                                && p.slopeTo(points[m]) == slope) {
                            bag.add(new LineSegment(p, points[m]));
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
        return lineSegments;
    }
}
