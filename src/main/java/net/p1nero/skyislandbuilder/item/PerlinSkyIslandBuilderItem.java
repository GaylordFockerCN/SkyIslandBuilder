package net.p1nero.skyislandbuilder.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.p1nero.skyislandbuilder.utils.SkyIslandGenerator;


public class PerlinSkyIslandBuilderItem extends Item {

    public PerlinSkyIslandBuilderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        SkyIslandGenerator skyIslandGenerator = new SkyIslandGenerator(context.getClickedPos(),context.getLevel());
        skyIslandGenerator.printSkyIsland();
        return super.useOn(context);
    }
}
