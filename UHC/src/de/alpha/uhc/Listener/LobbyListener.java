package de.alpha.uhc.Listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Stats;

public class LobbyListener implements Listener {
	
	public static String sel;
	public static String bought;
	public static String coinsneed;
	
	@EventHandler
	public void onChunkUnLoad(ChunkUnloadEvent e) {
		if(e.getWorld().getName().equals("world")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		
		if(GState.isState(GState.LOBBY) || GState.isState(GState.GRACE)) {
			e.setFoodLevel(20);
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!(GState.isState(GState.LOBBY))) return;
		if(Regions.lobby == false) return;
		
		if(Regions.isInRegion(e.getTo()) == false) {
			if(SpawnFileManager.getLobby() == null) {
				p.teleport(p.getWorld().getSpawnLocation());
			} else {
				p.teleport(SpawnFileManager.getLobby());
			}
		}
		
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(GState.isState(GState.LOBBY) || GState.isState(GState.GRACE)) {
			if(!(e.getEntity() instanceof ArmorStand)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHurt(EntityDamageEvent e) {
		
		if(e.getEntity() instanceof Player) {
			if(GState.isState(GState.LOBBY) || GState.isState(GState.GRACE)) {
				e.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(GState.isState(GState.LOBBY) || GState.isState(GState.RESTART)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if(GState.isState(GState.LOBBY) || GState.isState(GState.RESTART)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onInterAct(PlayerInteractEvent e) {
		
		if(!(GState.isState(GState.LOBBY))) return;
		if(e.getPlayer().getInventory().getItemInHand() == null) return;
		if(!(e.getPlayer().getInventory().getItemInHand().getType().equals(PlayerJoinListener.kitItem))) return;
		
		e.setCancelled(true);
		GUI.open(e.getPlayer());
		
	}
	
	private static HashMap<Player, String> kit = new HashMap<Player, String>();
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		if(e.getClickedInventory() == null) return;
		if(!(e.getClickedInventory().getName().equalsIgnoreCase(GUI.title))) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		
		Player p = (Player) e.getWhoClicked();
		
		e.setCancelled(true);
		
		for(String kits : new KitFileManager().getAllKits()) {
			if(e.getCurrentItem().getType().equals(Material.getMaterial(new KitFileManager().getMaterial(kits).toUpperCase()))) {
				if(new Stats(p).getKits().contains(kits)) {
					kit.put(p, kits);
					sel = sel.replace("[Kit]", kits);
					p.sendMessage(Core.getPrefix() + sel);
					AScoreboard.updateLobbyKit(p);
					sel = MessageFileManager.getMSGFile().getColorString("Kits.GUI.Selected");
					p.closeInventory();
					break;
				} else if(new Stats(p).getCoins() >= new KitFileManager().getPrice(kits)) {
					new Stats(p).removeCoins(new KitFileManager().getPrice(kits));
					new Stats(p).addKit(kits);
					if(kit.containsKey(p)) {
						kit.remove(p);
					}
					kit.put(p, kits);
					AScoreboard.updateLobbyKit(p);
					bought = bought.replace("[Kit]", kits);
					bought = bought.replace("[Coins]", Integer.toString(new KitFileManager().getPrice(kits)));
					p.sendMessage(Core.getPrefix() + bought);
					bought = MessageFileManager.getMSGFile().getColorString("Kits.GUI.Bought");
					p.closeInventory();
					AScoreboard.setLobbyScoreboard(p);
					break;
				} else {
					p.sendMessage(Core.getPrefix()+coinsneed);
				}
			}
		}
	}
	
	public static boolean hasSelKit(Player p) {
		if(kit.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static String getSelKit(Player p) {
		if(kit.containsKey(p)) {
			return kit.get(p);
		}
		return "";
	}
	
}
