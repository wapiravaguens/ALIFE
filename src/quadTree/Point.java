package quadTree;

import food.Food;
import predator.Predator;
import prey.Prey;

public class Point {

    public float x;
    public float y;
    public Object obj;

    public Point(float x, float y, Object data) {
        this.x = x;
        this.y = y;
        this.obj = data;
    }

    public Point(Prey data) {
        this.x = data.position.x;
        this.y = data.position.y;
        this.obj = data;
    }

    public Point(Predator data) {
        this.x = data.position.x;
        this.y = data.position.y;
        this.obj = data;
    }

    public Point(Food data) {
        this.x = data.position.x;
        this.y = data.position.y;
        this.obj = data;
    }

}