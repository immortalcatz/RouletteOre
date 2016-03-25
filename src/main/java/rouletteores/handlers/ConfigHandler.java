package rouletteores.handlers;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import rouletteores.JsonHelper;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import rouletteores.scheduler.RouletteRewardRegistry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public class ConfigHandler
{
	public static Configuration config;
	static JsonArray defComs = new JsonArray();
	
	public static void initConfigs()
	{
		if(config == null)
		{
			RouletteOres.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		File fileEvents = new File("config/RouletteEvents.json");
		File fileDefaults = new File("config/RouletteDefaults.json");
		boolean flag = false;
		
		if(!fileEvents.exists())
		{
			flag = true;
		}
		
		RouletteRewardRegistry.loadRewards(JsonHelper.ReadArrayFromFile(fileEvents), false);
		
		config.load();

		RO_Settings.hideUpdates = config.getBoolean("Hide Updates", Configuration.CATEGORY_GENERAL, false, "Hides the update notifications");
		RO_Settings.useOnline = config.getBoolean("Use Online Defaults", Configuration.CATEGORY_GENERAL, true, "Use Funwayguy's pick of events (Must be connected to the internet)");
		RO_Settings.nonDropSelf = config.getBoolean("Non-self Ore Drops", Configuration.CATEGORY_GENERAL, true, "Check whether the ore drop is itself or not. Prevent's exploits on ores like iron");
		RO_Settings.chance = config.getFloat("Roulette Chance", Configuration.CATEGORY_GENERAL, 0.01F, 0.0F, 1.0F, "Chance a random command will be run");
		RO_Settings.fakePlayers = config.getBoolean("Enable Fake Players", Configuration.CATEGORY_GENERAL, false, "Enable machines acting as player to trigger RouletteOres");
		RO_Settings.silkImmunity = config.getBoolean("Silk Immunity", Configuration.CATEGORY_GENERAL, true, "Using silk touch on ores prevents triggering events");
		RO_Settings.fortuneMult = config.getBoolean("Fortune Multiplier", Configuration.CATEGORY_GENERAL, true, "Fortune enchantments multiply the chance of triggering an ore");
		RO_Settings.genRoulette = config.getBoolean("Generate Ores", Configuration.CATEGORY_GENERAL, true, "Generate the mod's native Roulette Ore");
		RO_Settings.extraOres.clear();
		RO_Settings.extraOres.addAll(Arrays.asList(config.getStringList("Extra Ores", Configuration.CATEGORY_GENERAL, new String[]{}, "Additional blocks that should be treated as 'ores' (Ignores non-self drops)")));
		
		if(config.hasKey(Configuration.CATEGORY_GENERAL, "Roulette Commands"))
		{
			RouletteOres.logger.log(Level.WARN, "Converting legacy commands... (NOT EVERYTHING WILL WORK)");
			String[] oldCmds = config.getStringList("Roulette Commands", Configuration.CATEGORY_GENERAL, new String[0], "List of possible commands (Turn off online defaults if editing)(use ';;' to split multiple commands)");
			config.getCategory(Configuration.CATEGORY_GENERAL).remove("Roulette Commands"); // Remove legacy items once converted to new system
			
			for(String cmd : oldCmds)
			{
				RouletteRewardRegistry.readLegacyReward(cmd);
			}
			
			JsonArray ja = new JsonArray();
			RouletteRewardRegistry.saveRewards(ja);
			JsonHelper.WriteToFile(fileEvents, ja); // Must update file with old commands for future use
			flag = false;
		} 
		
		if(RO_Settings.useOnline)
		{
			try
			{
				String onlineList = UpdateNotification.getNotification("https://raw.githubusercontent.com/Funwayguy/RouletteOre-Defaults/master/AdvancedCommands1.9.json", false);
				Gson g = new GsonBuilder().setPrettyPrinting().create();
				JsonArray ja = g.fromJson(onlineList, JsonArray.class);
				RouletteRewardRegistry.loadRewards(ja, true);
				JsonHelper.WriteToFile(fileDefaults, ja);
				flag = false;
				RouletteOres.logger.log(Level.INFO, "Loaded online default commands and updated RouletteDefault.json");
			} catch(Exception e)
			{
				if(fileDefaults.exists())
				{
					RouletteOres.logger.log(Level.WARN, "Unable to load online defaults, loading cached version...", e);
					JsonArray ja = JsonHelper.ReadArrayFromFile(fileDefaults);
					RouletteRewardRegistry.loadRewards(ja, true);
					flag = false;
				}
			}
		}
		
		if(flag)
		{
			RouletteRewardRegistry.loadRewards(defComs, true);
			JsonHelper.WriteToFile(fileEvents, defComs);
			flag = false;
			RouletteOres.logger.log(Level.INFO, "Loaded local default commands and updated RouletteEvents.json");
		}
		
		config.save();
		
		RouletteOres.logger.log(Level.INFO, "Configs loaded.");
	}
	
	static
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(ConfigHandler.class.getResourceAsStream("/defaults.json"));
			defComs = new Gson().fromJson(isr, JsonArray.class);
			isr.close();
		} catch(Exception e)
		{
			RouletteOres.logger.log(Level.ERROR, "Unable to load local defaults!", e);
			defComs = new JsonArray();
		}
	}
}
