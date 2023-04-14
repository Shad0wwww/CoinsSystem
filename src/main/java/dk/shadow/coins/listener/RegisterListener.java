package dk.shadow.coins.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RegisterListener {

    public static void registerListeners(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), plugin);
    }
}
