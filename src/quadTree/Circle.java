package quadTree;

public class Circle {

    public float x;
    public float y;
    public float r;
    public float rSquared;

    public Circle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        rSquared = r * r;
    }

    boolean contains(Point point) {
        // check if the point is in the circle by checking if the euclidean distance of
        // the point and the center of the circle if smaller or equal to the radius of
        // the circle
        if (point == null) {
            return false;
        }
        float d = (float) Math.sqrt( Math.pow((point.x - x), 2) + (float) Math.pow((point.y - y), 2) );
        return d <= r + point.size / 2;
    }

    boolean intersects(Rectangle range) {

        float xDist = Math.abs(range.x - x);
        float yDist = Math.abs(range.y - y);
        float w = range.w;
        float h = range.h;

        float edges = (float) Math.pow((xDist - w), 2) + (float) Math.pow((yDist - h), 2);

        // no intersection
        if (xDist > (r + w) || yDist > (r + h)) {
            return false;
        }

        // intersection within the circle
        if (xDist <= w || yDist <= h) {
            return true;
        }

        // intersection on the edge of the circle
        return edges <= rSquared;
    }

}
