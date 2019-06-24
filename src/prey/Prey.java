package prey;

import aquarium.Aquarium;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import quadTree.Circle;
import quadTree.Point;
import quadTree.QuadTree;
import sketch.Sketch;
import turingMorph.TuringMorph;

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
    public float maxforce;    // Maximum steering force
    public float maxspeed;    // Maximum speed

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
        this.maxspeed = 3.0f;
        this.maxforce = 0.05f;
    }

    public void render() {
        sk.pushStyle();

        sk.pushMatrix();
        float theta = velocity.heading();
        sk.translate(position.x, position.y);
        sk.scale(sk.map(size, gen.initSize, gen.finalSize, gen.initSize / 50, gen.finalSize / 50), sk.map(size, gen.initSize, gen.finalSize, gen.initSize / 50, gen.finalSize / 50));
        sk.translate(-position.x, -position.y);
        if (theta < PApplet.PI / 2 && theta > -PApplet.PI / 2) {
            sk.scale(-1.0f, 1.0f);
            sk.translate(-position.x, position.y);
        } else {
            sk.translate(position.x, position.y);
        }
        
        sk.imageMode(PApplet.CENTER);
        sk.fill(0, 0, 255);
        sk.image(img, 0, 0);
        sk.popMatrix();
        
        sk.pushMatrix();
        sk.translate(position.x, position.y);
//        sk.circle(0, 0, size);
//        sk.rotate(velocity.heading() + sk.PI/2);
//        sk.triangle(0, -15, -5, 0, 5, 0);
//        sk.rectMode(PApplet.CENTER);
//        sk.rect(0, 0, 10, 10);
        sk.popMatrix();
        
        sk.popStyle();
        info();
        //update();
    }

    public void update(QuadTree preys, QuadTree predators, QuadTree foodL) {
//        Circle range = new Circle(position.x, position.y, gen.vision);
//        ArrayList<Point> predators_ = new ArrayList();
//        predators.query(range, predators_);
//        for (Point p : predators_) {
//            sk.pushStyle();
//            sk.fill(0, 255, 0);
//            sk.circle(p.x, p.y, p.size * 2);
//            sk.popStyle();
//        }
        move(preys, predators, foodL);
        metabolism();
    }

    public void move(QuadTree preys, QuadTree predators, QuadTree foodL) {
        // Update velocity
        velocity.add(acceleration);
        // Limit speed
        velocity.limit(maxspeed);
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
        float energyPlus = PApplet.min(eFood, gen.eMax - energy);
        energy = energy + energyPlus;
        return energyPlus;
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
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, size, 8);
        sk.fill(0, 255, 0);
        sk.rect(position.x - size / 2, position.y - size / 2 - 10, PApplet.map(energy, 0, gen.eMax, 0, size), 8);
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
