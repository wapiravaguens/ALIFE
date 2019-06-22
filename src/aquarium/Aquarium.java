package aquarium;

import food.*;
import java.util.ArrayList;
import quadTree.*;
import prey.*;
import predator.*;
import processing.core.PApplet;

public class Aquarium {

    public PApplet sk;
    public Rectangle limit;

    public ArrayList<Prey> fishes;
    public QuadTree qFishes;

    public ArrayList<Predator> predators;
    public QuadTree qPredators;

    public FoodGenerator foodGenerator;
    public QuadTree qFoodL;

    public Aquarium(PApplet sk) {
        this.sk = sk;
        limit = new Rectangle(sk.width / 2, sk.height / 2, sk.width, sk.height);

        this.foodGenerator = new FoodGenerator(sk);
        this.fishes = new ArrayList<>();
        this.predators = new ArrayList<>();
        
        make();
    }

    public void render() {
        update();
        foodGenerator.render();
        for (Prey fish : fishes) {
            //fish.render();
        }
        for (Predator predator : predators) {
            //predator.render();
        }
    }

    public void update() {
        updateQTrees();
    }

    public void make() {
        for (int i = 0; i < 1000; i++) {
            fishes.add(new Prey(sk, sk.random(0, sk.width), sk.random(0, sk.height)));
        }
        for (int i = 0; i < 1000; i++) {
            predators.add(new Predator(sk, sk.random(0, sk.width), sk.random(0, sk.height)));
        }
    }

    public void updateQTrees() {
        qFishes = new QuadTree(sk, limit, 25);
        for (Prey fish : fishes) {
            qFishes.insert(new Point(fish));
        }
        //qFishes.show();
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
}
