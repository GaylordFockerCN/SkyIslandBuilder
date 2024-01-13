package net.p1nero.skyislandbuilder.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.p1nero.skyislandbuilder.SkyIslandBuilderMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SkyIslandBuilderMod.MODID);

    public static  final RegistryObject<Item> CLEARER = ITEMS.register("clearer",()->new ClearerItem(new Item.Properties()));
    public static  final RegistryObject<Item> PERLIN_BUILDER = ITEMS.register("perlin_builder",()->new PerlinSkyIslandBuilderItem(new Item.Properties()));

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }

}
