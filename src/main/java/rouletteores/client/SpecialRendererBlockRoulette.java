package rouletteores.client;

import org.lwjgl.opengl.GL11;
import rouletteores.blocks.BlockRoulette;
import rouletteores.blocks.tiles.TileEntityRoulette;
import rouletteores.core.RouletteOres;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpecialRendererBlockRoulette implements ISimpleBlockRenderingHandler
{
	TileEntityRoulette tile = new TileEntityRoulette();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if(block != RouletteOres.oreRoulette)
		{
			return;
		}
		
		tile.blockMetadata = metadata;
		tile.blockType = block;
		
		GL11.glPushMatrix();
		
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return BlockRoulette.renderID;
	}
}
