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

    public PreyGenotype(float initSize, float finalSize, float eLife, float eRepro, float eMax, int expectedAge, int adultAge, float vision, int[] color1, int[] color2, int param) {
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

    public PreyGenotype[] crossover(PreyGenotype gen1, PreyGenotype gen2) {
        Object[] geneticCode1 = createRawGeneticCode(gen1);
        Object[] geneticCode2 = createRawGeneticCode(gen2);
        int pivot = new Random().nextInt(geneticCode1.length);

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

        return new FishGenotype[]{decodeGeneticCode(offspring1),
            decodeGeneticCode(offspring2)};
    }

    public Object[] createRawGeneticCode(PreyGenotype geno) {
        Object[] code = new Object[23];

        code[1] = geno.width;
        code[2] = geno.height;
        code[3] = geno.feedingGenotype.type;
        code[4] = geno.skinGenotype.ba;
        code[5] = geno.skinGenotype.bb;
        code[6] = geno.skinGenotype.Da;
        code[7] = geno.skinGenotype.Db;
        code[8] = geno.skinGenotype.iterations;
        code[9] = geno.skinGenotype.ra;
        code[10] = geno.skinGenotype.rb;
        code[11] = geno.skinGenotype.s;
        code[12] = geno.morphologyGenotype.bends;
        code[13] = geno.movementGenotype.moveToFishProbability;
        code[14] = geno.movementGenotype.moveToNothingProbability;
        code[15] = geno.movementGenotype.moveToPlantProbability;
        code[16] = geno.movementGenotype.stayProbability;
        code[17] = geno.skinGenotype.color1.getRed();
        code[18] = geno.skinGenotype.color1.getGreen();
        code[19] = geno.skinGenotype.color1.getBlue();
        code[20] = geno.skinGenotype.color2.getRed();
        code[21] = geno.skinGenotype.color2.getGreen();
        code[22] = geno.skinGenotype.color2.getBlue();
        return code;
    }

    public static PreyGenotype random() {
        Random rand = new Random();
        float initSize = 15.0f + (rand.nextFloat() * 5.0f);
        float finalSize = 50.0f - (rand.nextFloat() * 25.0f);
        float eLife = rand.nextInt(1) + 1;
        float eRepro = rand.nextInt(101) + 350;//
        float eMax = rand.nextInt(201) + 1800;//
        int expectedAge = 3800;
        int adultAge = rand.nextInt(51) + 800;//
        float vision = 45.0f + (rand.nextFloat() * 40.0f);
        int[] rColor1 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        int[] rColor2 = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)};
        int param = rand.nextInt(5);
        PreyGenotype preyGenotype = new PreyGenotype(initSize, finalSize, eLife, eRepro, eMax, expectedAge, adultAge, vision, rColor1, rColor2, param);
        return preyGenotype;
    }
}
