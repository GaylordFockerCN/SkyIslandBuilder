package net.p1nero.skyislandbuilder.items;
import java.util.Random;

public class Test {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final double SCALE = 0.1;
    private static final int OCTAVES = 6;
    private static final double PERSISTENCE = 0.5;
    private static final double LACUNARITY = 2.0;
    private static final int SEED = 0;

    public static void main(String[] args) {
        double[][] skyIsland = generateSkyIsland(WIDTH, HEIGHT, SCALE, OCTAVES, PERSISTENCE, LACUNARITY, SEED, new int[]{50, 50});

        // 打印天空岛高度模型
        for (double[] row : skyIsland) {
            for (double value : row) {
                System.out.print(String.format("%.0f ",value*1000));
            }
            System.out.println();
        }
    }

    public static double[][] generateSkyIsland(int width, int height, double scale, int octaves, double persistence, double lacunarity, int seed, int[] highestPoint) {
        double[][] skyIsland = new double[height][width];
        Random random = new Random(seed);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width * scale;
                double ny = y / (double) height * scale;
                double value = noise(nx, ny, octaves, persistence, lacunarity, random, highestPoint);

                skyIsland[y][x] = value;
            }
        }

        return skyIsland;
    }

    public static double noise(double x, double y, int octaves, double persistence, double lacunarity, Random random, int[] highestPoint) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxNoiseValue = 0;

        for (int i = 0; i < octaves; i++) {
            total += interpolatedNoise(x * frequency, y * frequency, random) * amplitude;

            maxNoiseValue += amplitude;

            frequency *= lacunarity;
            amplitude *= persistence;
        }

        // 将最高点的值设置为指定的高度
        total += highestPoint[1] - highestPoint[0];

        return total / maxNoiseValue;
    }

    public static double interpolatedNoise(double x, double y, Random random) {
        int ix = (int) x;
        int iy = (int) y;
        double fx = x - ix;
        double fy = y - iy;

        double v1 = smoothNoise(ix, iy, random);
        double v2 = smoothNoise(ix + 1, iy, random);
        double v3 = smoothNoise(ix, iy + 1, random);
        double v4 = smoothNoise(ix + 1, iy + 1, random);

        double i1 = interpolate(v1, v2, fx);
        double i2 = interpolate(v3, v4, fx);

        return interpolate(i1, i2, fy);
    }

    public static double smoothNoise(int x, int y, Random random) {
        double corners = (noise2D(x - 1, y - 1, random) + noise2D(x + 1, y - 1, random)
                + noise2D(x - 1, y + 1, random) + noise2D(x + 1, y + 1, random)) / 16.0;
        double sides = (noise2D(x - 1, y, random) + noise2D(x + 1, y, random)
                + noise2D(x, y - 1, random) + noise2D(x, y + 1, random)) / 8.0;
        double center = noise2D(x, y, random) / 4.0;

        return corners + sides + center;
    }

    public static double interpolate(double a, double b, double blend) {
        double theta = blend * Math.PI;
        double f = (1 - Math.cos(theta)) * 0.5;

        return a * (1 - f) + b * f;
    }

    public static double noise2D(int x, int y, Random random) {
        random.setSeed(x * 49632 + y * 325176 + SEED);
        return random.nextDouble() * 2 - 1;
    }
}
