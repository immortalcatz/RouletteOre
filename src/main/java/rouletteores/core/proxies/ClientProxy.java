package rouletteores.core.proxies;

import rouletteores.blocks.tiles.TileEntityRoulette;
import rouletteores.client.SpecialRendererBlockRoulette;
import rouletteores.client.TileEntityRouletteRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRoulette.class, new TileEntityRouletteRenderer());
		RenderingRegistry.registerBlockHandler(new SpecialRendererBlockRoulette());
	}
}
