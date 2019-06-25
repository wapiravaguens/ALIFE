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

    public static PredatorGenotype[] crossover(PredatorGenotype gen1, PredatorGenotype gen2) {
        Object[] geneticCode1 = createRawGeneticCode(gen1);
        Object[] geneticCode2 = createRawGeneticCode(gen2);

        int pivot = new Random().nextInt(geneticCode1.length) + 1;

        Object[] offspring1 = new Object[geneticCode1.length];
        Object[] offspring2 = new Object[geneticCode1.length];

        for (int i = 0; i < pivot; i++) {
            offspring1[i] = geneticCode1[i];
            offspring2[i] = geneticCode2[i];
        }

        for (int i = pivot; i < geneticCode1.length; i++) {
            offspring1[i] = geneticCode2[i];
            offspring2[i] = geneticCode1[i];
        }

        return new PredatorGenotype[]{decodeGeneticCode(offspring1),
            decodeGeneticCode(offspring2)};
    }

    public static Object[] createRawGeneticCode(PredatorGenotype gen) {
        Object[] code = new Object[11];
        code[0] = gen.initSize;
        code[1] = gen.finalSize;
        code[2] = gen.eLife;
        code[3] = gen.eRepro;
        code[4] = gen.eMax;
        code[5] = gen.expectedAge;
        code[6] = gen.adultAge;
        code[7] = gen.vision;
        code[8] = gen.color1;
        code[9] = gen.color2;
        code[10] = gen.param;
        return code;
    }

    public static PredatorGenotype decodeGeneticCode(Object[] code) {
        return new PredatorGenotype((float) code[0], //initSize,
                (float) code[1], //finalSize,
                (float) code[2], //eLife,
                (float) code[3], //eRepro,
                (float) code[4], //eMax,
                (int) code[5], //expectedAge,
                (int) code[6], //adultAge,
                (float) code[7], //vision,
                (int[]) code[8], //color1,
                (int[]) code[9], //color2,
                (int) code[10]); //param;
    }

    public void mutate() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < (1 / 11)) {
            this.initSize = 30.0f + (rand.nextFloat() * 10.0f);
        }
        if (p < (1 / 11)) {
            this.finalSize = 100.0f - (rand.nextFloat() * 50.0f);
        }
        if (p < (1 / 11)) {
            this.eLife = rand.nextInt(1) + 1;
        }
        if (p < (1 / 11)) {
            this.eRepro = rand.nextInt(101) + 550;//
        }
        if (p < (1 / 11)) {
            this.eMax = rand.nextInt(201) + 1800;//
        }
        if (p < (1 / 11)) {
            this.expectedAge = 3800;
        }
        if (p < (1 / 11)) {
            this.adultAge = rand.nextInt(51) + 1800;//
        }
        if (p < (1 / 11)) {
            this.vision = 45.0f + (rand.nextFloat() * 40.0f);
        }
        if (p < (1 / 11)) {
            this.color1 = new int[]{rand.nextInt(127) + 128,0, 0};
        }
        if (p < (1 / 11)) {
            this.color2 = new int[]{rand.nextInt(127) + 128,0, 0};
        }
        if (p < (1 / 11)) {
            this.param = rand.nextInt(5);
        }
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
