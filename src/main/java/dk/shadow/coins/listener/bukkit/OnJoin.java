package dk.shadow.coins.listener.bukkit;

import dk.shadow.coins.Coins;
import dk.shadow.coins.account.AccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Coins.getAccountManager().addCoins(event.getPlayer().getUniqueId(), 0);
    }
}
