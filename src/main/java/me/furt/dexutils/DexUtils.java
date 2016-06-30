package me.furt.dexutils;

import java.io.File;

import com.xemsdoom.mexdb.MexDB;
import com.xemsdoom.mexdb.system.Entry;

import me.furt.dexutils.commands.HomeCommand;
import me.furt.dexutils.init.ModBlocks;
import me.furt.dexutils.init.ModItems;
import me.furt.dexutils.proxy.ClientProxy;
import me.furt.dexutils.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.SidedProxy;

/**
 * Created by Furt on 5/30/2016.
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class DexUtils {
	
	public static MexDB homesDB;

	@Instance(Reference.MODID)
	public static DexUtils instance;

	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		Config.preInit(event);
		homesDB = new MexDB(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MODID, "homes");
		homesDB.autopush(true);
		proxy.preinit(event);
		ModItems.registerItems();
		ModBlocks.registerBlocks();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		proxy.registerRenderers();
		GameRegistry.addRecipe(new ItemStack(ModBlocks.citrine_block), "AAA", "AAA", "AAA", 'A', ModItems.citrine_gem);
		GameRegistry.addRecipe(new ItemStack(ModItems.citrine_gem, 9), "A", 'A', ModBlocks.citrine_block);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new HomeCommand("home", "'set <name>', 'del <name>'"));
	}

	public static CreativeTabs coreTab = new CreativeTabs(Reference.MODID) {
		@Override
		public Item getTabIconItem() {
			return null;
		}

		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(ModItems.hearthstone, 1);
		}
	};

}