package rouletteores.handlers;

import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import rouletteores.scheduler.RouletteEvent;
import rouletteores.scheduler.RouletteRewardRegistry;
import rouletteores.scheduler.RouletteScheduler;
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
			
			if(flag && (event.block == RouletteOres.oreRoulette || event.world.rand.nextFloat() < RO_Settings.chance * (RO_Settings.fortuneMult? event.fortuneLevel + 1F : 1F)))
			{
				RouletteEvent re = new RouletteEvent(event.harvester.getCommandSenderName(), event.x, event.y, event.z, RouletteRewardRegistry.getRandomReward(event.world.rand));
				
				if(re != null)
				{
					RouletteScheduler.events.add(re);
					RouletteOres.logger.log(Level.INFO, "Player " + event.harvester.getCommandSenderName() + " triggered event: " + re.reward.getName());
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
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if(!event.world.isRemote && !MinecraftServer.getServer().isServerRunning())
		{
			RouletteScheduler.events.clear();
		}
	}
}
