package plants;

import processing.core.PApplet;

public class Turtle {

    public static void render(PApplet sk, String toDo, float len, float delta, int[] color, float epsilon) {
        sk.pushStyle();
        sk.pushMatrix();
        sk.stroke(color[0], color[1], color[2]);
        sk.rotate(-PApplet.PI/2);
        for (int i = 0; i < toDo.length(); i++) {
            char c = toDo.charAt(i);
            if (c == 'F') {
                sk.line(0, 0, len, 0);
                sk.translate(len, 0);
            } else if (c == 'G') {
                sk.translate(len, 0);
            } else if (c == '+') { // Turn Left
                sk.rotate(-delta + sk.random(-epsilon, epsilon));
            } else if (c == '-') { // Turn Right
                sk.rotate(delta + sk.random(-epsilon, epsilon));
            } else if (c == '[') {
                sk.pushMatrix();
            } else if (c == ']') {
                sk.popMatrix();
            }
        }
        sk.popMatrix();
        sk.popStyle();
    }

}
