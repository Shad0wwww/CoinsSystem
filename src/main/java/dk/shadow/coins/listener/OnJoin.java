package dk.shadow.coins.listener;

import dk.shadow.coins.Coins;
import dk.shadow.coins.account.AccountManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class OnJoin implements Listener {
    private AccountManager accountManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Coins.getAccountManager().addCoins(event.getPlayer().getUniqueId(), 0);
    }
}
