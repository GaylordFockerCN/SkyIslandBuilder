package net.p1nero.skyislandbuilder.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.p1nero.skyislandbuilder.utils.SkyIslandGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class SkyIslandGeneratorItem extends Item {

    SkyIslandGenerator skyIslandGenerator;
    Random random;
    int seed;
    public SkyIslandGeneratorItem(Properties properties) {
        super(properties);
        skyIslandGenerator = new SkyIslandGenerator();
        random = new Random();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        //TODO: 打开设置窗口
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tips.perlin_builder"));
        super.appendHoverText(itemStack, level, list, flag);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        skyIslandGenerator.setSeed(random.nextInt());
        if(!level.isClientSide){
            skyIslandGenerator.printSkyIsland(context.getClickedPos(),level);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
