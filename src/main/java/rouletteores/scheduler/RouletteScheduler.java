package rouletteores.scheduler;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class RouletteScheduler
{
	public static ArrayList<RouletteEvent> events = new ArrayList<RouletteEvent>();
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if(event.world.isRemote)
		{
			return;
		}
		
		for(int i = events.size() - 1; i >= 0; i--)
		{
			RouletteEvent e = events.get(i);
			
			EntityPlayer player = event.world.getPlayerEntityByName(e.target);
			
			if(player != null)
			{
				e.tickEvent(player);
			}
			
			if(e.tick > e.reward.getDuration())
			{
				events.remove(i);
				continue;
			}
		}
	}
}
