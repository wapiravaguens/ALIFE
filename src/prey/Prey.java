package prey;

import affineTransformation.AffineTransformation;
import aquarium.Aquarium;
import java.util.Random;
import processing.core.*;
import quadTree.QuadTree;
import sketch.Sketch;

public class Prey {

    public PApplet sk;

    public PImage img;
    public PreyGenotype gen;

    // Life
    public int age;
    public float energy;
    public float size;
    public boolean sex;

    // Boid
    public PVector position;
    public PVector velocity;
    public PVector acceleration;

    public Prey(PApplet sk, float x, float y, PreyGenotype gen) {
        this.sk = sk;
        this.gen = gen;
        this.img = sk.loadImage("prey.png");
        Aquarium.turingMorph.paint(img, gen.color1, gen.color2, gen.param);

        // Life
        this.age = 0;
        this.energy = gen.eMax;
        this.size = gen.initSize;
        this.sex = new Random().nextBoolean();

        // Boids
        this.position = new PVector(x, y);
        this.velocity = new PVector(sk.random(-1.0f, 1.0f), sk.random(-1.0f, 1.0f));
        this.acceleration = new PVector(0, 0);
    }

    public void render() {
        sk.pushStyle();
        sk.pushMatrix();
        // Scale and ShearX
        AffineTransformation.transformPrey(sk, this);
        // Orientation
        float theta = velocity.heading();
        if (theta < PApplet.PI / 2 && theta > -PApplet.PI / 2) {
            sk.scale(-1.0f, 1.0f);
            sk.translate(-position.x, position.y);
        } else {
            sk.translate(position.x, position.y);
        }
        // Draw image
        sk.imageMode(PApplet.CENTER);
        sk.image(img, 0, 0);
        sk.popMatrix();
        sk.popStyle();

        info();
    }

    public void update(QuadTree qPreys, QuadTree qPredators, QuadTree qFoodL) {
        move(qPreys, qPredators, qFoodL);
        metabolism();
    }

    public void move(QuadTree preys, QuadTree predators, QuadTree foodL) {
        // Update velocity
        velocity.add(acceleration);
        // Limit speed
        velocity.limit(gen.maxspeed);
        position.add(velocity);
        // Reset accelertion to 0 each cycle
        acceleration.mult(0);
    }

    public void metabolism() {
        size = sk.map(sk.min(age, gen.adultAge), 0, gen.adultAge, gen.initSize, gen.finalSize);
        age++;
        energy = energy - gen.eLife;
    }

    public float eat(float eFood) {
        float energyPlus = sk.min(eFood, gen.eMax - energy);
        energy = energy + energyPlus;
        return energyPlus;
    }

    public void reproduction() {
        energy = energy - gen.eRepro;
    }

    public void info() {
        if (Sketch.showEnergy) {
            energyBar();
        }
        if (Sketch.showVision) {
            visionCircle();
        }
    }

    public void energyBar() {
        sk.pushStyle();
        sk.fill(255, 0, 0);
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, size, 5);
        sk.fill(0, 255, 0);
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, sk.map(energy, 0, gen.eMax, 0, size), 5);
        sk.popStyle();
    }

    public void visionCircle() {
        sk.pushStyle();
        sk.noFill();
        sk.stroke(159, 213, 209);
        sk.circle(position.x, position.y, gen.vision * 2);
        sk.popStyle();
    }

}
