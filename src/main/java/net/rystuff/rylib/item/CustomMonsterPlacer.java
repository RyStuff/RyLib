package net.rystuff.rylib.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
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

    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (world.isRemote)
        {
            return true;
        }
        else {
            Block block = world.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
            p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
            p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
            p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
            double d0 = 0.0D;

            if (p_77648_7_ == 1 && block.getRenderType() == 11) {
                d0 = 0.5D;
            }

            Entity entity = spawnCreature(world, itemStack.getItemDamage(), (double) p_77648_4_ + 0.5D, (double) p_77648_5_ + d0, (double) p_77648_6_ + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
                }

                if (!entityPlayer.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
            }

            return true;
        }
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (world.isRemote)
        {
            return itemStack;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);

            if (movingobjectposition == null)
            {
                return itemStack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!world.canMineBlock(entityPlayer, i, j, k))
                    {
                        return itemStack;
                    }

                    if (!entityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStack))
                    {
                        return itemStack;
                    }

                    if (world.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnCreature(world, itemStack.getItemDamage(), (double)i, (double)j, (double)k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && itemStack.hasDisplayName())
                            {
                                ((EntityLiving)entity).setCustomNameTag(itemStack.getDisplayName());
                            }

                            if (!entityPlayer.capabilities.isCreativeMode)
                            {
                                --itemStack.stackSize;
                            }
                        }
                    }
                }

                return itemStack;
            }
        }
    }

    public static Entity spawnCreature(World world, int id, double x, double y, double z)
    {
        if (!CustomEntityList.entityEggs.containsKey(id))
        {
            return null;
        }
        else
        {
            Entity entity = null;

            for (int j = 0; j < 1; ++j)
            {
                entity = CustomEntityList.createEntityByID(id, world);

                if (entity != null && entity instanceof EntityLivingBase)
                {
                    EntityLiving entityliving = (EntityLiving)entity;
                    entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                    entityliving.rotationYawHead = entityliving.rotationYaw;
                    entityliving.renderYawOffset = entityliving.rotationYaw;
                    entityliving.onSpawnWithEgg(null);
                    world.spawnEntityInWorld(entity);
                    entityliving.playLivingSound();
                }
            }

            return entity;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
}
