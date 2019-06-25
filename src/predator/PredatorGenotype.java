package predator;

import java.util.Random;

public class PredatorGenotype {

    public float initSize, finalSize;
    public float eLife, eRepro, eMax;
    public int expectedAge, adultAge;
    public float vision;
    public int[] color1;
    public int[] color2;
    public int param;

    public PredatorGenotype(float initSize, float finalSize, float eLife, float eRepro, float eMax, int expectedAge, int adultAge, float vision, int[] color1, int[] color2, int param) {
        this.initSize = initSize;
        this.finalSize = finalSize;
        this.eLife = eLife;
        this.eRepro = eRepro;
        this.eMax = eMax;
        this.expectedAge = expectedAge;
        this.adultAge = adultAge;
        this.vision = vision;
        this.color1 = color1;
        this.color2 = color2;
        this.param = param;
    }

    
    public static PredatorGenotype random() {
        Random rand = new Random();
        float initSize = 30.0f + (rand.nextFloat() * 10.0f);
        float finalSize = 100.0f - (rand.nextFloat() * 50.0f);
        float eLife = rand.nextInt(1) + 1;
        float eRepro = rand.nextInt(101) + 550;//
        float eMax = rand.nextInt(201) + 1800;//
        int expectedAge = 3800;
        int adultAge = rand.nextInt(51) + 1800;//
        float vision = 45.0f + (rand.nextFloat() * 40.0f);
        int[] rColor1 = new int[]{rand.nextInt(127) + 128,0, 0};
        int[] rColor2 = new int[]{rand.nextInt(127) + 128,0, 0};
        int param = rand.nextInt(5);
        PredatorGenotype predatorGenotype = new PredatorGenotype(initSize, finalSize, eLife, eRepro, eMax, expectedAge, adultAge, vision, rColor1, rColor2, param);
        return predatorGenotype;
    }
}
