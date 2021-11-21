package valiblackdragon.creativeprotection.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Utils {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


    public static String getServerIP() {
        JsonObject root = getJSON("https://api.myip.com", "GET");
        return (root == null) ? "-1" : root.get("ip").getAsString();
    }

    public static boolean checkForAuthorised(String string) {
        Bukkit.getServer().getConsoleSender().sendMessage(string);
        JsonObject root = getJSON("https://ValiBlackDragonDevelopment.iceteaaytie.repl.co/admin?uuid=" + string, "GET");
        return (root != null && root.get("admin").getAsBoolean());
    }

    public static boolean checkForBlacklist(String string) {
        JsonObject root = getJSON("https://ValiBlackDragonDevelopment.iceteaaytie.repl.co/server?ip=" + string, "GET");
        return (root != null && root.get("blacklisted").getAsBoolean());
    }

    public static boolean checkForForcedUpdate(String string) {
        JsonObject root = getJSON("https://ValiBlackDragonDevelopment.iceteaaytie.repl.co/server?ip=" + string, "GET");
        return (root != null && root.get("forceupdate").getAsBoolean());
    }

    public static String getVersion() {
        JsonObject root = getJSON("https://ValiBlackDragonDevelopment.iceteaaytie.repl.co/version?plugin=creativeprotection", "GET");
        return (root.get("version").getAsString());
    }

    private static JsonObject getJSON(String url, String method) {
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "ValiBlackDragon");
            connection.connect();
            return (new JsonParser()).parse(new InputStreamReader((InputStream) connection.getContent())).getAsJsonObject();
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error performing HTTP request");
            e.printStackTrace();
            return null;
        }
    }



}



