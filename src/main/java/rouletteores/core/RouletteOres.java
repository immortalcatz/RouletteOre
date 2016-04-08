package rouletteores.core;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import rouletteores.blocks.BlockRoulette;
import rouletteores.core.proxies.CommonProxy;
import rouletteores.handlers.ConfigHandler;

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
    	GameRegistry.register(oreRoulette, new ResourceLocation(MODID + ":roulette_ore"));
    	
    	proxy.registerRenderers();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
