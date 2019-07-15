package quadTree;

import java.util.ArrayList;
import processing.core.PApplet;

public class QuadTree {

    public PApplet sk;

    public Rectangle boundary;
    public int capacity;
    public ArrayList<Point> points;
    public boolean divided;
    
    public QuadTree northeast;
    public QuadTree northwest;
    public QuadTree southeast;
    public QuadTree southwest;

    public QuadTree(PApplet sk, Rectangle boundary, int capacity) {
        this.sk = sk;
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.divided = false;
    }

    public void subdivide() {
        float x = boundary.x;
        float y = boundary.y;
        float w = boundary.w / 2;
        float h = boundary.h / 2;

        Rectangle ne = new Rectangle(x + w, y - h, w, h);
        northeast = new QuadTree(sk, ne, capacity);
        Rectangle nw = new Rectangle(x - w, y - h, w, h);
        northwest = new QuadTree(sk, nw, capacity);
        Rectangle se = new Rectangle(x + w, y + h, w, h);
        southeast = new QuadTree(sk, se, capacity);
        Rectangle sw = new Rectangle(x - w, y + h, w, h);
        southwest = new QuadTree(sk, sw, capacity);

        divided = true;
    }

    public boolean insert(Point point) {
        if (!boundary.contains(point)) {
            return false;
        }
        if (points.size() < capacity) {
            points.add(point);
            return true;
        }
        if (!divided) {
            subdivide();
        }
        return (northeast.insert(point) || northwest.insert(point)
                || southeast.insert(point) || southwest.insert(point));
    }

    public void query(Circle range, ArrayList<Point> found) {
        if (!range.intersects(boundary)) {
            return;
        }
        for (Point p : points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }
        if (divided) {
            northwest.query(range, found);
            northeast.query(range, found);
            southwest.query(range, found);
            southeast.query(range, found);
        }
    }

    public void show() {
        sk.pushStyle();
        sk.stroke(255);
        sk.strokeWeight(3);
        sk.noFill();
        sk.rectMode(PApplet.CENTER);
        sk.rect(boundary.x, boundary.y, boundary.w * 2, boundary.h * 2);
        if (divided) {
            northeast.show();
            northwest.show();
            southeast.show();
            southwest.show();
        }
        sk.popStyle();
    }
}
