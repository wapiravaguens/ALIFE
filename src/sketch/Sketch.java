package sketch;

import Aquarium.Aquarium;
import food.FoodGenerator;
import processing.core.*;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    public static long time;

    public PImage background;
    public FoodGenerator foodGenerator;
    public Aquarium aquarium;

    @Override
    public void settings() {
        fullScreen();
        //size(1280, 720);
    }

    @Override
    public void setup() {
        frameRate(60);
        background = loadImage("background.jpg");
        background.resize(width, height);

        //Globals
        showFrameRate = true;
        foodGenerator = new FoodGenerator(this);
        aquarium = new Aquarium(this);
    }

    @Override
    public void draw() {
        image(background, 0, 0);
        time++;

        foodGenerator.render();
        aquarium.render();

        info();
    }

    @Override
    public void keyPressed() {
        if (key == 'f') {
            showFrameRate = !showFrameRate;
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Sketch"};
        Sketch sk = new Sketch();
        PApplet.runSketch(processingArgs, sk);
    }

    public void info() {
        if (showFrameRate) {
            textSize(32);
            text(frameRate, 10, 40);
        }
    }
}
