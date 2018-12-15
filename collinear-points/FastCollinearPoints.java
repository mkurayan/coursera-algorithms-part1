import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.MergeX;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private static final int P_COUNT = 3;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Bag<LineSegment> bag = new Bag<>();

        // Sort points in natural order.
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            // Copy points in natural order to sub array,
            // which will be used for sorting by slope.
            Point[] sPoints =  new Point[points.length];
            for (int j = 0; j < points.length; j++) {
                sPoints[j] = points[j];
            }

            Point p = points[i];

            MergeX.sort(sPoints, p.slopeOrder());

            // Obviously that current point is first in sorted array,
            // because it's slope equals to NEGATIVE_INFINITY
            int n = 0;
            double slope = Double.NEGATIVE_INFINITY;

            for (int j = 1; j < sPoints.length; j++) {
                if (p.slopeTo(sPoints[j]) == slope) {
                    n++;
                } else {
                    if (n >= P_COUNT && p.compareTo(sPoints[j - n]) < 0) {
                        bag.add(new LineSegment(p, sPoints[j - 1]));
                    }
                    n = 1;
                    slope = p.slopeTo(sPoints[j]);
                }
            }

            if (n >= P_COUNT && p.compareTo(sPoints[sPoints.length - n]) < 0
                    ) {
                bag.add(new LineSegment(p, sPoints[sPoints.length - 1]));
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
