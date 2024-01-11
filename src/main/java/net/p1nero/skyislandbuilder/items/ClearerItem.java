package net.p1nero.skyislandbuilder.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClearerItem extends Item {

    public ClearerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        DFS_Clear(context.getClickedPos(),context.getLevel());
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        //list.add(Component.translatable("tips.clearer"));
        list.add(Component.literal("慎重使用！！！"));
        super.appendHoverText(itemStack, level, list, flag);
    }

    //递归清理连着的方块
    public void DFS_Clear(BlockPos pos, Level level){
        if(level.getBlockState(pos).getBlock() instanceof AirBlock){
            return;
        }
        level.destroyBlock(pos,true);//以后可以改成在配置文件里面设置是否显示特效
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        DFS_Clear(new BlockPos(x+1,y,z),level);
        DFS_Clear(new BlockPos(x,y+1,z),level);
        DFS_Clear(new BlockPos(x,y,z+1),level);
        DFS_Clear(new BlockPos(x-1,y,z),level);
        DFS_Clear(new BlockPos(x,y-1,z),level);
        DFS_Clear(new BlockPos(x,y,z-1),level);
    }

}
