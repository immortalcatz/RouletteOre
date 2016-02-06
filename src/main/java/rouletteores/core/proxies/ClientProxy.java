package rouletteores.core.proxies;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rouletteores.core.RouletteOres;


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
	}
	
	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		
		registerBlockModel(RouletteOres.oreRoulette);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block)
	{
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(RouletteOres.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
