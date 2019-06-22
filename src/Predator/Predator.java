package Predator;

import processing.core.PApplet;
import processing.core.PVector;

public class Predator {

    public PApplet sk;
    public PVector position;

    public Predator(PApplet sk, float x, float y) {
        this.sk = sk;
        this.position = new PVector(x, y);
    }

    public void render() {
        sk.pushMatrix();
        sk.translate((int) position.x, (int) position.y);
        sk.rectMode(PApplet.CENTER);
        sk.fill(0, 0, 255);
        sk.rect(0, 0, 10, 10);
        sk.popMatrix();
    }
}
