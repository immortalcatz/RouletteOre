package rouletteores.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import rouletteores.RouletteGenerator;
import rouletteores.blocks.BlockRoulette;
import rouletteores.handlers.EventHandler;
import rouletteores.handlers.UpdateNotification;
import rouletteores.scheduler.RouletteScheduler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(new RouletteScheduler());
		FMLCommonHandler.instance().bus().register(handler);
		FMLCommonHandler.instance().bus().register(new UpdateNotification());
		GameRegistry.registerWorldGenerator(new RouletteGenerator(), 0);
		
		int RID = RenderingRegistry.getNextAvailableRenderId();
		BlockRoulette.renderID = RID;
	}
}
