package prey;

import java.util.Random;

public class PreyGenotype {

    public float initSize, finalSize;
    public float eLife, eRepro, eMax;
    public int expectedAge, adultAge;
    public float vision;
    public int[] color1;
    public int[] color2;
    public int param;
    public float shearX;

    public PreyGenotype(float initSize, float finalSize, float eLife, float eRepro, float eMax, int expectedAge, int adultAge, float vision, int[] color1, int[] color2, int param, float shearX) {
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
        this.shearX = shearX;
    }

    public static PreyGenotype[] crossover(PreyGenotype gen1, PreyGenotype gen2) {
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

        return new PreyGenotype[]{decodeGeneticCode(offspring1),
            decodeGeneticCode(offspring2)};
    }

    public static Object[] createRawGeneticCode(PreyGenotype gen) {
        Object[] code = new Object[12];
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
        code[11] = gen.shearX;
        return code;
    }

    public static PreyGenotype decodeGeneticCode(Object[] code) {
        return new PreyGenotype((float) code[0], //initSize,
                (float) code[1], //finalSize,
                (float) code[2], //eLife,
                (float) code[3], //eRepro,
                (float) code[4], //eMax,
                (int) code[5], //expectedAge,
                (int) code[6], //adultAge,
                (float) code[7], //vision,
                (int[]) code[8], //color1,
                (int[]) code[9], //color2,
                (int) code[10], //shearX
                (float) code[11]); //param;
    }

    public void mutate() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < (1 / 6)) {
            this.initSize = 15.0f + (rand.nextFloat() * 5.0f);
        }
        if (p < (1 / 6)) {
            this.finalSize = 50.0f - (rand.nextFloat() * 30.0f);
        }
        if (p < (1 / 6)) {
            this.eLife = (rand.nextFloat() * 1.0f);
        }
        if (p < (1 / 6)) {
            this.eRepro = (rand.nextFloat() * 100.0f) + 350.0f;//
        }
        if (p < (1 / 6)) {
            this.eMax = (rand.nextFloat() * 200.0f) + 1800.0f;//
        }
        if (p < (1 / 6)) {
            this.expectedAge = 3800;
        }
        if (p < (1 / 6)) {
            this.adultAge = rand.nextInt(51) + 800;//
        }
        if (p < (1 / 6)) {
            this.vision = 45.0f + (rand.nextFloat() * 40.0f);
        }
        if (p < (1 / 6)) {
            this.color1 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        }
        if (p < (1 / 6)) {
            this.color2 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        }
        if (p < (1 / 6)) {
            this.param = rand.nextInt(5);
        }
        if (p < (1 / 6)) {
            this.shearX = rand.nextFloat() * 45.0f;
        }
    }

    public static PreyGenotype random() {
        Random rand = new Random();
        float initSize = 15.0f + (rand.nextFloat() * 5.0f);
        float finalSize = 50.0f - (rand.nextFloat() * 30.0f);
        float eLife = (rand.nextFloat() * 1.0f);
        float eRepro = (rand.nextFloat() * 100.0f) + 350.0f;//
        float eMax = (rand.nextFloat() * 200.0f) + 1800.0f;//
        int expectedAge = 3800;
        int adultAge = rand.nextInt(51) + 800;//
        float vision = 45.0f + (rand.nextFloat() * 40.0f);
        int[] rColor1 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        int[] rColor2 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        int param = rand.nextInt(5);
        float shearX = rand.nextFloat() * 45.0f;
        PreyGenotype preyGenotype = new PreyGenotype(initSize, finalSize, eLife, eRepro, eMax, expectedAge, adultAge, vision, rColor1, rColor2, param, shearX);
        return preyGenotype;
    }
}
