package rouletteores.scheduler;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import rouletteores.handlers.OreCommandSender;

public class RouletteEvent
{
	public int x;
	public int y;
	public int z;
	public int tick = 0;
	public String target; // Name used instead of the instance so it resumes after teleporting
	public RouletteReward reward;
	
	public RouletteEvent(String target, int x, int y, int z, RouletteReward reward)
	{
		this.target = target;
		this.reward = reward;
		this.tick = 0;
		this.x = x;
		this.y = y;
		this.z = z;
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
				cmd = cmd.replaceAll("VAR_NAME", target.getCommandSenderName()).trim(); // New system can't use brackets
				
				OreCommandSender sender = new OreCommandSender(target.worldObj, x, y, z);
				MinecraftServer server = MinecraftServer.getServer();
				GameRules rules = server.worldServerForDimension(0).getGameRules();
				Boolean rule = rules.getGameRuleBooleanValue("commandBlockOutput");
				rules.setOrCreateGameRule("commandBlockOutput", "false");
				
				server.getCommandManager().executeCommand(sender, cmd);
				
				rules.setOrCreateGameRule("commandBlockOutput", rule.toString());
			}
		}
		
		tick++;
	}
}
