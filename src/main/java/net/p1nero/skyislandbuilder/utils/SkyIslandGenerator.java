package net.p1nero.skyislandbuilder.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class SkyIslandGenerator {

    private int length = 20;
    private int width = 20;

    private int maxHeight = 10;
    private double scale = 0.1;
    private int octaves = 6;
    private double persistence = 0.5;
    private double lacunarity = 2.0;
    private int seed = 0;
    private Random random;
    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setOctaves(int octaves) {
        this.octaves = octaves;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        random.setSeed(seed);
    }

    public SkyIslandGenerator(){
        random = new Random(seed);
    }

    public void printSkyIsland(BlockPos bottom, Level level) {
        double[][] skyIsland = generateSkyIsland(width, length, scale, octaves, persistence, lacunarity, seed, maxHeight*10);
        int maxHeight = -1;
        int max_x = 0, max_z = 0;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                if (skyIsland[x][z] > maxHeight) {
                    maxHeight = (int)skyIsland[x][z];
                    max_x = x;
                    max_z = z;
                }
                System.out.print("("+x+','+z+"):"+(int)skyIsland[x][z]+" ");
            }
            System.out.println();
        }

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(0,0,0);
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                for(int y = bottom.getY()+maxHeight ; y>bottom.getY()+maxHeight-skyIsland[x][z] ; y--){
                    //以最高点所在的点为中心打印
                    blockPos.set(bottom.getX()+x-max_x,y,bottom.getZ()+z-max_z);
                    System.out.println(level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), 3));
                }
            }
        }
    }

    private double[][] generateSkyIsland(int width, int height, double scale, int octaves, double persistence, double lacunarity, int seed, int maxHeight) {
        setSeed(seed);
        double[][] skyIsland = new double[height][width];
        int centerX = width / 2;
        int centerY = height / 2;
        double maxDistance = Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = x / (double) width * scale;
                double ny = y / (double) height * scale;
                double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                double normalizedDistance = distanceToCenter / maxDistance;
                if (normalizedDistance >= 1) {
                    skyIsland[y][x] = 0;
                    continue;
                }
                double value = noise(nx, ny, octaves, persistence, lacunarity) * (1 - normalizedDistance) * maxHeight;

                skyIsland[y][x] = value;
            }
        }

        // 找出最高值
        double maxEdgeValue = -Double.MAX_VALUE;
        for (int x = 0; x < width; x++) {
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[0][x]);
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[height - 1][x]);
        }
        for (int y = 0; y < height; y++) {
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[y][0]);
            maxEdgeValue = Math.max(maxEdgeValue, skyIsland[y][width - 1]);
        }

        // 减去最高值并设为0
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                skyIsland[y][x] = Math.max(skyIsland[y][x] - maxEdgeValue, 0);
            }
        }

        return skyIsland;
    }

    private double noise(double x, double y, int octaves, double persistence, double lacunarity) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxNoiseValue = 0;

        for (int i = 0; i < octaves; i++) {
            total += interpolatedNoise(x * frequency, y * frequency) * amplitude;
            maxNoiseValue += amplitude;
            frequency *= lacunarity;
            amplitude *= persistence;
        }

        return total / maxNoiseValue;
    }

    private double interpolatedNoise(double x, double y) {
        int ix = (int) x;
        int iy = (int) y;
        double fx = x - ix;
        double fy = y - iy;

        double v1 = smoothNoise(ix, iy);
        double v2 = smoothNoise(ix + 1, iy);
        double v3 = smoothNoise(ix, iy + 1);
        double v4 = smoothNoise(ix + 1, iy + 1);

        double i1 = interpolate(v1, v2, fx);
        double i2 = interpolate(v3, v4, fx);

        return interpolate(i1, i2, fy);
    }

    private double smoothNoise(int x, int y) {
        double corners = (noise2D(x - 1, y - 1) + noise2D(x + 1, y - 1)
                + noise2D(x - 1, y + 1) + noise2D(x + 1, y + 1)) / 16.0;
        double sides = (noise2D(x - 1, y) + noise2D(x + 1, y)
                + noise2D(x, y - 1) + noise2D(x, y + 1)) / 8.0;
        double center = noise2D(x, y) / 4.0;

        return corners + sides + center;
    }

    public double interpolate(double a, double b, double blend) {
        double theta = blend * Math.PI;
        double f = (1 - Math.cos(theta)) * 0.5;

        return a * (1 - f) + b * f;
    }

    private double noise2D(int x, int y) {
        random.setSeed(x * 49632 + y * 325176 + seed);
        return random.nextDouble() * 2 - 1;
    }

}
