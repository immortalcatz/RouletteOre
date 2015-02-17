package rouletteores.handlers;

import java.util.ArrayList;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandler
{
	public World lastWorld;
	public int lastX = 0;
	public int lastY = 0;
	public int lastZ = 0;
	
	@SubscribeEvent
	public void onHarvest(HarvestDropsEvent event)
	{
		if(!event.world.isRemote && event.harvester != null && event.harvester instanceof EntityPlayer && (RO_Settings.fakePlayers || !(event.harvester instanceof FakePlayer)))
		{
			String[] nameParts = event.block.getUnlocalizedName().split("\\.");
			
			boolean flag = event.block instanceof BlockOre;
			
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
			
			if(RO_Settings.nonDropSelf)
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
				
				if(server != null && RO_Settings.commands.size() > 0 && event.world.rand.nextFloat() < RO_Settings.chance)
				{
					String command = RO_Settings.commands.get(event.world.rand.nextInt(RO_Settings.commands.size()));
					command = command.replaceAll("\\{player\\}", event.harvester.getCommandSenderName());
					
					lastWorld = event.world;
					lastX = event.x;
					lastY = event.y;
					lastZ = event.z;
					
					OreCommandSender sender = new OreCommandSender()
					{
				        private final World world = EventHandler.this.lastWorld;
				        private final int posX = EventHandler.this.lastX;
				        private final int posY = EventHandler.this.lastY;
				        private final int posZ = EventHandler.this.lastZ;
				        
				        
				        /**
				         * Return the position for this command sender.
				         */
				        public ChunkCoordinates getPlayerCoordinates()
				        {
				            return new ChunkCoordinates(posX, posY, posZ);
				        }
				        public World getEntityWorld()
				        {
				            return world;
				        }
				        public void func_145752_a(String p_145752_1_)
				        {
				            super.func_145752_a(p_145752_1_);
				        }
				        public void func_145756_e()
				        {
				        	world.markBlockForUpdate(posX, posY, posZ);
				        }
				        @SideOnly(Side.CLIENT)
				        public int func_145751_f()
				        {
				            return 0;
				        }
				        @SideOnly(Side.CLIENT)
				        public void func_145757_a(ByteBuf p_145757_1_)
				        {
				            p_145757_1_.writeInt(posX);
				            p_145757_1_.writeInt(posY);
				            p_145757_1_.writeInt(posZ);
				        }
					};
					
					sender.func_145754_b(RouletteOres.NAME);
					
					server.getCommandManager().executeCommand(sender, command);
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
