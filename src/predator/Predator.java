package predator;

import processing.core.PApplet;
import processing.core.PVector;

public class Predator {

    public PApplet sk;
    public PVector position;
    public PVector velocity;

    public Predator(PApplet sk, float x, float y) {
        this.sk = sk;
        this.position = new PVector(x, y);
        this.velocity = new PVector(sk.random(-0.01f, 0.01f), sk.random(-0.01f, 0.01f));
    }

    public void render() {
        update();
        sk.pushStyle();
        sk.pushMatrix();
        sk.translate(position.x, position.y);
        sk.rotate(velocity.heading() + sk.PI/2);
        sk.rectMode(PApplet.CENTER);
        sk.fill(255, 0, 0);
        sk.triangle(0, -15, -5, 0, 5, 0);
        //sk.rect(0, 0, 10, 10);
        sk.popMatrix();
        sk.popStyle();
    }

    public void update() {
        position.add(velocity);
    }
}
