package de.alpha.uhc.aclasses;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.utils.LobbyPasteUtil;

public class AWorld {
	
	public static boolean lobbyAsSchematic;

	private Location location;
	private final String reset = "UHC_ARENA";
	private int numberOfArenas = 3;
	
	public static void performReset() {
		//TODO
	}
}
