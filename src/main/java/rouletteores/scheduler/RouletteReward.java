package rouletteores.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import rouletteores.JsonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class RouletteReward
{
	protected String name = "New Reward";
	protected HashMap<Integer,ArrayList<String>> schedule = new HashMap<Integer,ArrayList<String>>();
	protected ArrayList<String> mods = new ArrayList<String>();
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<String> getCommandsAtTime(int tickTime)
	{
		return schedule.get(tickTime);
	}
	
	public int getDuration()
	{
		int n = 0;
		
		for(Integer i : schedule.keySet())
		{
			n = Math.max(i, n);
		}
		
		return n;
	}
	
	public void readFromJson(JsonObject json)
	{
		name = JsonHelper.GetString(json, "name", "New Reward");
		
		schedule.clear();
		for(JsonElement entry : JsonHelper.GetArray(json, "events"))
		{
			if(entry == null || !entry.isJsonObject())
			{
				continue;
			}
			
			JsonObject j = entry.getAsJsonObject();
			
			int time = JsonHelper.GetNumber(j, "delay", 0).intValue();
			String cmd = JsonHelper.GetString(j, "command", "");
			
			if(schedule.containsKey(time))
			{
				schedule.get(time).add(cmd);
			} else
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(cmd);
				schedule.put(time, list);
			}
		}
		
		mods.clear();
		for(JsonElement entry : JsonHelper.GetArray(json, "req_mods"))
		{
			if(entry == null || !entry.isJsonPrimitive() || !entry.getAsJsonPrimitive().isString())
			{
				continue;
			}
			
			mods.add(entry.getAsString());
		}
	}
	
	public void writeToJson(JsonObject json)
	{
		json.addProperty("name", name);
		
		JsonArray reqMods = new JsonArray();
		for(String s : mods)
		{
			reqMods.add(new JsonPrimitive(s));
		}
		json.add("req_mods", reqMods);
		
		JsonArray jRewards = new JsonArray();
		for(Entry<Integer,ArrayList<String>> entry : schedule.entrySet())
		{
			for(String c : entry.getValue())
			{
				JsonObject jRwd = new JsonObject();
				
				jRwd.addProperty("delay", entry.getKey());
				jRwd.addProperty("command", c);
				jRewards.add(jRwd);
			}
		}
		json.add("events", jRewards);
	}
}
