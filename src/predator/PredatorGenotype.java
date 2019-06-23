package predator;

import java.util.Random;

public class PredatorGenotype {

    public float initSize, finalSize;
    public float eLife, eRepro, eMax;
    public int expectedAge, adultAge;

    public PredatorGenotype(float initSize, float finalSize, float eLife, float eRepro, float eMax, int expectedAge, int adultAge) {
        this.initSize = initSize;
        this.finalSize = finalSize;
        this.eLife = eLife;
        this.eRepro = eRepro;
        this.eMax = eMax;
        this.expectedAge = expectedAge;
        this.adultAge = adultAge;
    }

    public static PredatorGenotype random() {
        Random rand = new Random();
        float initSize =  35.0f + (rand.nextFloat() * 15.0f); 
        float finalSize = 65.0f - (rand.nextFloat() * 15.0f);
        float eLife = rand.nextInt(5) + 3;
        float eRepro = rand.nextInt(101) + 150;//
        float eMax = rand.nextInt(201) + 3800;//
        int expectedAge = rand.nextInt(201) + 1800;
        int adultAge = rand.nextInt(51) + 150;//
        PredatorGenotype predatorGenotype = new PredatorGenotype(initSize, finalSize, eLife, eRepro, eMax, expectedAge, adultAge);
        return predatorGenotype;
    }
}
