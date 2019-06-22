package prey;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Prey {

    public PApplet sk;
    public PVector position;
    public PVector velocity;

    public Prey(PApplet sk, float x, float y) {
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
        sk.fill(0, 0, 255);
        sk.triangle(0, -15, -5, 0, 5, 0);
        //sk.rect(0, 0, 10, 10);
        sk.popMatrix();
        sk.popStyle();
    }

    public void update() {
        position.add(velocity);
    }

}
