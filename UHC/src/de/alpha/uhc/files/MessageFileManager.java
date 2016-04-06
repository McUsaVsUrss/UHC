package de.alpha.uhc.files;

import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.Listener.MotdListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.aclasses.AGame;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.manager.BorderManager;
import de.alpha.uhc.timer.Timer;
import net.minetopix.library.main.file.SimpleFile;

public class MessageFileManager {
	
	
	public static SimpleFile getMSGFile() {
        return new SimpleFile("plugins/UHC", "messages.yml");
    }
	
	public static void addMessages() {
		SimpleFile file = getMSGFile();
		
		file.setDefault("Commands.Warns.OnlyPlayers", "&cThis Command have to be executed by a Player.");
		file.setDefault("Commands.Warns.NoPermissions", "&cYou don't have Permission to execute this Command.");
		file.setDefault("Commands.Admin.SpawnSet", "&aYou have sucessfully set the Spawn.");
		file.setDefault("Commands.Admin.LobbySet", "&aYou have sucessfully set the Lobby.");
		
		file.setDefault("Announcements.Countdown", "&aGame starts in &7[time]&8 seconds");
		file.setDefault("Announcements.NotEnoughPlayers", "§cCountdown reloaded! Not enough Players online.");
		file.setDefault("Announcements.Peaceperiod.timer", "§aThe damage is enabled in &7[time] &8seconds");
		file.setDefault("Announcements.DeathMatch.timer", "§aThe deathmatch starts in &7[time] &8minutes");
		file.setDefault("Announcements.PvP.timer", "§aPvP is enabled in &7[time] &8minutes");
		file.setDefault("Announcements.PvP.end", "§aPvP is now enabled");
		file.setDefault("Announcements.Peaceperiod.end", "&cThe Damage is now on. Be Careful!");
		file.setDefault("Announcements.Leave", "&6[Player]&c had left. [PlayerCount]");
		file.setDefault("Announcements.Join", "&6[Player]&a has joined. [PlayerCount]");
		file.setDefault("Announcements.Win", "&6[Player]&a has won UHC.");
		file.setDefault("Announcements.Border.Move", "&c The Border has moved!");
		file.setDefault("Announcements.Restart", "&cThe Server is now restarting to load UHC again!");
		file.setDefault("Announcements.End", "&cServer stops in &7[time]&8 seconds");
		
		file.setDefault("Warns.FullServer", "&cYou're not allowed to join. The Server is full.");
		
		file.setDefault("Compass.NoPlayerInRange", "&cThere is no Player in your Range!");
		file.setDefault("Compass.PlayerInRange", "&6[Player] &ais §7[distance] blocks &aaway from you.");
		file.setDefault("Compass.TeamPlayerInRange", "§aYour Teammate &6[Player] &ais §7[distance] blocks &aaway from you.");
		
		file.setDefault("Join.Title", "&aHello [Player]");
		file.setDefault("Join.Subtitle", "&7and welcome to UHC");
		
		file.setDefault("Kits.GUI.Title", "&7[&bKits&7]");
		file.setDefault("Kits.GUI.Selected", "&aYou selected &6[Kit]");
		file.setDefault("Kits.GUI.Bought", "&aYou bought &6[Kit] for &c[Coins] Coins");
		file.setDefault("Kits.GUI.No Coins", "&aYou need more Coins");
		
		file.setDefault("Teams.do not exist", "&7The Team &c[team]&7 do not exist!");
		file.setDefault("Teams.all", "&7Teams: [teams]");
		file.setDefault("Teams.chosen", "&aYou are now in Team [team]");
		file.setDefault("Teams.full", "&cThe team [team] is full.");
		
		file.setDefault("Reward", "&aYou got [Coins] Coins.");
		
		file.setDefault("Motd.Lobby", "&bUHC \n &aJoinable!");
		file.setDefault("Motd.Grace", "&bUHC \n &dGraceperiod!");
		file.setDefault("Motd.InGame", "&bUHC \n &cInGame!");
		file.setDefault("Motd.Restart", "&bUHC \n &5Restart!");
		
		file.setDefault("Tablist.Top", "&b>> &cWelcome [player] &con the UHC &b<< \n &7Players:");
		file.setDefault("Tablist.Bottom", "&b>> &cThere are currently &4[playercount] &cplayers online &b<< \n &b>> &cCurrent Gamestate: &4[gamestatus] &b<<");
		
		file.setDefault("GameStatus.Lobby", "Lobby");
		file.setDefault("GameStatus.Grace", "Grace");
		file.setDefault("GameStatus.No PvP", "Preparing");
		file.setDefault("GameStatus.InGame", "InGame");
		file.setDefault("GameStatus.Deathmatch", "Deathmatch");
		file.setDefault("GameStatus.Restart", "Restart");
	}
	
