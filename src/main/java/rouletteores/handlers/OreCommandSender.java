package rouletteores.handlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rouletteores.core.RouletteOres;

public class OreCommandSender extends CommandBlockLogic
{
	World world;
	BlockPos blockLoc;
    
    public OreCommandSender(World world, BlockPos pos)
    {
    	blockLoc = pos;
    	this.world = world;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(int level, String command)
    {
        return level <= 2;
    }
    
    @Override
    public String getName()
    {
        return RouletteOres.NAME;
    }
    
    @Override
    public IChatComponent getDisplayName()
    {
        return new ChatComponentText(this.getName());
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_)
    {
        if (this.getEntityWorld() != null && !this.getEntityWorld().isRemote)
        {
        	this.getEntityWorld().markBlockForUpdate(blockLoc);
        }
    }

	@Override
	public BlockPos getPosition()
	{
		return blockLoc;
	}

	@Override
	public World getEntityWorld()
	{
		return world;
	}
	
	@Override
    public void updateCommand(){}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int func_145751_f()
    {
    	return 0; // Unknown purpose
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void func_145757_a(ByteBuf p_145757_1_){};
    
    @Override
    public void setLastOutput(IChatComponent p_145750_1_){}

	@Override
	public Vec3 getPositionVector()
	{
		return new Vec3(blockLoc.getX() + 0.5D, blockLoc.getY() + 0.5D, blockLoc.getZ() + 0.5D);
	}

	@Override
	public Entity getCommandSenderEntity()
	{
		return null;
	}
}
