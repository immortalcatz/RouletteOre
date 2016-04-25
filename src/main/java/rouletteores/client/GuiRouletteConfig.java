package rouletteores.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import rouletteores.core.RouletteOres;
import rouletteores.handlers.ConfigHandler;

public class GuiRouletteConfig extends GuiConfig
{
	public GuiRouletteConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), RouletteOres.MODID, false, false, RouletteOres.NAME);
	}
}
