package food;

import java.util.ArrayList;
import plants.LSystem;
import processing.core.PApplet;
import sketch.Sketch;

public class FoodGenerator {

    public PApplet sk;

    public ArrayList<LSystem> plants;
    public int[] winter = {255, 255, 255};
    public int[] summer = {94, 255, 0};

    public ArrayList<Food> foodL;
    public boolean currentStation;
    public float gSummer, gWinter, timeStation;

    public FoodGenerator(PApplet sk) {
        this.sk = sk;
        this.plants = new ArrayList<>();
        this.foodL = new ArrayList<>();
        this.currentStation = true;

        // Initialization
        this.gSummer = 2.5f;
        this.gWinter = 1.0f;
        this.timeStation = 20;
        make();
    }

    public void render() {
        for (Food food : foodL) {
            food.render();
        }
        for (LSystem plant : plants) {
            plant.render();
        }
    }

    public void update() {
        for (Food food : foodL) {
            food.update();
        }
        if (Sketch.time % (timeStation * 60) == 0) {
            // Update LSystem
            int[] cNorth = currentStation ? summer : winter;
            int[] cSouth = currentStation ? winter : summer;
            for (LSystem plant : plants) {
                if (plant.y < Sketch.height_ / 2) {
                    plant.color = cNorth;
                } else {
                    plant.color = cSouth;
                }

            }

            // Update Food
            float gNorth = currentStation ? gSummer : gWinter;
            float gSouth = currentStation ? gWinter : gSummer;
            for (Food food : foodL) {
                food.currentLevel = 25;
                if (food.position.y < Sketch.height_ / 2) {
                    food.growBackRate = gNorth;
                } else {
                    food.growBackRate = gSouth;
                }
            }

            currentStation = !currentStation;
        }
    }

    public void make() {
        // North
        plants.add(LSystem.plant1(sk, (Sketch.width_ / 4), (Sketch.height_ / 4), winter));
        plants.add(LSystem.plant2(sk, (3 * Sketch.width_ / 4), (Sketch.height_ / 4), winter));

        // South
        plants.add(LSystem.plant3(sk, (Sketch.width_ / 4), (3 * Sketch.height_ / 4), summer));
        plants.add(LSystem.plant4(sk, (3 * Sketch.width_ / 4), (3 * Sketch.height_ / 4), summer));
        
//        for (LSystem plant : plants) {
//            System.out.println(plant.sentence.length());
//        }

        int n = 5;
        float offSetX = (Food.size + 30);
        float offSetY = (Food.size + 30);

        // Create Food
        for (LSystem plant : plants) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    float x = (plant.x - (n / 2 * offSetX)) + (j * offSetX);
                    float y = (plant.y - (n / 2 * offSetY)) + (i * offSetY);
                    float growth = plant.color == summer ? gSummer : gWinter;
                    foodL.add(new Food(sk, x, y, 200, growth, 25)); // Set maxCapacity and initial CurrentLevel
                }
            }
        }
    }

}
