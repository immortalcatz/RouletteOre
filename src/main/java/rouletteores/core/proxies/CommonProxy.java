package rouletteores.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rouletteores.RouletteGenerator;
import rouletteores.handlers.EventHandler;
import rouletteores.handlers.UpdateNotification;
import rouletteores.scheduler.RouletteScheduler;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(new RouletteScheduler());
		FMLCommonHandler.instance().bus().register(handler);
		FMLCommonHandler.instance().bus().register(new UpdateNotification());
		GameRegistry.registerWorldGenerator(new RouletteGenerator(), 0);
	}

	public void registerRenderers()
	{
	}
}
