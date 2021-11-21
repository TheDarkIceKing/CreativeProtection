package valiblackdragon.creativeprotection.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import valiblackdragon.creativeprotection.Main;
import valiblackdragon.creativeprotection.utils.Utils;

import java.util.ArrayList;


public class itemdrop implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private ArrayList<String> cooldown = new ArrayList<String>();

    // Stop item drop
    @EventHandler
    public void canceldrops(PlayerDropItemEvent event){
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE && !event.getPlayer().hasPermission("cp.itemdrop.bypass")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.color(plugin.getConfig().getString("drop-denied")));
        }
    }
    // Stop item pickup
    @EventHandler
    public void cancelpicks(PlayerPickupItemEvent event){
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE && !event.getPlayer().hasPermission("cp.itemdrop.bypass")){
            event.setCancelled(true);
            String name = event.getPlayer().getName();
            if(!cooldown.contains(name)) {
                event.getPlayer().sendMessage(Utils.color(plugin.getConfig().getString("pickup-denied")));
                cooldown.add(name);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                    public void run() {
                        cooldown.remove(name);
                    }
                }, 40L);
            }
        }
    }

}