	public static void loadMessages() {
		SimpleFile file = getMSGFile();
		
		ATablist.header = file.getColorString("Tablist.Top");
		ATablist.footer = file.getColorString("Tablist.Bottom");
		
		GState.lobby = file.getString("GameStatus.Lobby");
		GState.grace = file.getString("GameStatus.Grace");
		GState.pregame = file.getString("GameStatus.No PvP");
		GState.ingame = file.getString("GameStatus.InGame");
		GState.deathmatch = file.getString("GameStatus.Deathmatch");
		GState.restart = file.getString("GameStatus.Restart");
		
		MotdListener.lobby = file.getColorString("Motd.Lobby");
		MotdListener.grace = file.getColorString("Motd.Grace");
		MotdListener.ingame = file.getColorString("Motd.InGame");
		MotdListener.restart = file.getColorString("Motd.Restart");
		
		ATeam.chosen = file.getColorString("Teams.chosen");
		ATeam.noExist = file.getColorString("Teams.do not exist");
		ATeam.allTeams = file.getColorString("Teams.all");
		ATeam.full = file.getColorString("Teams.full");
		
		LobbyListener.sel = file.getColorString("Kits.GUI.Selected");
		LobbyListener.bought = file.getColorString("Kits.GUI.Bought");
		LobbyListener.coinsneed = file.getColorString("Kits.GUI.No Coins");
		
		UHCCommand.noplayer = file.getColorString("Commands.Warns.OnlyPlayers");
		UHCCommand.noperms = file.getColorString("Commands.Warns.NoPermissions");
		UHCCommand.spawnset = file.getColorString("Commands.Admin.SpawnSet");
		UHCCommand.lobbyset = file.getColorString("Commands.Admin.LobbySet");
		
		Timer.pvpstart = file.getColorString("Announcements.PvP.end");
		Timer.pvpmsg = file.getColorString("Announcements.PvP.timer");
		Timer.dmmsg = file.getColorString("Announcements.DeathMatch.timer");
		Timer.countmsg = file.getColorString("Announcements.Countdown");
		Timer.nep = file.getColorString("Announcements.NotEnoughPlayers");
		Timer.gracemsg = file.getColorString("Announcements.Peaceperiod.timer");
		Timer.end = file.getColorString("Announcements.Peaceperiod.end");
		Timer.endmsg = file.getColorString("Announcements.End");
		
		GameEndListener.win = file.getColorString("Announcements.Win");
		GameEndListener.kick = file.getColorString("Announcements.Restart");
		GameEndListener.rew = file.getColorString("Reward");
		GameEndListener.quit = file.getColorString("Announcements.Leave");
		
		Timer.kick = GameEndListener.kick;
		
		InGameListener.ntrack = file.getColorString("Compass.NoPlayerInRange");
		InGameListener.track = file.getColorString("Compass.PlayerInRange");
		InGameListener.trackteam = file.getColorString("Compass.TeamPlayerInRange");

		AGame.join = file.getColorString("Announcements.Join");
		AGame.full = file.getColorString("Warns.FullServer");
		AGame.title = file.getColorString("Join.Title");
		AGame.subtitle = file.getColorString("Join.Subtitle");
		
		BorderManager.moved = file.getColorString("Announcements.Border.Move");
		 
		GUI.title = file.getColorString("Kits.GUI.Title");
		 
	}

}
