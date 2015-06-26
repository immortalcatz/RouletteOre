package rouletteores.handlers;

import java.util.Arrays;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;

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
		RO_Settings.fakePlayers = config.getBoolean("Enable Fake Players", Configuration.CATEGORY_GENERAL, false, "Enable machines using 'fake' player instances to trigger RouletteOres");
		RO_Settings.silkImmunity = config.getBoolean("Silk Immunity", Configuration.CATEGORY_GENERAL, true, "Using silk touch on ores prevents triggering events");
		RO_Settings.fortuneMult = config.getBoolean("Fortune Multiplier", Configuration.CATEGORY_GENERAL, true, "Fortune enchantments multiply the chance of triggering an ore");
		RO_Settings.genRoulette = config.getBoolean("Generate Ores", Configuration.CATEGORY_GENERAL, true, "Generate the mod's native Roulette Ore");
		RO_Settings.commands.clear();
		RO_Settings.commands.addAll(Arrays.asList(config.getStringList("Roulette Commands", Configuration.CATEGORY_GENERAL, defComs, "List of possible commands (Turn off online defaults if editing)(use ';;' to split multiple commands)")));
		RO_Settings.extraOres.clear();
		RO_Settings.extraOres.addAll(Arrays.asList(config.getStringList("Extra Ores", Configuration.CATEGORY_GENERAL, new String[]{}, "Additional blocks that should be treated as 'ores' (Ignores non-self drops)")));
		
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
				RO_Settings.commands.clear();
				RO_Settings.commands.addAll(Arrays.asList(onlineList));
				RouletteOres.logger.log(Level.INFO, "Loaded online default commands");
			}
		}
		
		config.save();
		
		System.out.println("Loaded configs...");
	}
	
	static
	{
		defComs = new String[]
		{
				"/tell {player} You just murdered an innocent ore :(",
		        "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
		        "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
		        "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
		        "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:0}",
		        "/summon Creeper ~ ~ ~ {powered:1,CustomName:\"Roulette Creeper\",ExplosionRadius:16}",
		        "/summon PrimedTnt ~ ~ ~ {Fuse:380,Riding:{id:XPOrb}}",
		        "/setblock ~ ~ ~ minecraft:bedrock",
		        "/summon Item ~ ~ ~ {Item:{id:263,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:264,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:265,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:266,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:289,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:318,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:331,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:351,Count:1,Damage:4}}",
		        "/summon Item ~ ~ ~ {Item:{id:352,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:371,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:388,Count:1}}",
		        "/summon Item ~ ~ ~ {Item:{id:406,Count:1}}",
		        "/summon Silverfish ~ ~ ~ {CustomName:\"Troll Fish\",CustomNameVisible:10,ActiveEffects:[{Id:11,Amplifier:10,Duration:300}]}",
		        "/summon Zombie ~ ~ ~ {CustomName:\"Gary Coleman\",IsBaby:1,CustomNameVisible:10}",
		        "/summon Chicken ~ ~ ~ {CustomName:\"Cluckington\",CustomNameVisible:10}",
		        "/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"Poison Fish\",CustomNameVisible:10,id:Silverfish},Potion:{id:373,Count:1,Damage:16388}}",
		        "/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"Death Fish\",CustomNameVisible:10,id:Silverfish},Potion:{id:373,Count:1,Damage:16428}}",
		        "/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"Blinding Fish\",CustomNameVisible:10,id:Silverfish},Potion:{id:1,Damage:0,tag:{CustomPotionEffects:[{Id:15,Amplifier:0,Duration:900,ShowParticles:0b}]}}}",
		        "/summon ThrownPotion ~ ~ ~ {Riding:{CustomName:\"Paralysis Fish\",CustomNameVisible:10,id:Silverfish},Potion:{id:1,Damage:16458,tag:{CustomPotionEffects:[{Id:2,Amplifier:10,Duration:900,ShowParticles:0b}]}}}",
		        "/summon ThrownPotion ~ ~ ~ {Potion:{id:373,Damage:16396,Count:1}}",
		        "/summon ThrownExpBottle ~ ~ ~",
		        "/summon PrimedTnt ~ ~ ~ {Fuse:380,Riding:{CustomName:\"Hugs!\",CustomNameVisible:10,id:Zombie,IsBaby:1}}",
		        "/setblock ~ ~ ~ minecraft:mob_spawner 0 replace {EntityId:Zombie}",
		        "/setblock ~ ~ ~ minecraft:flowing_water",
		        "/setblock ~ ~ ~ minecraft:flowing_lava",
		        "/summon EnderCrystal ~ ~ ~ {Riding:{id:Bat,CustomName:\"Bat Bomb\"}}",
		        "/summon EnderCrystal ~ ~ ~",
		        "/setblock ~ ~ ~ minecraft:cake",
		        "/tellraw {player} {\"text\":\"\",\"extra\":[{\"text\":\"Herobrine joined the game\",\"color\":\"yellow\"}]}",
		        "/tellraw {player} {\"text\":\"<Herobrine> I'm coming for you {player}...\"}",
		        "/tellraw {player} {\"text\":\"<Herobrine> I wonder how fast you can run {player}?\"}",
		        "/playsound ambient.cave.cave {player} ~ ~ ~ 1.0 0.01",
		        "/playsound random.explode {player}",
		        "/playsound game.tnt.primed {player}",
		        "/setblock ~ ~ ~ minecraft:fire",
		        "/setblock ~ ~ ~ minecraft:web",
		        "/setblock ~ ~ ~ minecraft:monster_egg",
		        "/summon Item ~ ~ ~ {Item:{id:311,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:6,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Chestplate},ench:[{id:0,lvl:3}]}}}",
		        "/summon Item ~ ~ ~ {Item:{id:310,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:4,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Helmet},ench:[{id:0,lvl:3}]}}}",
		        "/summon Item ~ ~ ~ {Item:{id:312,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:6,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Leggings},ench:[{id:0,lvl:3}]}}}",
		        "/summon Item ~ ~ ~ {Item:{id:313,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.maxHealth\",Name:\"generic.maxHealth\",Amount:4,Operation:0,UUIDLeast:894654,UUIDMost:2872}],display:{Name:Buff Boots},ench:[{id:0,lvl:3}]}}}",
		        "/summon Item ~ ~ ~ {Item:{id:276,Count:1,tag:{display:{Name:Buff Sword},ench:[{id:16,lvl:5},{id:20,lvl:1},{id:21,lvl:3},{id:34,lvl:3}]}}}",
		        "/setblock ~ ~ ~ minecraft:mob_spawner 0 replace {EntityId:ThrownPotion,SpawnData:{Riding:{id:XPOrb},Potion:{id:373,Damage:8228,Count:1}},SpawnCount:3,SpawnRange:16,RequiredPlayerRange:24,MinSpawnDelay:20,MaxSpawnDelay:20,MaxNearbyEntities:16}",
		        "/summon Zombie ~ ~1 ~ {Riding:{id:ThrownPotion,Potion:{id:15,Damage:16,tag:{CustomPotionEffects:[{Id:15,Amplifier:0,Duration:2400,ShowParticles:0b}]}}},CustomName:\"Herobrine\",CustomNameVisible:1,CanBreakDoors:1,Equipment:[{id:278,Count:1,tag:{ench:[{id:32,lvl:5}]}},{},{},{},{id:397,Damage:3,Count:1,tag:{SkullOwner:Herobrine,ench:[{id:7,lvl:16}]}}],DropChances:[1F,1F,1F,1F,0.0F],Attributes:[{Name:zombie.spawnReinforcements,Base:1.0F}],ActiveEffects:[{Id:5,Amplifier:15,Duration:199980,ShowParticles:0b},{Id:11,Amplifier:10,Duration:2400,ShowParticles:0b}],Attributes:[{Name:generic.maxHealth,Base:200}],Health:200}"
		};
	}
}
