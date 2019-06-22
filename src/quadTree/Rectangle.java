package quadTree;

public class Rectangle {

    public float x;
    public float y;
    public float w;
    public float h;

    public Rectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    boolean contains(Point point) {
        return (point.x >= x - w
                && point.x <= x + w
                && point.y >= y - h
                && point.y <= y + h);
    }

    boolean intersects(Rectangle range) {
        return !(range.x - range.w > x + w
                || range.x + range.w < x - w
                || range.y - range.h > y + h
                || range.y + range.h < y - h);
    }
}
