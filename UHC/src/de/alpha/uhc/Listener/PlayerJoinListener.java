package de.alpha.uhc.Listener;

import de.alpha.uhc.aclasses.AGame;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.PlayerFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.manager.TitleManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.HoloUtil;
import de.alpha.uhc.utils.Spectator;
import net.minetopix.library.main.item.ItemCreator;
import net.minetopix.mysqlapi.MySQLManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onLeaveClick(PlayerInteractEvent e) {
		if(!(GState.isState(GState.LOBBY))) return;
		if(e.getItem() == null) return;
		if(e.getItem().getType().equals(AGame.leaveItem)) {
			//TODO
		} else if(e.getPlayer().hasPermission("uhc.start") && e.getItem().getType().equals(AGame.startItem)) {
				Timer.changeTime();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ATeam.removePlayerFromTeam(e.getPlayer());
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player all : Core.getSpecs()) {
					AScoreboard.setLobbyScoreboard(all);
				}

				for(Player all : Core.getInGamePlayers()) {
					AScoreboard.setLobbyScoreboard(all);
				}
			}
		}.runTaskLater(Core.getInstance(), 5);
	}
}
