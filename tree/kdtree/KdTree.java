import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Bag;

public class KdTree {
    private static final double PEN_RADIUS = 0.01;
    private static final boolean H_SPLIT = false;
    private static final boolean V_SPLIT = true;
    private static final boolean LB = false;
    private static final boolean RT = true;

    private final RectHV rootRect;
    private Node root;
    private int s;

    public KdTree() {
        s = 0;
        root = null;

        rootRect = new RectHV(0, 0, 1, 1);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return s;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!contains(p)) {
            root = insert(p, root, V_SPLIT);
            root.rect = rootRect;
            s++;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return contains(p, root, true) != null;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Bag<Point2D> bag = new Bag<>();

        range(root, rect, bag);

        return bag;
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            return null;
        }

        return nearest(p, root, root).p;
    }

    private Node nearest(Point2D p, Node nearest, Node node) {
        if (node == null) {
            return nearest;
        }

        if (node.p.equals(p)) {
            return node;
        }

        if (node.p.distanceSquaredTo(p) < nearest.p.distanceSquaredTo(p)) {
            nearest = node;
        }

        if (node.lb == null && node.rt == null) {
            return nearest;
        }

        if (node.rt == null) {
            nearest = findNearest(p, nearest, node.lb);
        } else if (node.lb == null) {
            nearest = findNearest(p, nearest, node.rt);
        } else {
            Node first;
            Node second;
            if (node.lb.rect.distanceSquaredTo(p) < node.rt.rect.distanceSquaredTo(p)) {
                first = node.lb;
                second = node.rt;
            } else {
                first = node.rt;
                second = node.lb;
            }

            nearest = findNearest(p, nearest, first);
            nearest = findNearest(p, nearest, second);
        }

        return nearest;
    }

    private Node findNearest(Point2D p, Node nearest, Node node) {
        double dDist = nearest.p.distanceSquaredTo(p);
        if (node.rect.distanceSquaredTo(p) < dDist) {
            Node sNearest = nearest(p, nearest, node);

            if (sNearest.p.distanceSquaredTo(p) < dDist) {
                nearest = sNearest;
            }
        }

        return nearest;
    }

    // draw all points to standard draw
    public void draw() {
       draw(root, V_SPLIT);
    }

    private void draw(Node node, boolean orientation) {
        if (node == null) {
            return;
        }

        draw(node.p, node.rect, orientation);
        draw(node.lb, !orientation);
        draw(node.rt, !orientation);
    }

    private void draw(Point2D p, RectHV rect, boolean split) {
        StdDraw.setPenRadius();
        if (split == V_SPLIT) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(PEN_RADIUS);
        StdDraw.point(p.x(), p.y());
    }

    private void range(Node node, RectHV rect, Bag<Point2D> bag) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.p)) {
            bag.add(node.p);
        }

        if (node.lb != null && (node.lb.rect.intersects(rect) ||  rect.intersects(node.lb.rect))) {
            range(node.lb, rect, bag);
        }

        if (node.rt != null && (node.rt.rect.intersects(rect) || rect.intersects(node.rt.rect))) {
            range(node.rt, rect, bag);
        }
    }

    private Node contains(Point2D p, Node node, boolean split) {
        if (node == null) {
            return null;
        }

        if (p.equals(node.p)) {
            return node;
        }

        double pCoordinate = split ?  p.x() : p.y();
        double nCoordinate = split ? node.p.x() : node.p.y();

        if (pCoordinate < nCoordinate) {
            return contains(p, node.lb, !split);
        } else {
            return contains(p, node.rt, !split);
        }
    }

    private Node insert(Point2D p, Node node, boolean split) {
        if (node == null) {
            return new Node(p);
        }

        double pCoordinate = split ?  p.x() : p.y();
        double nCoordinate = split ? node.p.x() : node.p.y();

        if (pCoordinate < nCoordinate) {
            node.lb = insert(p, node.lb, !split);

            if (node.lb.rect == null) {
                node.lb.rect = getSubRect(node.rect, node.p, split, LB);
            }
        } else {
            node.rt = insert(p, node.rt, !split);

            if (node.rt.rect == null) {
                node.rt.rect = getSubRect(node.rect, node.p, split, RT);
            }
        }

        return node;
    }


    private RectHV getSubRect(RectHV rect, Point2D p, boolean split, boolean part) {
        double xmin = split == V_SPLIT && part == RT ? p.x() : rect.xmin();
        double xmax = split == V_SPLIT && part == LB ? p.x() : rect.xmax();

        double ymin = split == H_SPLIT && part == RT ? p.y() : rect.ymin();
        double ymax = split == H_SPLIT && part == LB ? p.y() : rect.ymax();

        return new RectHV(xmin, ymin, xmax, ymax);
    }

    private static final class Node {
        private final Point2D p;      // the point

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D point) {
            p = point;
        }
    }
}
