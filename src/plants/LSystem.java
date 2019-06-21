package plants;

import processing.core.PApplet;

public class LSystem {

    public PApplet sk;

    public int x;
    public int y;
    public String sentence;
    public Rule[] rules;
    public int n;
    public int len;
    public float delta;
    public float epsilon;
    public int[] color;

    public LSystem(PApplet sk, int x, int y, String sentence, Rule[] rules, int n, int len, float delta, float epsilon, int[] color) {
        this.sk = sk;
        this.x = x;
        this.y = y;
        this.sentence = sentence;
        this.rules = rules;
        this.n = n;
        this.len = len;
        this.delta = delta;
        this.epsilon = epsilon;
        this.color = color;
        buildSentece();
    }

    public void render() {
        sk.pushMatrix();
        sk.translate(x, y);
        Turtle.render(sk, sentence, len, delta, color, epsilon);
        sk.popMatrix();
    }

    public void buildSentece() {
        for (int i = 0; i < n; i++) {
            StringBuilder nextgen = new StringBuilder();
            for (int j = 0; j < sentence.length(); j++) {
                char curr = sentence.charAt(j);
                String replace = "" + curr;
                for (Rule rule : rules) {
                    char a = rule.a;
                    if (a == curr) {
                        replace = rule.b;
                        break;
                    }
                }
                nextgen.append(replace);
            }
            sentence = nextgen.toString();
        }
    }

    public static LSystem plant1(PApplet sk, int x, int y, int[] color) {
        Rule[] rules = {new Rule('F', "FF-[-F+F+F]+[+F-F-F]")};
        LSystem Plant = new LSystem(sk, x, y, "F", rules, 2, 5, PApplet.radians(22.5f), PApplet.radians(1.0f), color);
        return Plant;
    }

    public static LSystem plant2(PApplet sk, int x, int y, int[] color) {
        Rule[] rules2 = {new Rule('X', "F[+X][-X]FX"), new Rule('F', "FF")};
        LSystem Plant2 = new LSystem(sk, x, y, "X", rules2, 4, 2, PApplet.radians(25.7f), PApplet.radians(3.0f), color);
        return Plant2;
    }

    public static LSystem plant3(PApplet sk, int x, int y, int[] color) {
        Rule[] rules3 = {new Rule('F', "F[+F]F[-F][F]")};
        LSystem Plant3 = new LSystem(sk, x, y, "F", rules3, 3, 5, PApplet.radians(20.0f), PApplet.radians(3.0f), color);
        return Plant3;
    }

    public static LSystem plant4(PApplet sk, int x, int y, int[] color) {
        Rule[] rules4 = {new Rule('X', "F-[[X]+X]+F[+FX]-X"), new Rule('F', "FF")};
        LSystem Plant4 = new LSystem(sk, x, y, "X", rules4, 3, 6, PApplet.radians(22.5f), PApplet.radians(3.0f), color);
        return Plant4;
    }

}
