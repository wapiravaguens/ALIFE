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
    public static int speed;
    public static long time;

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
        background = loadImage("background.jpg");
        background.resize(width, height);

        //Globals
        showFrameRate = true;
        showPreyPredator = true;
        showEnergy = false;
        showVision = false;
        speed = 1;

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
            text("Predators: " + aquarium.predators.size(), width - 260, 40);
            text("Preys: " + aquarium.preys.size(), width - 260, 80);
        }
        if (speed > 1) {
            text(speed + "X", width - 90, height - 15);
        }
        popStyle();
    }
}
