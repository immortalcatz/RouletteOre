package rouletteores.core;

import java.util.ArrayList;


/**
 * A container for all the configurable settings in the mod
 */
public class RO_Settings
{
	public static float chance = 0.01F;
	public static boolean nonDropSelf = true;
	public static boolean hideUpdates = false;
	public static boolean useOnline = true;
	public static boolean fakePlayers = false;
	public static boolean silkImmunity = true;
	public static boolean fortuneMult = true;
	public static boolean genRoulette = true;
	public static int orePerChunk = 4;
	public static ArrayList<String> extraOres = new ArrayList<String>();
}
