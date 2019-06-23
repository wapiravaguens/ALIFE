package prey;

import java.util.Random;
import processing.core.PApplet;
import processing.core.PVector;
import sketch.Sketch;

public class Prey {

    public PApplet sk;

    public PreyGenotype gen;

    // Life
    public int age;
    public float energy;
    public float size;
    public boolean sex;

    // Boid
    public PVector position;
    public PVector velocity;

    public Prey(PApplet sk, float x, float y, PreyGenotype gen) {
        this.sk = sk;
        this.gen = gen;

        // Life
        this.age = 0;
        this.energy = gen.eMax;
        this.size = gen.initSize;
        this.sex = new Random().nextBoolean();

        // Boids
        this.position = new PVector(x, y);
        this.velocity = new PVector(sk.random(-0.2f, 0.2f), sk.random(-0.2f, 0.2f));
    }

    public void render() {
        sk.pushStyle();
        sk.pushMatrix();
        sk.translate(position.x, position.y);
        sk.rotate(velocity.heading() + sk.PI / 2);
        sk.rectMode(PApplet.CENTER);
        sk.fill(0, 0, 255);
        sk.circle(0, 0, size);
        //sk.triangle(0, -15, -5, 0, 5, 0);
        //sk.rect(0, 0, 10, 10);
        sk.popMatrix();
        sk.popStyle();
        info();
        update();
    }

    public void update() {
        position.add(velocity);
        metabolism();
    }

    public void metabolism() {
        energy = energy - gen.eLife;
    }

    public float eat(float eFood) {
        float energyPlus = PApplet.min(eFood, gen.eMax - energy);
        energy = energy + energyPlus;
        return energyPlus;
    }

    public void info() {
        if (Sketch.showEnergy) {
            energyBar();
        }
    }

    public void energyBar() {
        sk.pushStyle();
        sk.fill(255, 0, 0);
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, size, 8);
        sk.fill(0, 255, 0);
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, PApplet.map(energy, 0, gen.eMax, 0, size), 8);
        sk.popStyle();
    }

}
