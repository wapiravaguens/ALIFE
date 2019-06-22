package Aquarium;

import Prey.Prey;
import QuadTree.*;
import java.util.ArrayList;
import processing.core.PApplet;
import sketch.Sketch;

public class Aquarium {

    public PApplet sk;
    public Rectangle limit;

    public ArrayList<Prey> fishes;
    public QuadTree qfishes;

    public Aquarium(PApplet sk) {
        this.sk = sk;
        limit = new Rectangle(sk.width / 2, sk.height / 2, sk.width, sk.height);

        this.fishes = new ArrayList<>();

        make();
    }

    public void make() {
        for (int i = 0; i < 500; i++) {
            fishes.add(new Prey(sk, sk.random(0, sk.width), sk.random(0, sk.height)));
        }
    }

    public void render() {
        qfishes = new QuadTree(sk, limit, 25);
        for (Prey fish : fishes) {
            qfishes.insert(new Point(fish));
        }
        for (Prey fish : fishes) {
            fish.render();
        }
//        qfishes.show();
//
//        if (Sketch.showFrameRate) {
//            for (Prey prey : fishes) {
//                Circle range = new Circle(prey.position.x, prey.position.y, 100);
//                //Rectangle range = new Rectangle(position.x, position.y, 50, 50);
//                ArrayList<Point> points = new ArrayList();
//                qfishes.query(range, points);
//                //circle(range.x, range.y, 25 * 2);
//                for (Point pr : points) {
//                    Prey other = (Prey) pr.obj;
//                    sk.pushStyle();
//                    sk.noFill();
//                    //sk.circle(range.x, range.y, 200);
//                    sk.stroke(0, 0, 255);
//                    sk.strokeWeight(5);
//                    sk.point((float) other.position.x, (float) other.position.y);
//                    sk.popStyle();
//                }
//            }
//        } else {
//            for (Prey prey : fishes) {
//                for (Prey prey2 : fishes) {
//                    sk.pushStyle();
//                    sk.noFill();
//                    //sk.circle(prey.position.x, prey.position.y, 200);
//                    sk.stroke(0, 255, 0);
//                    sk.strokeWeight(5);
//                    sk.point((float) prey2.position.x, (float) prey2.position.y);
//                    sk.popStyle();
//                }
//            }
//        }

    }
}
