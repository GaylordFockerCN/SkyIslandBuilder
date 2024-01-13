package net.p1nero.skyislandbuilder.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.noise.module.source.Billow;
import org.spongepowered.noise.module.source.Perlin;
import org.spongepowered.noise.module.source.Simplex;

public class SkyIslandGenerator {

    private int height = 10;
    private int width = 10;
    private int length = 10;

    private int frequency = 1;
    private double amplitude = 0.5;
    private BlockPos bottom;
    private Level level;
    private int[][] map;

    public SkyIslandGenerator(BlockPos bottom, Level level){
        this.bottom = bottom;
        this.level = level;
        map = new int[width][length];
    }

    public static SkyIslandGenerator create(BlockPos bottom, Level level){
        return new SkyIslandGenerator(bottom,level);
    }

    public static SkyIslandGenerator create(BlockPos bottom, Level level, int height, int width, int length){
        SkyIslandGenerator skyIslandGenerator = create(bottom,level);
        skyIslandGenerator.height = height;
        skyIslandGenerator.width = width;
        skyIslandGenerator.length = length;
        return skyIslandGenerator;
    }

    public static SkyIslandGenerator create(BlockPos bottom, Level level, int frequency, double amplitude, int height, int width, int length){
        SkyIslandGenerator skyIslandGenerator = create(bottom,level,height,width,length);
        skyIslandGenerator.frequency = frequency;
        skyIslandGenerator.amplitude = amplitude;
        return skyIslandGenerator;
    }

    public void perlin(){
        Perlin perlin = new Perlin();
        for(int x=0 ; x<width ; x++){
            for(int z=0 ; z<length ; z++){
                map[x][z] = (int) perlin.get(x,0,z);
            }
        }
    }

    public static void main(String[] args) {
        PerlinNoise perlinNoise = PerlinNoise.create(RandomSource.create(),1,0.5);
        double[][] skyIsland = new double[100][100];
        for(int x=0 ; x<100 ; x++){
            for(int z=0 ; z<100 ; z++){
                skyIsland[x][z] = (int)perlinNoise.getValue(x,0,z);
            }
        }
        // 打印天空岛高度模型
        for (double[] row : skyIsland) {
            for (double value : row) {
                System.out.print(String.format("%.0f ",value*1000));
            }
            System.out.println();
        }
    }


}
