package rouletteores.handlers;

import java.util.List;
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
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import rouletteores.scheduler.RouletteEvent;
import rouletteores.scheduler.RouletteRewardRegistry;
import rouletteores.scheduler.RouletteScheduler;

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
		
		String blockID = Block.blockRegistry.getNameForObject(event.state.getBlock()).toString();
		int blockMeta = event.state.getBlock().getMetaFromState(event.state);
		boolean custom = RO_Settings.extraOres.contains(blockID) || RO_Settings.extraOres.contains(blockID + ":" + blockMeta);
		
		if(!event.world.isRemote && event.harvester != null && event.harvester instanceof EntityPlayer && (RO_Settings.fakePlayers || !(event.harvester instanceof FakePlayer)))
		{
			String[] nameParts = event.state.getBlock().getUnlocalizedName().split("\\.");
			
			boolean flag = event.state.getBlock() instanceof BlockOre || custom;
			
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
				List<ItemStack> drops = event.state.getBlock().getDrops(event.world, event.pos, event.state, event.fortuneLevel);
				
				for(ItemStack item : drops)
				{
					if(item != null && item.getItem() == Item.getItemFromBlock(event.state.getBlock()) && item.getItemDamage() == blockMeta)
					{
						flag = false;
						break;
					}
				}
			}
			
			if(flag && (event.state.getBlock() == RouletteOres.oreRoulette || event.world.rand.nextFloat() < RO_Settings.chance * (RO_Settings.fortuneMult? event.fortuneLevel + 1F : 1F)))
			{
				RouletteEvent re = new RouletteEvent(event.harvester.getName(), event.pos, RouletteRewardRegistry.getRandomReward(event.world.rand));
				
				if(re != null)
				{
					RouletteScheduler.events.add(re);
					RouletteOres.logger.log(Level.INFO, "Player " + event.harvester.getName() + " triggered event: " + re.reward.getName());
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
