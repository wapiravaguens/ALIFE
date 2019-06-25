package affineTransformation;

import prey.Prey;
import processing.core.PApplet;

public class AffineTransformation {

    public static void transform(PApplet sk, Prey prey) {
        sk.translate(prey.position.x, prey.position.y);
        
        float sizeScale = sk.map(prey.size, prey.gen.initSize, prey.gen.finalSize, prey.gen.initSize / 50, prey.gen.finalSize / 50);
        sk.scale(sizeScale);
        sk.shearX(PApplet.radians(prey.gen.shearX));
        
        sk.translate(-prey.position.x, -prey.position.y);
    }
}
