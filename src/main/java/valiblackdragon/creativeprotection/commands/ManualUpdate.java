package valiblackdragon.creativeprotection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import valiblackdragon.creativeprotection.Main;

public class ManualUpdate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Main plugin = Main.getPlugin(Main.class);
        if (!sender.hasPermission("cp.update")) {
            sender.sendMessage((valiblackdragon.creativeprotection.utils.Utils.color(plugin.getConfig().getString("no-update-permission"))));
            return false;}

        sender.sendMessage(valiblackdragon.creativeprotection.utils.Utils.color( "&0[&6CreativeProtection&0]&r Plugin word geupdate, Na deze update zal de server opnieuw opgestart worden"));
        plugin.autoupdater();
        return false;
    }
}
