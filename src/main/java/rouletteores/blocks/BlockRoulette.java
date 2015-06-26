package rouletteores.blocks;

import java.util.Random;
import rouletteores.blocks.tiles.TileEntityRoulette;
import rouletteores.client.SpecialRendererBlockRoulette;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRoulette extends Block implements ITileEntityProvider
{
	public BlockRoulette()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockTextureName("stone");
		this.setHardness(3F);
		this.setResistance(5F);
		this.setBlockName("roulette_ore");
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityRoulette();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return SpecialRendererBlockRoulette.renderID;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}