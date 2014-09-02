package net.rystuff.rylib.stats;

import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.rystuff.rylib.entity.CustomEntityList;

public class CustomStatList
{
    public static StatBase entityKill(CustomEntityList.EntityEggInfo entityEggInfo)
    {
        String s = CustomEntityList.getStringFromID(entityEggInfo.spawnedID);
        return s == null ? null : (new StatBase("stat.killEntity." + s, new ChatComponentTranslation("stat.killEntity.", new ChatComponentTranslation("entity." + s + ".name", new Object[0]))).registerStat());
    }

    public static StatBase entityKilledBy(CustomEntityList.EntityEggInfo entityEggInfo)
    {
        String s = CustomEntityList.getStringFromID(entityEggInfo.spawnedID);
        return s == null ? null : (new StatBase("stat.entityKilledBy." + s, new ChatComponentTranslation("stat.entityKilledBy", new ChatComponentTranslation("entity." + s + ".name"), new Object[0])).registerStat());
    }
}
