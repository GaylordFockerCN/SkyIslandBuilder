package net.p1nero.skyislandbuilder.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

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
        PerlinNoise perlinNoise = PerlinNoise.create(RandomSource.create(),frequency,amplitude);
        for(int x=0 ; x<width ; x++){
            for(int z=0 ; z<length ; z++){
                map[x][z] = (int)perlinNoise.getValue(x,0,z);
            }
        }
    }


}
