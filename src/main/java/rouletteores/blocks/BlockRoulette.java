package rouletteores.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoulette extends Block
{
	public BlockRoulette()
	{
		super(Material.ROCK);
		this.setCreativeTab(CreativeTabs.MISC);
		this.setHardness(3F);
		this.setResistance(5F);
		this.setUnlocalizedName("roulette_ore");
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}