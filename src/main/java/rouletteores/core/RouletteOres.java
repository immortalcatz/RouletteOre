package rouletteores.core;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import rouletteores.blocks.BlockRoulette;
import rouletteores.blocks.tiles.TileEntityRoulette;
import rouletteores.core.proxies.CommonProxy;
import rouletteores.handlers.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RouletteOres.MODID, version = RouletteOres.VERSION, name = RouletteOres.NAME, guiFactory = "rouletteores.handlers.ConfigGuiFactory")
public class RouletteOres
{
    public static final String MODID = "rouletteores";
    public static final String VERSION = "RO_VER_KEY";
    public static final String NAME = "RouletteOres";
    public static final String PROXY = "rouletteores.core.proxies";
    public static final String CHANNEL = "RO_NET_CHAN";
	
	@Instance(MODID)
	public static RouletteOres instance;
	
	@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	
	public static Block oreRoulette;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	oreRoulette = new BlockRoulette();
    	GameRegistry.registerBlock(oreRoulette, "ore_roulette");
    	GameRegistry.registerTileEntity(TileEntityRoulette.class, "tile.rouletteores.ore");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
