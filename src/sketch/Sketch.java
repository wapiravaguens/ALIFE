package sketch;

import aquarium.Aquarium;
import processing.core.*;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    public static boolean showPreyPredator;
    public static boolean showEnergy;
    
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
        showPreyPredator= true;
        showEnergy = false;
        
        aquarium = new Aquarium(this);
    }

    @Override
    public void draw() {
        image(background, 0, 0);
        time++;

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
    }

    public static void main(String[] args) {
        String[] processingArgs = {"Sketch"};
        Sketch sk = new Sketch();
        PApplet.runSketch(processingArgs, sk);
    }

    public void info() {
        pushStyle();
        textSize(32);
        if (showFrameRate) {
            text(frameRate, 10, 40);
        }
        if (showPreyPredator) {
            text(frameRate, 10, 40);
            text("Predators: " + aquarium.predators.size(), width - 250, 40);
            text("Preys: "+ aquarium.preys.size(), width - 250, 80);
        }
        popStyle();
    }
}
