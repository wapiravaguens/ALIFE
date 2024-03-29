package food;

import processing.core.PApplet;
import processing.core.PVector;

public class Food {

    public PApplet sk;
    public static float size = 20;

    public PVector position;
    public float currentLevel;
    public float growBackRate;
    public float maxCapacity;

    public Food(PApplet sk, float x, float y, float maxCapacity, float growBackRate, float currentLevel) {
        this.sk = sk;
        this.position = new PVector(x, y);
        this.maxCapacity = maxCapacity;
        this.growBackRate = growBackRate;
        this.currentLevel = currentLevel;
    }

    public void render() {
        sk.pushStyle();
        sk.pushMatrix();
        sk.rectMode(PApplet.CENTER);
        sk.stroke(128);
        sk.fill(255, 255, 0, PApplet.map(currentLevel, 0, maxCapacity, 0, 255));
        sk.circle(position.x, position.y, size);
        sk.popMatrix();
        sk.popStyle();
    }

    public void update() {
        currentLevel = currentLevel + growBackRate;
        currentLevel = PApplet.min(currentLevel, maxCapacity);
    }

}
