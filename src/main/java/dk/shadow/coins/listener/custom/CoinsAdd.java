package dk.shadow.coins.listener.custom;

import dk.shadow.coins.events.CoinsAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CoinsAdd implements Listener {


    @EventHandler
    public void onCoinsAddEvent(CoinsAddEvent event) {
        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " " + event.getAmount().toString());
    }
}
