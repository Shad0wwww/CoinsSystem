package dk.shadow.coins.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CoinsAddEvent extends CallableEvent {

    protected Player player;
    protected Double d;

    public CoinsAddEvent(Player player, Double d) {
        this.d = d;
        this.player = player;

    }

    public Player getPlayer() {
        return this.player;
    }

    public Double getAmount() {
        return this.d;
    }

}
