package net.rystuff.rylib.entity;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.stats.StatBase;
import net.minecraft.world.World;
import net.rystuff.rylib.common.LogHelper;
import net.rystuff.rylib.stats.CustomStatList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomEntityList
{
    public static Map<String, Class> stringToClassMapping = new HashMap<String, Class>();

    public static Map<Class, String> classToStringMapping = new HashMap<Class, String>();

    public static Map<Integer, Class> IDtoClassMapping = new HashMap<Integer, Class>();

    public static Map<Class, Integer> classToIDMapping = new HashMap<Class, Integer>();

    public static Map<String, Integer> stringToIDMapping = new HashMap<String, Integer>();

    public static Map<Integer, CreativeTabs> IDtoTabMapping = new HashMap<Integer, CreativeTabs>();

    public static Map<CreativeTabs, Integer> TabtoIDMapping = new HashMap<CreativeTabs, Integer>();

    public static HashMap<Integer, EntityEggInfo> entityEggs = new LinkedHashMap<Integer, EntityEggInfo>();

    public static void addMapping(Class entityClass, String entityName, int id, CreativeTabs tab, int background, int inner)
    {
        if (stringToClassMapping.containsKey(entityName))
        {
            throw new IllegalArgumentException("ID is already registered: " + entityName);
        }
        else if (IDtoClassMapping.containsKey(id))
        {
            throw new IllegalArgumentException("ID is already registered: " + id);
        }
        else
        {
            stringToClassMapping.put(entityName, entityClass);
            classToStringMapping.put(entityClass, entityName);
            IDtoClassMapping.put(id, entityClass);
            classToIDMapping.put(entityClass, id);
            stringToIDMapping.put(entityName, id);
            IDtoTabMapping.put(id, tab);
            TabtoIDMapping.put(tab, id);
        }
        entityEggs.put(id, new CustomEntityList.EntityEggInfo(id, background, inner));
    }

    public static Entity createEntityByID(int id, World world) {
        Entity entity; entity = null;

        try
        {
            Class<?> entityClass = getClassFromID(id);
            if (entityClass != null)
            {
                entity = (Entity)entityClass.getConstructor(new Class<?>[]{World.class}).newInstance(world);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (entity == null)
        {
            LogHelper.warn("Skipping Entity with id " + id);
        }
        return entity;
    }

    public static CreativeTabs getCreativeTab(int id)
    {
        return IDtoTabMapping.get(id);
    }

    public static int getEntityID(Entity entity)
    {
        Class entityClass = entity.getClass();
        return classToIDMapping.containsKey(entityClass) ? classToIDMapping.get(entityClass) : 0;
    }

    public static Class getClassFromID(int id)
    {
        return IDtoClassMapping.get(id);
    }

    public static String getEntityString(Entity entity)
    {
        return classToStringMapping.get(entity.getClass());
    }

    public static String getStringFromID(int id)
    {
        Class entityclass = getClassFromID(id);
        return entityclass != null ? classToStringMapping.get(entityclass) : null;
    }

    static {}

    public static class EntityEggInfo
    {
        public final int spawnedID;
        public final int primaryColor;
        public final int secondaryColor;
        public final StatBase field_151512_d;
        public final StatBase field_151513_e;

        public EntityEggInfo(int id, int color1, int color2)
        {
            this.spawnedID = id;
            this.primaryColor = color1;
            this.secondaryColor = color2;
            this.field_151512_d = CustomStatList.entityKill(this);
            this.field_151513_e = CustomStatList.entityKilledBy(this);
        }
    }
}
