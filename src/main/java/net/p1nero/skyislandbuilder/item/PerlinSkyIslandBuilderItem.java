package net.p1nero.skyislandbuilder.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.p1nero.skyislandbuilder.utils.SkyIslandGenerator;


public class PerlinSkyIslandBuilderItem extends Item {

    SkyIslandGenerator skyIslandGenerator;
    public PerlinSkyIslandBuilderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        //TODO: 打开设置窗口
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        SkyIslandGenerator skyIslandGenerator = new SkyIslandGenerator();
        skyIslandGenerator.printSkyIsland(context.getClickedPos(),context.getLevel());
        return super.useOn(context);
    }
}
