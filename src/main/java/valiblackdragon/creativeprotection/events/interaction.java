package valiblackdragon.creativeprotection.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import valiblackdragon.creativeprotection.Main;
import valiblackdragon.creativeprotection.utils.Utils;

public class interaction implements Listener {
    Main plugin = Main.getPlugin(Main.class);

    // Open inventory's
    @EventHandler
    public void cancelinv(InventoryOpenEvent event){
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE && !event.getPlayer().hasPermission("cp.interact.bypass")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.color(plugin.getConfig().getString("interact-denied")));
        }
    }

    // Interact met armor stand
    @EventHandler
    public void cancelarm(PlayerArmorStandManipulateEvent event){
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE && !event.getPlayer().hasPermission("cp.armorstand.bypass")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.color(plugin.getConfig().getString("armorstand-denied")));
        }
    }
}
