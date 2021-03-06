package de.alpha.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import de.alpha.uhc.Core;
import de.alpha.uhc.utils.Cuboid;
import de.alpha.uhc.utils.Regions;
import net.minetopix.library.main.file.SimpleFile;

public class SpawnFileManager {
	
	public static SimpleFile getSpawnFile() {
		return new SimpleFile("plugins/UHC", "locations.yml");
	}
	
	private static SimpleFile cfg = getSpawnFile();
	
	private static void saveCfg() {cfg.save();}
	
	public static void SetLobby(double x, double y, double z, float yaw, float pitch, World w) {
		
		cfg.set("Lobby.x", x);
		cfg.set("Lobby.y", y);
		cfg.set("Lobby.z", z);
		cfg.set("Lobby.yaw", yaw);
		cfg.set("Lobby.pitch", pitch);
		cfg.set("Lobby.world", w.getName());
		
		cfg.save();
		
	}
	
	public static void SetSpawn(double x, double y, double z, World w) {
		
		cfg.set("Spawn.x", x);
		cfg.set("Spawn.y", y);
		cfg.set("Spawn.z", z);
		cfg.set("Spawn.world", w.getName());
		
		saveCfg();
		
	}
	
	public static Location getLobby() {
		
		if(cfg.getString("Lobby.world") == null) {
			return null;
		}
		
		World w = Bukkit.getWorld(cfg.getString("Lobby.world"));
		int x = cfg.getInt("Lobby.x");
		int y = cfg.getInt("Lobby.y");
		int z = cfg.getInt("Lobby.z");
		float yaw = (float) cfg.getDouble("Lobby.yaw");
		float pitch = (float) cfg.getDouble("Lobby.pitch");
		
		Location l = new Location(w, x, y, z, yaw, pitch);
		
		return l;
		
	}
	
	public static void createSpawnWorld() {
		
		if(cfg.getString("Spawn.world") == null) Bukkit.createWorld(new WorldCreator("UHC"));
		
		if(cfg.getString("Spawn.world") != null) Bukkit.createWorld(new WorldCreator(cfg.getString("Spawn.world")));
		
	}
	
	public static Location getSpawn() {
		
		if(cfg.getString("Spawn.world") == null) {
			World w = Bukkit.getWorld("UHC");
			int x = 0;
			int y = 200;
			int z = 0;
			
			Location l = new Location(w, x, y, z);
			
			return l;
		}
		
		World w = Bukkit.getWorld(cfg.getString("Spawn.world"));
		int x = cfg.getInt("Spawn.x");
		int y = cfg.getInt("Spawn.y");
		int z = cfg.getInt("Spawn.z");
		
		Location l = new Location(w, x, y, z);
		
		return l;
		
	}
	
    public static Location getRandomLocation(Location player, int Xminimum, int Xmaximum, int Zminimum, int Zmaximum) {
    	try {
    		World world = player.getWorld();
    		int randomZ = Zminimum + ((int) (Math.random() * ((Zmaximum - Zminimum) + 1)));
    		double x = Double.parseDouble(Integer.toString(Xminimum + ((int) (Math.random() * ((Xmaximum - Xminimum) + 1))))) + 0.5d;
    		double z = Double.parseDouble(Integer.toString(randomZ)) + 0.5d;
    		player.setY(200);
    		return new Location(world, x, player.getY(), z);
    	} catch (NullPointerException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	public static String getSpawnWorldName() {
		
		if(cfg.getString("Spawn.world") == null) {
			return "UHC";
		}
		
		return cfg.getString("Spawn.world");
	}
	
	public static String getLobbyWorldName() {
		
		if(cfg.getString("Lobby.world") == null) {
			return null;
		}
		
		return cfg.getString("Lobby.world");
	}
	
	
	
	public static void addRegion(Location loc1, Location loc2) {
		
		cfg.set("Lobbyregion.pos1.world", loc1.getWorld().getName());
		cfg.set("Lobbyregion.pos1.x", loc1.getBlockX());
		cfg.set("Lobbyregion.pos1.y", loc1.getBlockY());
		cfg.set("Lobbyregion.pos1.z", loc1.getBlockZ());
		
		cfg.set("Lobbyregion.pos2.world", loc2.getWorld().getName());
		cfg.set("Lobbyregion.pos2.x", loc2.getBlockX());
		cfg.set("Lobbyregion.pos2.y", loc2.getBlockY());
		cfg.set("Lobbyregion.pos2.z", loc2.getBlockZ());
		saveCfg();
	}
	
	public static void registerRegions() {
		
		if(!cfg.isConfigurationSection("Lobbyregion")) {
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "�cYou haven't created a Lobbyregion."); 
			return;
		}
		else if(Bukkit.getWorld(cfg.getString("Lobbyregion.pos1.world")) == null) {
			Bukkit.createWorld(new WorldCreator(cfg.getString("Lobbyregion.pos1.world")));
			return;
		}
		
		Regions.addRegion(new Cuboid(getRegionLoc("pos1"), getRegionLoc("pos2")));
			
	}
	
	public static Location getRegionLoc(String name) {
		
		if(!cfg.isConfigurationSection("Lobbyregion")) {Regions.lobby = false; return null;}
		
		World w =  Bukkit.getWorld(cfg.getString("Lobbyregion." + name + ".world"));
		
		return new Location(w,
				cfg.getInt("Lobbyregion."+name+".x"),
				cfg.getInt("Lobbyregion."+name+".y"),
				cfg.getInt("Lobbyregion."+name+".z"));
	}

}
