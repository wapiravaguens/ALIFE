package aquarium;

import food.*;
import java.util.*;
import quadTree.*;
import prey.*;
import predator.*;
import processing.core.*;
import sketch.Sketch;

public class Aquarium {

    public PApplet sk;
    public Rectangle limit;

    public ArrayList<Prey> preys;
    public QuadTree qPreys;

    public ArrayList<Predator> predators;
    public QuadTree qPredators;

    public FoodGenerator foodGenerator;
    public QuadTree qFoodL;
    
    public static ArrayList<ArrayList<PVector>> data = new ArrayList<>();

    public Aquarium(PApplet sk) {
        this.sk = sk;
        limit = new Rectangle(Sketch.width_ / 2, Sketch.height_ / 2, Sketch.width_, Sketch.height_);

        // Chart data arrays initialization
        for (int i = 0; i < 12; i++) {
            this.data.add(new ArrayList<PVector>());
        }

        this.foodGenerator = new FoodGenerator(sk);
        this.preys = new ArrayList<>();
        this.predators = new ArrayList<>();

        make();
        updateQTrees();
    }

    public void make() {
        for (int i = 0; i < 125; i++) {
            //preys.add(new BoidPrey(sk, sk.random(0, Sketch.width_), sk.random(0, Sketch.height_), PreyGenotype.random()));
            preys.add(new BoidPrey(sk, Sketch.width_ / 2, Sketch.height_ / 2, PreyGenotype.random()));
        }
        for (int i = 0; i < 25; i++) {
            predators.add(new BoidPredator(sk, sk.random(0, Sketch.width_), sk.random(0, Sketch.height_), PredatorGenotype.random()));
            //predators.add(new BoidPredator(sk, 3*Sketch.width_ / 4, Sketch.height_ / 2, PredatorGenotype.random()));
        }
    }

    public void render() {
        foodGenerator.render();
        for (Prey fish : preys) {
            fish.render();
        }
        for (Predator predator : predators) {
            predator.render();
        }
    }

    public void update() {
        for (Prey prey : preys) {
            prey.update(qPreys, qPredators, qFoodL);
        }
        for (Predator predator : predators) {
            predator.update(qPreys, qPredators, qFoodL);
        }
        foodGenerator.update();
        preysEatFood();
        predatorsEatPreys();
        preyReproduction();
        predatorReproduction();
        preysPredatorsDeath();
        updateQTrees();
        updateChartsData();
    }

    public void updateQTrees() {
        qPreys = new QuadTree(sk, limit, 25);
        for (Prey prey : preys) {
            qPreys.insert(new Point(prey));
        }
        //qPreys.show();
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
            Circle range = new Circle(predator.position.x, predator.position.y, predator.gen.vision);
            ArrayList<Point> preys_ = new ArrayList();
            qPreys.query(range, preys_);
            for (Point p : preys_) {
                Prey prey = (Prey) p.obj;
                float dist = PVector.dist(predator.position, prey.position);
                if (dist > 0 && dist < (predator.size / 2 + prey.size / 2)) {
                    predator.eat(prey.energy);
                    preys.remove(prey);
                    break;
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
                    break;
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
                                PreyGenotype[] offsprings = PreyGenotype.offsprings(prey1.gen, prey1.gen);
                                iter.add(new BoidPrey(sk, prey1.position.x, prey1.position.y, offsprings[0]));
                                //iter.add(new BoidPrey(sk, prey1.position.x, prey1.position.y, offsprings[1]));
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
                                PredatorGenotype[] offsprings = PredatorGenotype.offsprings(predator1.gen, predator2.gen);
                                iter.add(new BoidPredator(sk, predator1.position.x, predator1.position.y, offsprings[0]));
                                //iter.add(new BoidPredator(sk, predator1.position.x, predator1.position.y, offsprings[1]));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void updateChartsData() {
        if (Sketch.time % 60 == 0) {
            float data_[] = new float[12];
            for (Prey prey : preys) {
                data_[0] += prey.gen.eMax;
                data_[1] += prey.gen.eLife;
                data_[2] += prey.gen.finalSize;
                data_[3] += prey.gen.vision;
                data_[4] += prey.gen.maxspeed;
                data_[5] += prey.gen.maxforce;
            }
            for (Predator predator : predators) {
                data_[6] += predator.gen.eMax;
                data_[7] += predator.gen.eLife;
                data_[8] += predator.gen.finalSize;
                data_[9] += predator.gen.vision;
                data_[10] += predator.gen.maxspeed;
                data_[11] += predator.gen.maxforce;
            }
            for (int i = 0; i < 6; i++) {
                if (preys.size() > 0) {
                    data_[i] /= preys.size();
                    data.get(i).add(new PVector(Sketch.time / 60, data_[i]));
                }
                if (predators.size() > 0) {
                    data_[i + 6] /= predators.size();
                    data.get(i + 6).add(new PVector(Sketch.time / 60, data_[i + 6]));
                }
            }
        }
    }
}
