package rouletteores.client;

import rouletteores.core.RouletteOres;
import rouletteores.handlers.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class GuiInventoryConfig extends GuiConfig
{
	@SuppressWarnings({"rawtypes", "unchecked"})
	public GuiInventoryConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), RouletteOres.MODID, false, false, RouletteOres.NAME);
	}
}
