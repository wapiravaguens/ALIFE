package charts;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Button {

    public PApplet sk;
    int x, y, f;
    String text;

    Button(PApplet sk, int x, int y, int f, String text) {
        this.sk = sk;
        this.x = x;
        this.y = y;
        this.f = f;
        this.text = text;
    }

    boolean click() {
        boolean bx = x < sk.mouseX && x + 150 > sk.mouseX;
        boolean by = y < sk.mouseY && y + 50 > sk.mouseY;
        return bx && by;
    }

    void draw(PGraphics p) {
        sk.fill(255);
        p.rect(x, y, 150, 50, 7);
        p.textSize(12);
        sk.fill(0, 0, 0);
        p.text(text, x + 75 , y + 25);
        sk.fill(255);
    }
}
