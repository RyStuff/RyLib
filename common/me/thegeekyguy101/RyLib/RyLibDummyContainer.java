package me.thegeekyguy101.RyLib;

import java.util.Arrays;

import me.thegeekyguy101.RyLib.item.ItemDiamondStick;
import me.thegeekyguy101.RyLib.item.ItemGoldStick;
import me.thegeekyguy101.RyLib.item.ItemIronStick;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class RyLibDummyContainer extends cpw.mods.fml.common.DummyModContainer {
	
	public static Item ironStick;
	public static Item goldStick;
	public static Item diamondStick;
	
	public RyLibDummyContainer() {

		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "RyLib";
		meta.name = "RyLib";
		meta.version = "1.0";
		meta.credits = "Ry_dog101";
		meta.authorList = Arrays.asList("Ry_dog101");
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void init(FMLPreInitializationEvent evt) {
		//Iron Stick
		ironStick = new ItemIronStick(1000).setUnlocalizedName("rylib:ironStick");
		GameRegistry.registerItem(ironStick, "ironStick");
		LanguageRegistry.addName(ironStick, "Iron Stick");
		GameRegistry.addRecipe(new ItemStack(ironStick), new Object[]{
			"I", "I", 'I', Item.ingotIron });
		//Gold Stick
		goldStick = new ItemGoldStick(1001).setUnlocalizedName("rylib:goldStick");
		GameRegistry.registerItem(goldStick, "goldStick");
		LanguageRegistry.addName(goldStick, "Gold Stick");
		GameRegistry.addRecipe(new ItemStack(goldStick), new Object[]{
			"G", "G", 'G', Item.ingotGold });
		//Diamond Stick
		diamondStick = new ItemDiamondStick(1002).setUnlocalizedName("rylib:diamondStick");
		GameRegistry.registerItem(diamondStick, "diamondStick");
		LanguageRegistry.addName(diamondStick, "Diamond Stick");
		GameRegistry.addRecipe(new ItemStack(diamondStick), new Object[]{
			"D", "D", 'D', Item.diamond	});
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent evt) {

	}
}
