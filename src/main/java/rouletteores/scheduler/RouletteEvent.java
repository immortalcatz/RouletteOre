package rouletteores.scheduler;

import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.GameRules;
import rouletteores.core.RouletteOres;
import rouletteores.handlers.OreCommandSender;

public class RouletteEvent
{
	BlockPos pos;
	public int tick = 0;
	public String target; // Name used instead of the instance so it resumes after teleporting
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
			for(String cmd : commands)
			{
				cmd = cmd.replaceAll("VAR_NAME", target.getName()).trim(); // New system can't use brackets
				
				RouletteOres.logger.log(Level.INFO, "Running comand: " + cmd);
				
				OreCommandSender sender = new OreCommandSender(target.worldObj, pos);
				MinecraftServer server = MinecraftServer.getServer();
				GameRules rules = server.worldServerForDimension(0).getGameRules();
				Boolean rule = rules.getBoolean("commandBlockOutput");
				rules.setOrCreateGameRule("commandBlockOutput", "false");
				
				server.getCommandManager().executeCommand(sender, cmd);
				
				rules.setOrCreateGameRule("commandBlockOutput", rule.toString());
			}
		}
		
		tick++;
	}
}
