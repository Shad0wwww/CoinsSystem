package dk.shadow.coins.listener.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CoinsAddEvent extends Event {

    protected Player player;
    protected Double d;

    public CoinsAddEvent(Player player, Double d) {
        this.d = d;
        this.player = player;

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Double getAmount() {
        return this.d;
    }

}
