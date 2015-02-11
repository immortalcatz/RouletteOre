package rouletteores.handlers;

import java.util.ArrayList;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

public class ConfigHandler
{
	public static Configuration config;
	static String[] defComs;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			RouletteOres.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();

		RO_Settings.hideUpdates = config.getBoolean("Hide Updates", Configuration.CATEGORY_GENERAL, false, "Hides the update notifications");
		RO_Settings.useOnline = config.getBoolean("Use Online Defaults", Configuration.CATEGORY_GENERAL, true, "Use Funwayguy's pick of commands (Must be connected to the internet. Overwrites local list)");
		RO_Settings.nonDropSelf = config.getBoolean("Non-self Ore Drops", Configuration.CATEGORY_GENERAL, true, "Check whether the ore drop is itself or not. Prevent's exploits on ores like iron");
		RO_Settings.chance = config.getFloat("Roulette Chance", Configuration.CATEGORY_GENERAL, 0.01F, 0.0F, 1.0F, "Chance a random command will be run");
		String[] tmpList = config.getStringList("Roulette Commands", Configuration.CATEGORY_GENERAL, defComs, "List of possible commands (Turn off online defaults if editing)");
		
		Property prop = config.get(Configuration.CATEGORY_GENERAL, "Roulette Commands", defComs, "List of possible commands (Turn off online defaults if editing)");
		
		if(RO_Settings.useOnline)
		{
			String[] onlineList = null;
			
			try
			{
				onlineList = UpdateNotification.getNotification("https://raw.githubusercontent.com/Funwayguy/RouletteOre-Defaults/master/DefaultCommands.txt", false);
			} catch(Exception e)
			{
				RouletteOres.logger.log(Level.WARN, "Unable to load online defaults", e);
			}
			
			if(onlineList != null && onlineList.length > 0)
			{
				prop.set(onlineList);
				tmpList = onlineList;
				RouletteOres.logger.log(Level.INFO, "Loaded online defaults");
			}
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(String com : tmpList)
		{
			list.add(com);
		}
		
		RO_Settings.commands = list;
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
	
	static
	{
		defComs = new String[]
		{
				"/tell {player} You just murdered an innocent ore :(",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"NOPE.\"}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
				"/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:16}",
				"/summon PrimedTnt ~ ~ ~ {Fuse:380,Riding:{id:XPOrb}}",
				"/setblock ~ ~ ~ minecraft:bedrock",
				"/summon Item ~ ~ ~ {Item:{id:264,Count:1}}",
				"/summon Silverfish ~ ~ ~ {CustomName:\"Troll Fish\",CustomNameVisible:10,ActiveEffects:[{Id:11,Amplifier:10,Duration:300}]}",
				"/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"Poison Fish\",CustomNameVisible:10,id:Silverfish},Potion:{id:373,Count:1,Damage:16388}}",
				"/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"DEATH FISH\",CustomNameVisible:10,id:Silverfish},Potion:{id:373,Count:1,Damage:16428}}",
		        "/summon ThrownPotion ~ ~ ~ {Potion:{id:373,Damage:16396,Count:1}}",
		        "/summon ThrownExpBottle ~ ~ ~",
		        "/summon PrimedTnt ~ ~ ~ {Fuse:380,Riding:{CustomName:\"Hugs!\",CustomNameVisible:10,id:Zombie,IsBaby:1}}",
		        "/setblock ~ ~ ~ minecraft:mob_spawner 0 replace {EntityId:Zombie}",
		        "/setblock ~ ~ ~ minecraft:flowing_water",
		        "/setblock ~ ~ ~ minecraft:flowing_lava",
		        "/summon EnderCrystal ~ ~ ~ {Riding:{id:Bat}}",
		        "/summon EnderCrystal ~ ~ ~",
		        "/setblock ~ ~ ~ minecraft:cake",
		        "/tellraw {player} {\"text\":\"\",\"extra\":[{\"text\":\"Herobrine joined the game\",\"color\":\"yellow\"}]}",
		        "/playsound ambient.cave.cave {player} ~ ~ ~ 1.0 0.01",
		        "/playsound random.explode {player}",
		        "/playsound game.tnt.primed {player}",
		        "/setblock ~ ~ ~ minecraft:fire",
		        "/summon Item ~ ~ ~ {Item:{id:311,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:3,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Chestplate},ench:[{id:0,lvl:3}]}}}",
		        "/summon Item ~ ~ ~ {Item:{id:310,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:2,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Helmet}}}}",
		        "/summon Item ~ ~ ~ {Item:{id:312,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:3,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Leggings}}}}",
		        "/summon Item ~ ~ ~ {Item:{id:313,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:2,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Boots}}}}",
		        "/summon Item ~ ~ ~ {Item:{id:276,Count:1,tag:{display:{Name:Buff Sword},ench:[{id:16,lvl:5},{id:20,lvl:1},{id:21,lvl:3},{id:34,lvl:3}]}}}",
		        "/setblock ~ ~ ~ minecraft:mob_spawner 0 replace {EntityId:ThrownPotion,SpawnData:{Riding:{id:XPOrb},Potion:{id:373,Damage:8228,Count:1}},SpawnCount:1,SpawnRange:16,RequiredPlayerRange:24,MinSpawnDelay:30,MaxSpawnDelay:30,MaxNearbyEntities:10}"
		};
	}
}
