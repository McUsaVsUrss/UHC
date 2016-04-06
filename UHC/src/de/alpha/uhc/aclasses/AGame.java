package de.alpha.uhc.aclasses;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Listener.GameEndListener;
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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

/**
 * Created by matte on 06/04/2016.
 */
public class AGame {
    //CONSTANTS
    public static int mpc;
    public static String join;
    public static String full;

    public static String title;
    public static String subtitle;


    public static Material teamItem;
    public static String teamName;

    public static Material kitItem;
    public static String kitName;
    public static boolean kitMode;

    public static Material leaveItem;
    public static String leaveName;

    public static Material startItem;
    public static String startName;

    public static void leave(Player p){
        //TODO
        if(getPlayers().contains(p)){
            Core.removeInGamePlayer(p);
        }
        if(getSpecatorPlayers().contains(p)){
            Core.removeSpec(p);
        }
    }

    public static boolean join(Player p){
        if(GState.isState(GState.RESTART)) {
            return false;
        }

        if(GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
            if(SpawnFileManager.getSpawn() == null) {
                p.teleport(Bukkit.getWorld("UHC").getSpawnLocation());
            } else {
                p.teleport(SpawnFileManager.getSpawn());
            }
            
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);

                    Core.addSpec(p);
                    Spectator.setSpec(p);
                    AScoreboard.setInGameScoreboard(p);

                    ATablist.sendStandingInGameTablist();
                    for(Player all : Core.getInGamePlayers()) {
                        AScoreboard.updateInGameSpectators(all);
                    }
                    //TODO UNDERSTAND
                }
            }, 5);
            return true;
        }

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(all);
        }

        ATablist.sendStandingLobbyTablist();

        if(Core.isMySQLActive) {
            if(MySQLManager.getObjectConditionResult("UHC", "UUID", p.getUniqueId().toString(), "UUID") == null) {
                MySQLManager.exInsertQry("UHC", p.getName(), p.getUniqueId().toString(), "0", "0", "0", "");
            } else if(MySQLManager.getObjectConditionResult("UHC", "UUID ", p.getUniqueId().toString(), "UUID") != null) {
                MySQLManager.exUpdateQry("UHC", "UUID", p.getUniqueId().toString(), "Player", p.getName());
            }
        } else {
            if(!(PlayerFileManager.getPlayerFile().contains("Player: "+ p.getName()))) {
                new PlayerFileManager().addPlayer(p);
            }
        }

        if(Core.getTotalPlayers() == mpc + 1) {
            return false;
        }

        /*for(Entity kill : p.getWorld().getEntities()) {
            if(!(kill instanceof Player || kill instanceof ArmorStand)) {
                kill.remove();
            }
        }*/

        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(Core.getPrefix() + MessageFileManager.getMSGFile().getColorString("Announcements.Join").replace("[Player]", p.getDisplayName()).replace("[PlayerCount]", "§7["+(getPlayers().size() + getSpecatorPlayers().size())+"/"+mpc+"]"));
            }
        }, 20);

        p.setLevel(0);
        p.setExp(0);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        new HoloUtil().showHologram(p);

        if(kitMode) {
            if(kitItem == null || kitName == null) {
                Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
            } else {
                p.getInventory().setHeldItemSlot(0);
                p.getInventory().setItemInHand(new ItemCreator(kitItem).setName(kitName).build());

            }
        }
        if(UHCCommand.teamMode) {
            if(teamItem == null || teamName == null) {
                Bukkit.getConsoleSender().sendMessage(Core.getPrefix()+"§cYou don't have any Kits in your kits.yml");
            } else {
                p.getInventory().setItem(1, new ItemCreator(teamItem).setName(teamName).build());
            }
        }
        p.getInventory().setItem(8, new ItemCreator(leaveItem).setName(leaveName).build());
        if(p.hasPermission("uhc.start")) {
            p.getInventory().setItem(4, new ItemCreator(startItem).setName(startName).build());
        }
        if(SpawnFileManager.getLobby() != null) {
            if(Bukkit.getWorld(SpawnFileManager.getLobbyWorldName()) == null) {
                p.teleport(p.getWorld().getSpawnLocation());
            } else {
                p.teleport(SpawnFileManager.getLobby());
            }
        }
        Core.addInGamePlayer(p);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        for(PotionEffect pe : p.getActivePotionEffects()) {
            if(p.hasPotionEffect(pe.getType())) {
                p.removePotionEffect(pe.getType());
            }
        }

        if(title.contains("[Player]")) {
            String aa = title.replace("[Player]", p.getDisplayName());
            if(subtitle.contains("[Player]")) {
                String bb = subtitle.replace("[Player]", p.getDisplayName());
                TitleManager.sendTitle(p, 20, 20, 20, aa, bb);
            } else {
                TitleManager.sendTitle(p, 20, 20, 20, aa, subtitle);
            }
        } else {
            TitleManager.sendTitle(p, 20, 20, 20, title, subtitle);
        }

        for(Player all : getPlayers()) {
            AScoreboard.setLobbyScoreboard(all);
        }

        for(Player all : getSpecatorPlayers()) {
            AScoreboard.setLobbyScoreboard(all);
        }


        if(Core.getTotalPlayers() == Timer.pc) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    Timer.startCountdown();

                }
            }.runTaskLater(Core.getInstance(), 20);

        }
        return true;
    }

    public static List<Player> getPlayers() {
        return Core.getInGamePlayers();
    }

    public static List<Player> getSpecatorPlayers() {
        return Core.getSpecs();
    }

}
