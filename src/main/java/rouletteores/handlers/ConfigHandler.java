package rouletteores.handlers;

import java.util.ArrayList;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class ConfigHandler
{
	public static Configuration config;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			RouletteOres.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
		
		RO_Settings.chance = config.getFloat("Roulette Chance", Configuration.CATEGORY_GENERAL, 0.01F, 0.0F, 1.0F, "Chance a random command will be run");
		String[] tmpList = config.getStringList("Roulette Commands", Configuration.CATEGORY_GENERAL, new String[]{"/tell {player} You just murdered an innocent ore :(", "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"NOPE.\"}", "/summon PrimedTnt", "/setblock ~ ~ ~ minecraft:bedrock", "/summon Item ~ ~ ~ {Item:{id:264,Count:1}}", "/summon Silverfish ~ ~ ~ {CustomName:\"Troll Fish\",CustomNameVisible:10,ActiveEffects:[{Id:11,Amplifier:10,Duration:300}]}"}, "List of possible commands");
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(String com : tmpList)
		{
			list.add(com);
		}
		
		RO_Settings.commands = list;
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
}
