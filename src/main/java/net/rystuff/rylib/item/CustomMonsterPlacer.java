package net.rystuff.rylib.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.rystuff.rylib.entity.CustomEntityList;

public class CustomMonsterPlacer extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon theIcon;

    public static CreativeTabs creativeTabs;

    public CustomMonsterPlacer(CreativeTabs tab)
    {
        creativeTabs = tab;
        this.setHasSubtypes(true);
        this.setCreativeTab(tab);
    }

    public String getItemStackDisplayName(ItemStack itemStack)
    {
        String s = null;

        if (CustomEntityList.getCreativeTab(itemStack.getItemDamage())==creativeTabs)
        {
            s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
            String s1 = CustomEntityList.getStringFromID(itemStack.getItemDamage());

            if (s1 != null)
            {
                s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
            }
        }

        return s;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int p_82790_2_)
    {
        CustomEntityList.EntityEggInfo entityEggInfo = null;

        if (CustomEntityList.getCreativeTab(itemStack.getItemDamage())==creativeTabs)
        {
            entityEggInfo = CustomEntityList.entityEggs.get(itemStack.getItemDamage());
        }
       return entityEggInfo != null ? (p_82790_2_ == 0 ? entityEggInfo.primaryColor : entityEggInfo.secondaryColor) : 16777215;
    }
}
