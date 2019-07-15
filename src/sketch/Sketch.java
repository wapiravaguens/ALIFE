package sketch;

import charts.Line;
import aquarium.Aquarium;
import processing.core.*;
import processing.event.MouseEvent;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    public static boolean showPreyPredator;
    public static boolean showEnergy;
    public static boolean showVision;
    public static boolean complete; //?
    public static int speed;
    public static long time;
    public static float zoom;
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
        zoom = 1.0f;
        width_ = (int) (width * zoom);
        height_ = (int) (height * zoom);
        
        background = loadImage("background.jpg");
        background.resize(width_, height_);
        aquarium = new Aquarium(this);
    }

    @Override
    public void draw() {
        image(background, 0, 0);
       
        for (int i = 0; i < speed; i++) {
            aquarium.update();
            time++;
        }
        
        pushMatrix();
        scale(1.0f/zoom);
        aquarium.render();  
        popMatrix();
        
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

//    @Override
//    public void mouseWheel(MouseEvent event) {
//        float e = event.getCount();
//        zoom = min(1.5f, zoom + e/50);
//    }

    public static void main(String[] args) {
        String[] processingArgs = {"ALIFE"};
        Sketch sk = new Sketch();
        PApplet.runSketch(processingArgs, sk);
        String[] processingArgs2 = {"Charts"};
        Line sk2 = new Line();
        PApplet.runSketch(processingArgs2, sk2);
    }

    public void info() {
        pushStyle();
        textSize(32);
        if (showFrameRate) {
            text(frameRate, 10, 40);
            text(time/60, 10, 80); //change
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
