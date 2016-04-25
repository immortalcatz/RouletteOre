package rouletteores.scheduler;

import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import rouletteores.core.RouletteOres;
import rouletteores.handlers.OreCommandSender;

public class RouletteEvent
{
	BlockPos pos;
	public int tick = 0;
	public String target;
	public RouletteReward reward;
	
	public RouletteEvent(String target, BlockPos pos, RouletteReward reward)
	{
		this.target = target;
		this.reward = reward;
		this.tick = 0;
		this.pos = pos;
	}
	
	public void tickEvent(EntityPlayer target)
	{
		if(target == null || target.worldObj.isRemote)
		{
			return;
		}
		
		ArrayList<String> commands = reward.getCommandsAtTime(tick);
		
		if(commands != null)
		{
			for(String tmp : commands)
			{
				try
				{
					String cmd = tmp.replaceAll("VAR_NAME", target.getName()).trim();
					
					OreCommandSender sender = new OreCommandSender(target.worldObj, pos);
					MinecraftServer server = target.getServer();
					GameRules rules = server.worldServerForDimension(0).getGameRules();
					Boolean rule = rules.getBoolean("commandBlockOutput");
					rules.setOrCreateGameRule("commandBlockOutput", "false");
					
					server.getCommandManager().executeCommand(sender, cmd);
					
					rules.setOrCreateGameRule("commandBlockOutput", rule.toString());
				} catch(Exception e)
				{
					RouletteOres.logger.log(Level.ERROR, "Unable to perform roulette event! name=" + reward.name + ", tick=" + tick + ", command=" + tmp, e);
				}
			}
		}
		
		tick++;
	}
}
