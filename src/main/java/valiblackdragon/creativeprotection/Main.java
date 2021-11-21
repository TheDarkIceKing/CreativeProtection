package valiblackdragon.creativeprotection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import org.apache.commons.io.FileUtils;
import valiblackdragon.creativeprotection.commands.ManualUpdate;
import valiblackdragon.creativeprotection.commands.suicide;
import valiblackdragon.creativeprotection.events.interaction;
import valiblackdragon.creativeprotection.events.itemdrop;
import valiblackdragon.creativeprotection.utils.Utils;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        // Config Fules
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_AQUA + "Initializing Config");
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Commands
        Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_AQUA + "Registering Commands...");
        getCommand("suicide").setExecutor(new suicide());
        getCommand("update").setExecutor(new ManualUpdate());


        // Events
        Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_AQUA + "Registering Events...");
        Bukkit.getServer().getPluginManager().registerEvents(new suicide(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new interaction(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new itemdrop(), this);

        // Bungee check
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.GREEN + "Checking for online mode");
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("spigot.yml"));
        if (!Bukkit.getOnlineMode() && !config.getBoolean("settings.bungeecord")) {
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_RED + "Offline servers are not supported. You have not enabled bungeecord mode. The plugin will be removed. To prevent this, please enable bungeecord in the spigot.yml file");
            this.selfdestruct();
            return;
        }
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_RED + "Server in online mode detected. Starting plugin...");

        // Blacklist
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.GREEN + "Checking for blacklist...");
        if (valiblackdragon.creativeprotection.utils.Utils.checkForBlacklist(valiblackdragon.creativeprotection.utils.Utils.getServerIP() + ":" + Bukkit.getServer().getPort())) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, this::selfdestruct, 200);
            return;
        }

        //Update
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_AQUA + "Checking for forced update...");
        if (valiblackdragon.creativeprotection.utils.Utils.checkForForcedUpdate(valiblackdragon.creativeprotection.utils.Utils.getServerIP() + ":" + Bukkit.getServer().getPort())) {
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.GOLD + "Forced update enabled. Incase there is an update available, the plugin will be updated");
            autoupdater();
            return;
        }
        if (getConfig().getBoolean("autoupdater")) {
            autoupdater();
            return;
        }

        // Finishing logic
        Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_GREEN + "Plugin started. Have fun!");

    }


    public void selfdestruct() {
        try {
            Bukkit.broadcastMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0================&4" + this.getDescription().getName() + "&0================"));
            Bukkit.broadcastMessage(valiblackdragon.creativeprotection.utils.Utils.color("&4De developer van deze plugin heeft besloten deze plugin te verwijderen van deze server."));
            Bukkit.broadcastMessage(valiblackdragon.creativeprotection.utils.Utils.color("&4Indien je het hier niet mee eens bent, neem contact op met de developer van deze plugin"));
            Bukkit.broadcastMessage(valiblackdragon.creativeprotection.utils.Utils.color("&4Discord: &6TheDarkIceKing#9445"));
            Bukkit.broadcastMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0================&4" + this.getDescription().getName() + "&0================"));
            File delete = this.getFile().getAbsoluteFile();
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.RED + "Deleting plugin..."));
            delete.deleteOnExit();
            this.getPluginLoader().disablePlugin(this);
            FileUtils.deleteDirectory(new File(this.getDataFolder() + ""));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoupdater() {
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.DARK_AQUA + "Checking for update..."));
        String version = "CreativeProtection-" + getDescription().getVersion() + ".jar";
        if (!valiblackdragon.creativeprotection.utils.Utils.getVersion().equalsIgnoreCase(version)) {
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.DARK_RED + "Update found!"));
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.DARK_AQUA + "Updating..."));
            String link = "https://valiblackdragondevelopment.iceteaaytie.repl.co/update?plugin=creativeprotection";
            File out = new File(this.getDataFolder().getParent() + "/" + this.getDescription().getName() + ".jar");

            new Thread(new valiblackdragon.creativeprotection.utils.Update(link, out)).start();
            return;
        }
        Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.DARK_AQUA + "No update found"));

    }
}
