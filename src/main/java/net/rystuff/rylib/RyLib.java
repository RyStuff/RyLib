package net.rystuff.rylib;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = "rylib", name = "RyLib", version = "1.0")

public class RyLib {

    public static Item test;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }
}
