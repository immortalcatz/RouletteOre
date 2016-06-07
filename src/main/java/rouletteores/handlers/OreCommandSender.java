package rouletteores.handlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rouletteores.core.RouletteOres;

public class OreCommandSender extends CommandBlockBaseLogic
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
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(this.getName());
    }

    @Override
    public void addChatMessage(ITextComponent p_145747_1_)
    {
        if (this.getEntityWorld() != null && !this.getEntityWorld().isRemote)
        {
        	IBlockState state = this.getEntityWorld().getBlockState(blockLoc);
        	this.getEntityWorld().notifyBlockUpdate(blockLoc, state, state, 3);
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
    public int getCommandBlockType()
    {
    	return 0; // Unknown purpose
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void fillInInfo(ByteBuf p_145757_1_){};
    
    @Override
    public void setLastOutput(ITextComponent p_145750_1_){}

	@Override
	public Vec3d getPositionVector()
	{
		return new Vec3d(blockLoc.getX() + 0.5D, blockLoc.getY() + 0.5D, blockLoc.getZ() + 0.5D);
	}

	@Override
	public Entity getCommandSenderEntity()
	{
		return null;
	}
	
	@Override
	public MinecraftServer getServer()
	{
		return world.getMinecraftServer();
	}
}
