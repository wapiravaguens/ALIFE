package aquarium;

import food.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import quadTree.*;
import prey.*;
import predator.*;
import processing.core.PApplet;
import processing.core.PVector;
import turingMorph.TuringMorph;

public class Aquarium {

    public PApplet sk;
    public Rectangle limit;

    public ArrayList<Prey> preys;
    public QuadTree qPreys;

    public ArrayList<Predator> predators;
    public QuadTree qPredators;

    public FoodGenerator foodGenerator;
    public QuadTree qFoodL;

    public static TuringMorph turingMorph;

    public Aquarium(PApplet sk) {
        this.sk = sk;
        this.turingMorph = new TuringMorph(sk, 100, 100, 2000);
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
        for (Prey prey : preys) {
            prey.update(qPreys, qPredators, qFoodL);
        }
        for (Predator predator : predators) {
            predator.update(qPreys, qPredators, qFoodL);
        }
        preysEatFood();
        preyReproduction();
        predatorReproduction();
        predatorsEatPreys();
        preysPredatorsDeath();
        updateQTrees();
    }

    public void make() {
        for (int i = 0; i < 300; i++) {
            preys.add(new BoidPrey(sk, sk.random(0, sk.width), sk.random(0, sk.height), PreyGenotype.random()));
        }
        for (int i = 0; i < 30; i++) {
            predators.add(new BoidPredator(sk, sk.random(0, sk.width), sk.random(0, sk.height), PredatorGenotype.random()));
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
            Circle range = new Circle(prey.position.x, prey.position.y, prey.gen.vision);
            ArrayList<Point> foodL = new ArrayList();
            qFoodL.query(range, foodL);
            for (Point p : foodL) {
                Food food = (Food) p.obj;
                float dist = PVector.dist(prey.position, food.position);
                if (dist > 0 && dist < (prey.size / 2 + food.size / 2)) {
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

    public void preyReproduction() {
        ListIterator<Prey> iter = preys.listIterator();
        while (iter.hasNext()) {
            Prey prey1 = iter.next();
            Circle range = new Circle(prey1.position.x, prey1.position.y, prey1.gen.vision);
            ArrayList<Point> preys_ = new ArrayList();
            qPreys.query(range, preys_);
            for (Point p : preys_) {
                Prey prey2 = (Prey) p.obj;
                if (prey1.sex != prey2.sex) {
                    if (prey1.age >= prey1.gen.adultAge && prey2.age >= prey2.gen.adultAge) {
                        if (prey1.energy >= prey1.gen.eRepro && prey2.energy >= prey2.gen.eRepro) {
                            float dist = PVector.dist(prey1.position, prey2.position);
                            if (dist > 0 && dist < (prey1.size / 2 + prey2.size / 2)) {
                                prey1.reproduction();
                                prey2.reproduction();
                                iter.add(new BoidPrey(sk, prey1.position.x, prey1.position.y, prey1.gen));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void predatorReproduction() {
        ListIterator<Predator> iter = predators.listIterator();
        while (iter.hasNext()) {
            Predator predator1 = iter.next();
            Circle range = new Circle(predator1.position.x, predator1.position.y, predator1.gen.vision);
            ArrayList<Point> predators_ = new ArrayList();
            qPredators.query(range, predators_);
            for (Point p : predators_) {
                Predator predator2 = (Predator) p.obj;
                if (predator1.sex != predator2.sex) {
                    if (predator1.age >= predator1.gen.adultAge && predator2.age >= predator2.gen.adultAge) {
                        if (predator1.energy >= predator1.gen.eRepro && predator2.energy >= predator2.gen.eRepro) {
                            float dist = PVector.dist(predator1.position, predator2.position);
                            if (dist > 0 && dist < (predator1.size / 2 + predator2.size / 2)) {
                                predator1.reproduction();
                                predator2.reproduction();
                                iter.add(new BoidPredator(sk, predator1.position.x, predator1.position.y, predator1.gen));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
