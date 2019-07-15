package prey;

import food.Food;
import java.util.Collections;
import predator.Predator;
import java.util.ArrayList;
import java.util.Random;
import processing.core.*;
import quadTree.*;
import sketch.Sketch;

public class BoidPrey extends Prey {

    public boolean eat = false;


    public BoidPrey(PApplet sk, float x, float y, PreyGenotype gen) {
        super(sk, x, y, gen);
    }

    @Override
    public void move(QuadTree qPreys, QuadTree qPredators, QuadTree qFoodL) {
        float fFood = 0.5f;
        if (energy <= gen.eMax * 0.30 || eat) {
            fFood = 30.0f;
            eat = true;
            if (energy > gen.eMax * 0.70) {
                eat = false;
            }
        }
        
        PVector avop = avoidPredators(qPredators); //Run
        PVector food = seekFood(qFoodL); // SeekFood
        PVector sep = separate(qPreys);   // Separation
        PVector ali = align(qPreys);      // Alignment
        PVector coh = cohesion(qPreys);   // Cohesion  
        
        avop.mult(100.0f);
        food.mult(fFood);
        sep.mult(gen.fSep);
        ali.mult(gen.fAli);
        coh.mult(gen.fCoh);
        
        applyForce(avop);
        applyForce(food);
        applyForce(sep);
        applyForce(ali);
        applyForce(coh);

        // Update velocity
        velocity.add(acceleration);
        // Limit speed
        velocity.limit(gen.maxspeed);
        position.add(velocity);
        // Reset accelertion to 0 each cycle
        acceleration.mult(0);
        // CheckBorders
        borders();
    }

    // Run
    // Method for run
    public PVector avoidPredators(QuadTree qPredators) {
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> predators = new ArrayList();
        qPredators.query(range, predators);
        PVector steerTotal = new PVector(0, 0);
        for (Point p : predators) {
            Predator predator = (Predator) p.obj;
            float d = PVector.dist(position, predator.position);
            if ((d > 0) && (d < gen.vision * 2)) {
                PVector steer = new PVector(); // creates vector for steering
                steer.set(PVector.sub(position, predator.position)); // steering vector points away from
                steer.mult(1 / PApplet.sq(PVector.dist(position, predator.position)));
                steerTotal.add(steer);
            }
        }
        return steerTotal.limit(gen.maxforce);
    }

    // Separation
    // Method checks for nearby boids and steers away
    public PVector separate(QuadTree qPreys) {
        float desiredseparation = 25.0f;
        PVector steer = new PVector(0, 0);
        int count = 0;
        // For every boid in the system, check if it's too close
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> preys = new ArrayList();
        qPreys.query(range, preys);
        for (Point p : preys) {
            Prey other = (Prey) p.obj;
            float d = PVector.dist(position, other.position);
            // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
            if ((d > 0) && (d < desiredseparation)) {
                // Calculate vector pointing away from neighbor
                PVector diff = PVector.sub(position, other.position);
                diff.normalize();
                diff.div(d);        // Weight by distance
                steer.add(diff);
                count++;            // Keep track of how many
            }
        }
        // Average -- divide by how many
        if (count > 0) {
            steer.div((float) count);
        }
        // As long as the vector is greater than 0
        if (steer.mag() > 0) {
            // Implement Reynolds: Steering = Desired - Velocity
            steer.normalize();
            steer.mult(gen.maxspeed);
            steer.sub(velocity);
            steer.limit(gen.maxforce);
        }
        return steer;
    }

    // Alignment
    // For every nearby boid in the system, calculate the average velocity
    public PVector align(QuadTree qPreys) {
        float neighbordist = 50;
        PVector sum = new PVector(0, 0);
        int count = 0;
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> preys = new ArrayList();
        qPreys.query(range, preys);
        for (Point p : preys) {
            Prey other = (Prey) p.obj;
            float d = PVector.dist(position, other.position);
            if ((d > 0) && (d < gen.vision)) {
                sum.add(other.velocity);
                count++;
            }
        }
        if (count > 0) {
            sum.div((float) count);
            sum.normalize();
            sum.mult(gen.maxspeed);
            PVector steer = PVector.sub(sum, velocity);
            steer.limit(gen.maxforce);
            return steer;
        } else {
            return new PVector(0, 0);
        }
    }

    // Cohesion
    // For the average position (i.e. center) of all nearby boids, calculate steering vector towards that position
    public PVector cohesion(QuadTree qPreys) {
        float neighbordist = 50;
        PVector sum = new PVector(0, 0);   // Start with empty vector to accumulate all positions
        int count = 0;
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> preys = new ArrayList();
        qPreys.query(range, preys);
        for (Point p : preys) {
            Prey other = (Prey) p.obj;
            float d = PVector.dist(position, other.position);
            if ((d > 0) && (d < gen.vision)) {
                sum.add(other.position); // Add position
                count++;
            }
        }
        if (count > 0) {
            sum.div(count);
            return seek(sum);  // Steer towards the position
        } else {
            return new PVector(0, 0);
        }
    }

    // seekFood
    // For the average position (i.e. center) of all nearby boids, calculate steering vector towards that position
    public PVector seekFood(QuadTree qFoodL) {
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> foodL = new ArrayList();
        qFoodL.query(range, foodL);
        int count = 0;
        PVector bestFood = new PVector(0, 0);
        float best = 0;
        Collections.shuffle(foodL);
        for (Point p : foodL) {
            Food food = (Food) p.obj;
            if (food.currentLevel > best) {
                best = food.currentLevel;
                bestFood = food.position;
                count++;
            }
        }
        if (count > 0) {
            return seek(bestFood);  // Steer towards the position
        } else {
            return new PVector(0, 0);
        }
    }

    public void applyForce(PVector force) {
        // We could add mass here if we want A = F / M
        acceleration.add(force);
    }

    // A method that calculates and applies a steering force towards a target
    // STEER = DESIRED MINUS VELOCITY
    public PVector seek(PVector target) {
        PVector desired = PVector.sub(target, position);  // A vector pointing from the position to the target
        // Normalize desired and scale to maximum speed
        desired.normalize();
        desired.mult(gen.maxspeed);
        // Steering = Desired minus Velocity
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(gen.maxforce);  // Limit to maximum steering force
        return steer;
    }

    public void borders() {
        if (position.x < size / 2) {
            position.x = Sketch.width_;
        }
        if (position.y < size / 2) {
            position.y = Sketch.height_;
        }
        if (position.x > Sketch.width_ + size / 2) {
            position.x = size / 2;
        }
        if (position.y > Sketch.height_ + size / 2) {
            position.y = size / 2;
        }
    }

}
