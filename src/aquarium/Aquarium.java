package aquarium;

import food.*;
import java.util.ArrayList;
import java.util.Iterator;
import quadTree.*;
import prey.*;
import predator.*;
import processing.core.PApplet;
import processing.core.PVector;

public class Aquarium {

    public PApplet sk;
    public Rectangle limit;

    public ArrayList<Prey> preys;
    public QuadTree qPreys;

    public ArrayList<Predator> predators;
    public QuadTree qPredators;

    public FoodGenerator foodGenerator;
    public QuadTree qFoodL;

    public Aquarium(PApplet sk) {
        this.sk = sk;
        limit = new Rectangle(sk.width / 2, sk.height / 2, sk.width, sk.height);

        this.foodGenerator = new FoodGenerator(sk);
        this.preys = new ArrayList<>();
        this.predators = new ArrayList<>();

        make();
        updateQTrees();
    }

    public void render() {
        foodGenerator.render();
        for (Prey fish : preys) {
            fish.render();
        }
        for (Predator predator : predators) {
            predator.render();
        }
        //update();
    }

    public void update() {
        foodGenerator.update();
        for (Prey fish : preys) {
            fish.update();
        }
        for (Predator predator : predators) {
            predator.update();
        }
        preysEatFood();
        predatorsEatPreys();
        preysPredatorsDeath();
        updateQTrees();
    }

    public void make() {
        for (int i = 0; i < 800; i++) {
            preys.add(new Prey(sk, sk.random(0, sk.width), sk.random(0, sk.height), PreyGenotype.random()));
        }
        for (int i = 0; i < 200; i++) {
            predators.add(new Predator(sk, sk.random(0, sk.width), sk.random(0, sk.height), PredatorGenotype.random()));
        }
    }

    public void updateQTrees() {
        qPreys = new QuadTree(sk, limit, 25);
        for (Prey prey : preys) {
            qPreys.insert(new Point(prey));
        }
        //qpreys.show();
        qPredators = new QuadTree(sk, limit, 25);
        for (Predator predator : predators) {
            qPredators.insert(new Point(predator));
        }
        //qPredators.show();
        qFoodL = new QuadTree(sk, limit, 25);
        for (Food food : foodGenerator.foodL) {
            qFoodL.insert(new Point(food));
        }
        //qFoodL.show();
    }

    public void predatorsEatPreys() {
        for (Predator predator : predators) {
            Circle range = new Circle(predator.position.x, predator.position.y, 65);
            ArrayList<Point> preys_ = new ArrayList();
            qPreys.query(range, preys_);
            for (Point p : preys_) {
                Prey prey = (Prey) p.obj;
                float dist = PVector.dist(predator.position, prey.position);
                if (dist > 0 && dist < (predator.size / 2 + prey.size / 2)) {
                    predator.eat(prey.energy);
                    preys.remove(prey);
                }
            }
        }
    }

    public void preysEatFood() {
        for (Prey prey : preys) {
            Circle range = new Circle(prey.position.x, prey.position.y, 65);
            ArrayList<Point> foodL = new ArrayList();
            qFoodL.query(range, foodL);
            for (Point p : foodL) {
                Food food = (Food) p.obj;
                float dist = PVector.dist(prey.position, food.position);
                if (dist > 0 && dist < (prey.size / 2 + food.d / 2)) {
                    float energyPlus = prey.eat(food.currentLevel);
                    food.currentLevel = food.currentLevel - energyPlus;
                }
            }
        }
    }

    public void preysPredatorsDeath() {
        for (Iterator<Prey> iterator = preys.iterator(); iterator.hasNext();) {
            Prey prey = iterator.next();
            if (prey.energy <= 0 || prey.age > prey.gen.expectedAge) {
                iterator.remove();
            }
        }
        for (Iterator<Predator> iterator = predators.iterator(); iterator.hasNext();) {
            Predator predator = iterator.next();
            if (predator.energy <= 0 || predator.age > predator.gen.expectedAge) {
                iterator.remove();
            }
        }
    }
}
