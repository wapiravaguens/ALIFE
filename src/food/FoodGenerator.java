package food;

import java.util.ArrayList;
import plants.LSystem;
import processing.core.PApplet;
import sketch.Sketch;

public class FoodGenerator {

    public PApplet sk;

    public ArrayList<LSystem> plants;
    public int[] winter = {255, 255, 255};
    public int[] summer = {32, 192, 32};

    public ArrayList<Food> foodL;
    public boolean currentStation;
    public float gSummer, gWinter, timeStation;

    public FoodGenerator(PApplet sk) {
        this.sk = sk;
        this.plants = new ArrayList<>();
        this.foodL = new ArrayList<>();
        this.currentStation = true;

        // Initialization
        this.gSummer = 3.0f;
        this.gWinter = 0.1f;
        this.timeStation = 5;
        make();
    }

    public void render() {
        update();

        for (Food food : foodL) {
            food.render();
        }

        for (LSystem plant : plants) {
            plant.render();
        }
    }

    public void update() {
        if (Sketch.time % (timeStation * 60) == 0) {
            // Update LSystem
            int[] cNorth = currentStation ? summer : winter;
            int[] cSouth = currentStation ? winter : summer;
            for (LSystem plant : plants) {
                if (plant.y < sk.height / 2) {
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
                if (food.position.y < sk.height / 2) {
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
        plants.add(LSystem.plant1(sk, (sk.width / 4), (sk.height / 4), winter));
        plants.add(LSystem.plant2(sk, (3 * sk.width / 4), (sk.height / 4), winter));

        // South
        plants.add(LSystem.plant3(sk, (sk.width / 4), (3 * sk.height / 4), summer));
        plants.add(LSystem.plant4(sk, (3 * sk.width / 4), (3 * sk.height / 4), summer));
//        for (LSystem plant : plants) {
//            System.out.println(plant.sentence.length());
//        }

        int n = 5;
        int offSetX = (Food.lx + 30);
        int offSetY = (Food.ly + 30);

        // Create Food
        for (LSystem plant : plants) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    float x = (plant.x - (n / 2 * offSetX)) + (j * offSetX);
                    float y = (plant.y - (n / 2 * offSetY)) + (i * offSetY);
                    float growth = plant.color == summer ? gSummer : gWinter;
                    foodL.add(new Food(sk, x, y, 100, growth, 25));
                }
            }
        }
    }

}
