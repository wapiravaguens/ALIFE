package sketch;

import charts.Line;
import aquarium.Aquarium;
import processing.core.*;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    public static boolean showPreyPredator;
    public static boolean showEnergy;
    public static boolean showVision;
    public static boolean complete;
    public static int speed;
    public static long time;
    public static int width_;
    public static int height_;

    public PImage background;
    public Aquarium aquarium;

    @Override
    public void settings() {
        fullScreen(P2D);
        //size(1280, 720);
    }

    @Override
    public void setup() {
        frameRate(60);
        
        //Globals
        showFrameRate = true;
        showPreyPredator = true;
        showEnergy = false;
        showVision = false;
        speed = 1;
        time = 0;
        width_ = width;
        height_ = height;
        
        
        background = loadImage("background.jpg");
        background.resize(width_, height_);
        aquarium = new Aquarium(this);
    }

    @Override
    public void draw() {
        image(background, 0, 0);

        for (int i = 0; i < speed; i++) {
            time++;
            aquarium.update();
        }

        aquarium.render();

        info();
    }

    @Override
    public void keyPressed() {
        if (key == 'f') {
            showFrameRate = !showFrameRate;
        }
        if (key == 'p') {
            showPreyPredator = !showPreyPredator;
        }
        if (key == 'e') {
            showEnergy = !showEnergy;
        }
        if (key == 'v') {
            showVision = !showVision;
        }
        if (key == '+') {
            speed = min(128, speed * 2);
        }
        if (key == '-') {
            speed = max(1, speed / 2);
        }
        if (key == ' ') {
            complete = !complete;
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"ALIFE"};
        Sketch sk = new Sketch();
        PApplet.runSketch(processingArgs, sk);
//        String[] processingArgs2 = {"Chart"};
//        Line sk2 = new Line();
//        PApplet.runSketch(processingArgs2, sk2);
    }

    public void info() {
        pushStyle();
        textSize(32);
        if (showFrameRate) {
            text(frameRate, 10, 40);
        }
        if (showPreyPredator) {
            text("Predators: " + aquarium.predators.size(), width_ - 260, 40);
            text("Preys: " + aquarium.preys.size(), width_ - 260, 80);
        }
        if (speed > 1) {
            text(speed + "X", width_ - 90, height_ - 15);
        }
        popStyle();
    }
}
