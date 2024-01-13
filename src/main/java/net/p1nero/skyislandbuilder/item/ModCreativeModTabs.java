package net.p1nero.skyislandbuilder.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.skyislandbuilder.SkyIslandBuilderMod;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SkyIslandBuilderMod.MODID);

    public static final RegistryObject<CreativeModeTab> SKY_ISLAND_BUILDER_TAB = CREATIVE_MODE_TABS.register("sky_island_builder_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.CLEARER.get()))
                    .title(Component.translatable("creativetab.sky_island_builder_tab"))
                    .displayItems((pParameter, pOutput) -> {
                            pOutput.accept(ModItems.CLEARER.get());
                            pOutput.accept(ModItems.PERLIN_BUILDER.get());
                    })
                    .build());

    public static void register(IEventBus bus){
        CREATIVE_MODE_TABS.register(bus);

    }
}
