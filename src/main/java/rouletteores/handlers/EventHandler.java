package rouletteores.handlers;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandler
{
	public World lastWorld;
	public int lastX = 0;
	public int lastY = 0;
	public int lastZ = 0;
	
	@SubscribeEvent
	public void onHarvest(HarvestDropsEvent event)
	{
		if(event.isSilkTouching && RO_Settings.silkImmunity)
		{
			return;
		}
		
		String blockID = Block.blockRegistry.getNameForObject(event.block);
		boolean custom = RO_Settings.extraOres.contains(blockID) || RO_Settings.extraOres.contains(blockID + ":" + event.blockMetadata);
		
		if(!event.world.isRemote && event.harvester != null && event.harvester instanceof EntityPlayer && (RO_Settings.fakePlayers || !(event.harvester instanceof FakePlayer)))
		{
			String[] nameParts = event.block.getUnlocalizedName().split("\\.");
			
			boolean flag = event.block instanceof BlockOre || custom;
			
			if(!flag)
			{
				for(String part : nameParts)
				{
					if(part.toLowerCase().startsWith("ore") || part.toLowerCase().endsWith("ore"))
					{
						flag = true;
						break;
					}
				}
			}
			
			if(RO_Settings.nonDropSelf && !custom)
			{
				ArrayList<ItemStack> drops = event.block.getDrops(event.world, event.x, event.y, event.z, event.blockMetadata, event.fortuneLevel);
				
				for(ItemStack item : drops)
				{
					if(item != null && item.getItem() == Item.getItemFromBlock(event.block) && item.getItemDamage() == event.blockMetadata)
					{
						flag = false;
						break;
					}
				}
			}
			
			if(flag)
			{
				MinecraftServer server = MinecraftServer.getServer();
				
				if(server != null && RO_Settings.commands.size() > 0 && (event.block == RouletteOres.oreRoulette || event.world.rand.nextFloat() < RO_Settings.chance * (RO_Settings.fortuneMult? event.fortuneLevel : 1F)))
				{
					lastWorld = event.world;
					lastX = event.x;
					lastY = event.y;
					lastZ = event.z;
					
					String[] commands = RO_Settings.commands.get(event.world.rand.nextInt(RO_Settings.commands.size())).split(";;");
					
					for(String cmd : commands)
					{
						cmd = cmd.replaceAll("\\{player\\}", event.harvester.getCommandSenderName()).trim();
						
						OreCommandSender sender = new OreCommandSender(event.harvester, event.x, event.y, event.z);
						
						Boolean rule = MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
						MinecraftServer.getServer().worldServers[0].getGameRules().setOrCreateGameRule("commandBlockOutput", "false");
						server.getCommandManager().executeCommand(sender, cmd);
						MinecraftServer.getServer().worldServers[0].getGameRules().setOrCreateGameRule("commandBlockOutput", rule.toString());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(RouletteOres.MODID))
		{
			ConfigHandler.config.save();
			ConfigHandler.initConfigs();
		}
	}
}
