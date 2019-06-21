package sketch;

import processing.core.*;

public class Sketch extends PApplet {

    // Globals
    public static boolean showFrameRate;
    
    public PImage background;

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
    }

    @Override
    public void draw() {
        image(background, 0, 0);
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
