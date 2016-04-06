package de.alpha.uhc.aclasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.manager.TitleManager;

public class ATablist {
	
	public static String header;
	public static String footer;
	
	public static void sendStandingLobbyTablist() {
		for(Player all : Core.getInGamePlayers()) {
			sendx(all);
		}
		for(Player all : Core.getSpecs()) {
			sendx(all);
		}
	}

	private static void sendx(Player all){
		header = header.replace("[player]", all.getDisplayName());
		header = header.replace("[playercount]", Integer.toString(Core.getTotalPlayers()));
		header = header.replace("[gamestatus]", GState.getGStateName());

		footer = footer.replace("[player]", all.getDisplayName());
		footer = footer.replace("[playercount]", Integer.toString(Core.getTotalPlayers()));
		footer = footer.replace("[gamestatus]", GState.getGStateName());

		TitleManager.sendTabTitle(all, "", "");
		TitleManager.sendTabTitle(all, header, footer);

		header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
		footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
	}
	
	public static void sendStandingInGameTablist() {
		for(Player all : Core.getInGamePlayers()) {
			send(all);
		}
		for(Player all : Core.getSpecs()) {
			send(all);
		}
	}

	private static void send(Player p){
		header = header.replace("[player]", p.getDisplayName());
		header = header.replace("[playercount]", Integer.toString(Core.getTotalPlayers()));
		header = header.replace("[gamestatus]", GState.getGStateName());

		footer = footer.replace("[player]", p.getDisplayName());
		footer = footer.replace("[playercount]", Integer.toString(Core.getTotalPlayers()));
		footer = footer.replace("[gamestatus]", GState.getGStateName());

		TitleManager.sendTabTitle(p, "", "");
		TitleManager.sendTabTitle(p, header, footer);

		header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
		footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
	}
}
