package affineTransformation;

import predator.Predator;
import prey.Prey;
import processing.core.PApplet;

public class AffineTransformation {

    public static void transformPrey(PApplet sk, Prey prey) {
        sk.translate(prey.position.x, prey.position.y);

        float sizeScale = sk.map(prey.size, prey.gen.initSize, prey.gen.finalSize, prey.gen.initSize / 50, prey.gen.finalSize / 50);
        sk.scale(sizeScale);
        sk.shearX(PApplet.radians(prey.gen.shearX));

        sk.translate(-prey.position.x, -prey.position.y);
    }

    public static void transformPredator(PApplet sk, Predator predator) {
        sk.translate(predator.position.x, predator.position.y);

        float sizeScale = sk.map(predator.size, predator.gen.initSize, predator.gen.finalSize, predator.gen.initSize / 50, predator.gen.finalSize / 50);
        sk.scale(sizeScale);
        sk.shearX(PApplet.radians(predator.gen.shearX));

        sk.translate(-predator.position.x, -predator.position.y);
    }
}
