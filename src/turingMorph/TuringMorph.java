package turingMorph;

import processing.core.PApplet;
import processing.core.PImage;

public class TuringMorph {

    public PApplet sk;

    public int rows, cols;
    public float[][][] A, B;

    // Du, Dv, u, v
    public float[][] params = {{0.16f, 0.08f, 0.035f, 0.06f},
    {0.16f, 0.08f, 0.042f, 0.065f},
    {0.14f, 0.06f, 0.035f, 0.065f},
    {0.19f, 0.09f, 0.062f, 0.062f},
    {0.16f, 0.08f, 0.05f, 0.065f}
    };

    public TuringMorph(PApplet sk, int rows, int cols, int n) {
        this.sk = sk;
        this.rows = rows;
        this.cols = cols;

        this.A = new float[params.length][rows][cols];
        this.B = new float[params.length][rows][cols];

        generateInitialState();
        compute(n);
    }

    public void paint(PImage img, int[] color1, int[] color2, int k) {
        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width ; j++) {
                if ((int) (sk.brightness(img.pixels[i * img.width + j])) > 127) {
                    float c = (1 - A[k][i][j]);
                    if (c < 0.5) {
                        img.set(j, i, sk.color(color1[0], color1[1], color1[2]));
                        //img.pixels[i * img.width + j] = sk.color(color1[0], color1[1], color1[2]);
                    } else {
                        img.set(j, i, sk.color(color2[0], color2[1], color2[2]));
                        //img.pixels[i * img.width + j] = sk.color(color2[0], color2[1], color2[2]);
                    }
                }
            }
        }
    }

    public PImage texture(int[] color1, int[] color2, int k) {
        PImage img = new PImage(cols, rows);
        img.loadPixels();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float c = (1 - A[k][i][j]);
                if (c < 0.5) {
                    img.pixels[i * cols + j] = sk.color(color1[0], color1[1], color1[2]);
                } else {
                    img.pixels[i * cols + j] = sk.color(color2[0], color2[1], color2[2]);
                }

            }
        }
        img.updatePixels();
        return img;
    }

    public float Ra(float a, float b, int k) {
        return -a * b * b + params[k][2] * (1 - a);
    }

    public float Rb(float a, float b, int k) {
        return +a * b * b - (params[k][2] + params[k][3]) * b;
    }

    public void compute(int n) {
        for (int k = 0; k < params.length; k++) {
            for (int l = 0; l < n; l++) {
                float[][][] dA = new float[params.length][rows][cols];
                float[][][] dB = new float[params.length][rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        float a = A[k][i][j];
                        float b = B[k][i][j];

                        // Calc dA
                        float La = ((-4 * a) + A[k][mod(i + 1, rows)][j] + A[k][mod(i - 1, rows)][j] + A[k][i][mod(j + 1, cols)] + A[k][i][mod(j - 1, cols)]);
                        dA[k][i][j] = (params[k][0] * La + Ra(a, b, k));

                        // Calc dB,
                        float Lb = ((-4 * b) + B[k][mod(i + 1, rows)][j] + B[k][mod(i - 1, rows)][j] + B[k][i][mod(j + 1, cols)] + B[k][i][mod(j - 1, cols)]);
                        dB[k][i][j] = (params[k][1] * Lb + Rb(a, b, k));

                    }
                }

                // Update 
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        A[k][i][j] += dA[k][i][j];
                        B[k][i][j] += dB[k][i][j];
                    }
                }
            }
        }
    }

    public void generateInitialState() {
        for (int k = 0; k < params.length; k++) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    A[k][i][j] = 1.0f;
                    B[k][i][j] = 0.0f;
                }
            }

            for (int l = 0; l < 10; l++) {
                int starty = (int) sk.random(0, rows);
                int startx = (int) sk.random(0, cols);

                for (int i = starty; i < starty + 10 && i < rows; i++) {
                    for (int j = startx; j < startx + 10 && j < cols; j++) {
                        A[k][i][j] = 1.0f;
                        B[k][i][j] = 1.0f;
                    }
                }
            }
        }
    }

    int mod(int a, int b) {
        while (a < 0) {
            a = a + b;
        }
        return a % b;
    }

}
