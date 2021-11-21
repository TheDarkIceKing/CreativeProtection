package valiblackdragon.creativeprotection.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Update implements Runnable {

    String link;
    File out;

    public Update(String link, File out) {
        this.link = link;
        this.out = out;
    }


    @Override
    public void run() {

        try {
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            double fileSize = (double) http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] buffer = new byte[1024];
            double downloaded = 0.00;
            int read = 0;
            double percentDownloaded = 0.00;
            Bukkit.getServer().getConsoleSender().sendMessage("cast");
            while ((read = in.read(buffer, 0, 1024)) >= 0) {
                bout.write(buffer, 0, read);
                downloaded += read;
                percentDownloaded = (downloaded * 100) / fileSize;
                String percent = String.format("%.0f", percentDownloaded);
                Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r" + ChatColor.DARK_AQUA + "Updating...(" + percent +"%)"));
            }
            bout.close();
            in.close();
            Bukkit.getServer().getConsoleSender().sendMessage(valiblackdragon.creativeprotection.utils.Utils.color("&0[&6CreativeProtection&0]&r") + ChatColor.DARK_RED + " Updated. Server will restart to apply the update");
            Bukkit.shutdown();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
