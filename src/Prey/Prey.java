package Prey;

import processing.core.PApplet;
import processing.core.PVector;

public class Prey {

    public PApplet sk;
    public PVector position;
    public float move;

    public Prey(PApplet sk, float x, float y) {
        this.sk = sk;
        this.position = new PVector(x, y);
        move = sk.random(-2, 2);
    }

    public void render() {
        update();
        sk.pushMatrix();
        sk.translate((int) position.x, (int) position.y);
        sk.rectMode(PApplet.CENTER);
        sk.rect(0, 0, 10, 10);
        sk.popMatrix();
    }

    public void update() {
        //position.add(new PVector(move, move));
    }

}
