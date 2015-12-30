package rouletteores.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.Loader;

public class RouletteRewardRegistry
{
	static ArrayList<RouletteReward> rewards = new ArrayList<RouletteReward>();
	
	public static void registerReward(RouletteReward r)
	{
		if(r == null || rewards.contains(r) || r.schedule.size() <= 0)
		{
			return;
		}
		
		for(String mod : r.mods)
		{
			if(!Loader.isModLoaded(mod))
			{
				return; // This reward requires one or more mods that aren't loaded
			}
		}
		
		rewards.add(r);
	}
	
	/**
	 * Converts old configuration file rewards to the new system.
	 */
	public static void readLegacyReward(String command)
	{
		RouletteReward reward = new RouletteReward();
		command = command.replaceAll("\\{player\\}", "VAR_NAME");
		reward.schedule.put(0, new ArrayList<String>(Arrays.asList(command.split(";;"))));
		reward.name = "Legacy Reward";
		registerReward(reward);
	}
	
	public static RouletteReward getRandomReward(Random rand)
	{
		if(rewards == null || rewards.size() <= 0)
		{
			return null;
		}
		
		return rewards.get(rand.nextInt(rewards.size()));
	}
	
	public static void loadRewards(JsonArray json, boolean append)
	{
		if(!append)
		{
			rewards.clear();
		}
		
		for(JsonElement e : json)
		{
			if(e == null || !e.isJsonObject())
			{
				continue;
			}
			
			RouletteReward r = new RouletteReward();
			r.readFromJson(e.getAsJsonObject());
			registerReward(r);
		}
	}
	
	public static void saveRewards(JsonArray json)
	{
		for(RouletteReward r : rewards)
		{
			JsonObject j = new JsonObject();
			r.writeToJson(j);
			json.add(j);
		}
	}
}
