package rouletteores;

import java.util.Random;
import rouletteores.core.RO_Settings;
import rouletteores.core.RouletteOres;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class RouletteGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(!RO_Settings.genRoulette)
		{
			return;
		}
		
		WorldGenMinable rGen = new WorldGenMinable(RouletteOres.oreRoulette, 1);
		
		for(int i = 0; i < 4; i++)
		{
			int x = chunkX*16 + rand.nextInt(16);
			int y = rand.nextInt(128);
			int z = chunkZ*16 + rand.nextInt(16);
			
			rGen.generate(world, rand, x, y, z);
		}
	}
}
