package predator;

import java.util.ArrayList;
import prey.Prey;
import processing.core.*;
import quadTree.*;
import sketch.Sketch;

public class BoidPredator extends Predator {

    public boolean eat = false;

    public BoidPredator(PApplet sk, float x, float y, PredatorGenotype gen) {
        super(sk, x, y, gen);
    }

    @Override
    public void move(QuadTree qPreys, QuadTree qPredators, QuadTree qFoodL) {
        float foodWeight = 0.0f;
        if (energy <= gen.eMax * 0.50) {
            foodWeight = 100;
        }

        PVector hunt = hunt(qPreys);   // Cohesion
        PVector sep = separate(qPredators);   // Separation
        PVector ali = align(qPredators);      // Alignment
        PVector coh = cohesion(qPredators);   // Cohesion
        // Arbitrarily weight these forces
        hunt.mult(foodWeight);
        sep.mult(1.5f);
        ali.mult(1.0f);
        coh.mult(1.0f);
        // Add the force vectors to acceleration
        applyForce(hunt);
        applyForce(sep);
        applyForce(ali);
        applyForce(coh);
        // Update velocity
        acceleration.limit(maxforce);
        velocity.add(acceleration);
        // Limit speed
        velocity.limit(maxspeed);
        position.add(velocity);
        // Reset accelertion to 0 each cycle
        acceleration.mult(0);
        // CheckBorders
        borders();
    }

    // Separation
    // Method checks for nearby boids and steers away
    public PVector separate(QuadTree qPredators) {
        float desiredseparation = 25.0f;
        PVector steer = new PVector(0, 0);
        int count = 0;
        // For every boid in the system, check if it's too close
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> predators = new ArrayList();
        qPredators.query(range, predators);
        for (Point p : predators) {
            Predator other = (Predator) p.obj;
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
            steer.mult(maxspeed);
            steer.sub(velocity);
            steer.limit(maxforce);
        }
        return steer;
    }

    // Alignment
    // For every nearby boid in the system, calculate the average velocity
    public PVector align(QuadTree qPredators) {
        float neighbordist = 50;
        PVector sum = new PVector(0, 0);
        int count = 0;
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> predators = new ArrayList();
        qPredators.query(range, predators);
        for (Point p : predators) {
            Predator other = (Predator) p.obj;
            float d = PVector.dist(position, other.position);
            if ((d > 0) && (d < neighbordist)) {
                sum.add(other.velocity);
                count++;
            }
        }
        if (count > 0) {
            sum.div((float) count);
            sum.normalize();
            sum.mult(maxspeed);
            PVector steer = PVector.sub(sum, velocity);
            steer.limit(maxforce);
            return steer;
        } else {
            return new PVector(0, 0);
        }
    }

    // Cohesion
    // For the average position (i.e. center) of all nearby boids, calculate steering vector towards that position
    public PVector cohesion(QuadTree qPredators) {
        float neighbordist = 50;
        PVector sum = new PVector(0, 0);   // Start with empty vector to accumulate all positions
        int count = 0;
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> predators = new ArrayList();
        qPredators.query(range, predators);
        for (Point p : predators) {
            Predator other = (Predator) p.obj;
            float d = PVector.dist(position, other.position);
            if ((d > 0) && (d < neighbordist)) {
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

    // Hunt
    // For the average position (i.e. center) of all nearby boids, calculate steering vector towards that position
    public PVector hunt(QuadTree qPreys) {
        PVector sum = new PVector(0, 0);   // Start with empty vector to accumulate all positions
        int count = 0;
        Circle range = new Circle(position.x, position.y, gen.vision);
        ArrayList<Point> preys = new ArrayList();
        qPreys.query(range, preys);
        for (Point p : preys) {
            Prey other = (Prey) p.obj;
            sum.add(other.position); // Add position
            count++;
            break;
        }
        if (count > 0) {
            sum.div(count);
            return seek(sum);  // Steer towards the position
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
        desired.mult(maxspeed);
        // Steering = Desired minus Velocity
        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxforce);  // Limit to maximum steering force
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
