package sketch;

import charts.Charts;
import aquarium.Aquarium;
import processing.core.*;
import processing.event.MouseEvent;
import turingMorph.TuringMorph;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    public static boolean showPreyPredator;
    public static boolean showEnergy;
    public static boolean showVision;
    public static boolean showTime;
    public static boolean pause;
    public static int speed;
    public static long time;
    public static float maxZoom;
    public static float zoom;
    public static int width_;
    public static int height_;

    public PImage background;
    public Aquarium aquarium;
    public static TuringMorph turingMorph;
    public boolean load = false;

    @Override
    public void settings() {
        fullScreen(P2D);
        //size(1280, 720);
    }

    @Override
    public void setup() {
        frameRate(60);

        //Globals
        showFrameRate = false;
        showPreyPredator = true;
        showEnergy = false;
        showVision = false;
        showTime = false;
        pause = false;
        speed = 1;
        time = 0;
        maxZoom = 1.4056f;
        zoom = maxZoom;
        width_ = (int) (width * maxZoom);
        height_ = (int) (height * maxZoom);

        background = loadImage("background.jpg");
        background.resize(width_, height_);
        this.turingMorph = new TuringMorph(this, 75, 75);
        thread("load");
    }

    public void load() {
        turingMorph.compute(1500);
        synchronized (this) {
            aquarium = new Aquarium(this);
            load = true;
        }
    }

    @Override
    public void draw() {
        if (!pause && load) {
            background(255);
            image(background, 0, 0);

            for (int i = 0; i < speed; i++) {
                aquarium.update();
                time++;
            }

            pushMatrix();
            //translate(mouseX, mouseY);
            scale(1.0f / zoom);
            //translate(-mouseX, -mouseY);
            aquarium.render();
            popMatrix();

            info();
        }
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
        if (key == 't') {
            showTime = !showTime;
        }
        if (key == ' ') {
            pause = !pause;
        }
    }

//    @Override
//    public void mouseWheel(MouseEvent event) {
//        float e = event.getCount();
//        zoom = zoom + e / 50;
//        zoom = min(maxZoom, zoom);
//        zoom = max(0.52f, zoom);
//    }

    public static void main(String[] args) {
        String[] processingArgs = {"ALIFE"};
        Sketch sk = new Sketch();
        PApplet.runSketch(processingArgs, sk);
        String[] processingArgs2 = {"Charts"};
        Charts sk2 = new Charts();
        PApplet.runSketch(processingArgs2, sk2);
    }

    public void info() {
        pushStyle();
        textSize(24);
        if (showFrameRate) {
            text(nf(frameRate, 1, 2), 10, 30);
        }
        if (showTime) {
            text(time / 60 + "s", 10, height - 12);
        }
        if (showPreyPredator) {
            text("Predators: " + aquarium.predators.size(), width - 180, 30);
            text("Preys: " + aquarium.preys.size(), width - 180, 60);
        }
        if (speed > 1) {
            text(speed + "X", width - 70, height - 12);
        }
        popStyle();
    }
}
