package predator;

import java.util.Random;

public class PredatorGenotype {

    // Form
    public float initSize, finalSize;
    public float shearX;

    // Skin
    public int param;
    public int[] color1, color2;

    // Metabolism and age
    public float eLife, eRepro, eMax;
    public int expectedAge, adultAge;

    // Movement
    public float vision;
    public float fSep, fAli, fCoh;
    public float maxforce, maxspeed;

    public PredatorGenotype(float initSize, float finalSize, float shearX, int param, int[] color1, int[] color2, float eLife, float eRepro, float eMax, int expectedAge, int adultAge, float vision, float fSep, float fAli, float fCoh, float maxforce, float maxspeed) {
        this.initSize = initSize;
        this.finalSize = finalSize;
        this.shearX = shearX;
        this.param = param;
        this.color1 = color1;
        this.color2 = color2;
        this.eLife = eLife;
        this.eRepro = eRepro;
        this.eMax = eMax;
        this.expectedAge = expectedAge;
        this.adultAge = adultAge;
        this.vision = vision;
        this.fSep = fSep;
        this.fAli = fAli;
        this.fCoh = fCoh;
        this.maxforce = maxforce;
        this.maxspeed = maxspeed;
    }

    public static PredatorGenotype[] offsprings(PredatorGenotype gen1, PredatorGenotype gen2) {
        Object[] geneticCode1 = createRawGeneticCode(gen1);
        Object[] geneticCode2 = createRawGeneticCode(gen2);

        // CrossOver
        int index = new Random().nextInt(geneticCode1.length - 1) + 1;
        Object[] offspring1 = new Object[geneticCode1.length];
        Object[] offspring2 = new Object[geneticCode1.length];
        for (int i = 0; i < index; i++) {
            offspring1[i] = geneticCode1[i];
            offspring2[i] = geneticCode2[i];
        }
        for (int i = index; i < geneticCode1.length; i++) {
            offspring1[i] = geneticCode2[i];
            offspring2[i] = geneticCode1[i];
        }

        // Mutation
        Random rand = new Random();
        Object[] randValues1 = randomValues();
        Object[] randValues2 = randomValues();
        for (int i = 0; i < offspring1.length; i++) {
            float p = rand.nextFloat();
            if (p < (1.0 / offspring1.length)) {
                offspring1[i] = randValues1[i];
            }
            if (p < (1.0 / offspring2.length)) {
                offspring2[i] = randValues2[i];
            }
        }

        return new PredatorGenotype[]{decodeGeneticCode(offspring1), decodeGeneticCode(offspring2)};
    }

    public static Object[] createRawGeneticCode(PredatorGenotype gen) {
        Object[] code = new Object[17];
        code[0] = gen.initSize;
        code[1] = gen.finalSize;
        code[2] = gen.shearX;
        code[3] = gen.param;
        code[4] = gen.color1;
        code[5] = gen.color2;
        code[6] = gen.eLife;
        code[7] = gen.eRepro;
        code[8] = gen.eMax;
        code[9] = gen.expectedAge;
        code[10] = gen.adultAge;
        code[11] = gen.vision;
        code[12] = gen.fSep;
        code[13] = gen.fAli;
        code[14] = gen.fCoh;
        code[15] = gen.maxforce;
        code[16] = gen.maxspeed;
        return code;
    }

    public static PredatorGenotype decodeGeneticCode(Object[] code) {
        return new PredatorGenotype((float) code[0], //initSize,
                (float) code[1], //finalSize,
                (float) code[2], //shearX,
                (int) code[3], //param,
                (int[]) code[4], //color1,
                (int[]) code[5], //color2,
                (float) code[6], //eLife,
                (float) code[7], //eRepro,
                (float) code[8], //eMax,
                (int) code[9], //expectedAge,
                (int) code[10], //adultAge
                (float) code[11], //vision
                (float) code[12], //fSep
                (float) code[13], //fAli
                (float) code[14], //fCoh
                (float) code[15], //maxforce
                (float) code[16]); //maxspeed
    }

    public static Object[] randomValues() {
        Random rand = new Random();
        Object[] code = new Object[17];
        code[0] = 15.0f + (rand.nextFloat() * 5.0f); //initSize >
        code[1] = 50.0f - (rand.nextFloat() * 30.0f); //finalSize >
        code[2] = rand.nextFloat() * 30.0f; //shearX >
        code[3] = rand.nextInt(5); //param >
        code[4] = new int[]{rand.nextInt(128) + 128, 0, 0}; //color1 >
        code[5] = new int[]{0, 0, 0}; //color2 >
        code[6] = (float) ((rand.nextGaussian() * 0.5) + 1.5f); //eLife >
        code[7] = (float) ((rand.nextGaussian() * 1.25f) + 5.0f) * 60.0f; //eRepro >
        code[8] = (float) ((rand.nextGaussian() * 3.0f) + 20.0f) * 60.0f; //eMax >
        code[9] = (int) (((rand.nextFloat() * 5.0f) + 60.0f) * 60.0f); //expectedAge >
        code[10] = (int) (((rand.nextFloat() * 2.0f) + 30.0f) * 60.0f); //adultAge 
        code[11] = 45.0f + (rand.nextFloat() * 45.0f); //vision >
        code[12] = (float) ((rand.nextGaussian() * 0.1) + 1.5f); //fSep >
        code[13] = (float) ((rand.nextGaussian() * 0.1) + 1.0f); //fAli >
        code[14] = (float) ((rand.nextGaussian() * 0.1) + 1.25f); //fCoh >
        code[15] = (float) ((rand.nextGaussian() * 0.02) + 0.15f); //maxforce >
        code[16] = (float) ((rand.nextGaussian() * 0.20) + 4.2f); //maxspeed >
        return code;
    }

    public static PredatorGenotype random() {
        PredatorGenotype predatorGenotype = decodeGeneticCode(randomValues());
        return predatorGenotype;
    }
}
