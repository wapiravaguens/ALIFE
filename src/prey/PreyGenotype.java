package prey;

import java.util.Random;

public class PreyGenotype {

    // Form
    public float initSize, finalSize;
    public float shearX;
    
    // Skin
    public int[] color1, color2;
    public int param;
    
    // Movement
    public float vision;
    public float maxspeed, maxforce;
    public float fSep, fAli, fCoh;
    
    // Metabolism and age
    public float eLife, eRepro, eMax;
    public int expectedAge, adultAge;
    
    

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

    public static PreyGenotype[] offsprings(PreyGenotype gen1, PreyGenotype gen2) {
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

        return new PreyGenotype[]{decodeGeneticCode(offspring1), decodeGeneticCode(offspring2)};
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
                (int) code[10], //Param
                (float) code[11]); //shearX
    }

    public static Object[] randomValues() {
        Random rand = new Random();
        Object[] code = new Object[12];
        code[0] = 15.0f + (rand.nextFloat() * 5.0f); //initSize,
        code[1] = 50.0f - (rand.nextFloat() * 30.0f); //finalSize,
        
        code[2] = ((rand.nextFloat() * 2.0f) + 0.5f); //eLife,
        
        code[3] = ((rand.nextFloat() * 1.25f) + 10.0f) * 60.0f; //eRepro,
        code[4] = ((rand.nextFloat() * 2.5f) + 20.0f) * 60.0f; //eMax,
        
        code[5] = (int) (((rand.nextFloat() * 5.0f) + 60.0f) * 60.0f); //expectedAge,
        code[6] = (int) (((rand.nextFloat() * 2.0f) + 15.0f) * 60.0f); //adultAge,
        
        code[7] = 50.0f + (rand.nextFloat() * 50.0f); //vision,
        code[8] = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)}; //color1,
        code[9] = new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)}; //color2,
        code[10] = rand.nextInt(5); //Param
        code[11] = rand.nextFloat() * 30.0f; //shearX
        return code;
    }

    public static PreyGenotype random() {
        PreyGenotype preyGenotype = decodeGeneticCode(randomValues());
        return preyGenotype;
    }
}
