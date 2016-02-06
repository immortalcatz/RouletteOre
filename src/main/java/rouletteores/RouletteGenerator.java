package rouletteores;

import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;

public class RouletteGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(!RO_Settings.genRoulette)
		{
			return;
		}
		
		WorldGenMinable rGen = new WorldGenMinable(RouletteOres.oreRoulette.getDefaultState(), 1);
		
		for(int i = 0; i < 4; i++)
		{
			int x = chunkX*16 + rand.nextInt(16);
			int y = rand.nextInt(128);
			int z = chunkZ*16 + rand.nextInt(16);
			
			rGen.generate(world, rand, new BlockPos(x, y, z));
		}
	}
}
