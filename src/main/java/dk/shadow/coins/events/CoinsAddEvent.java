package dk.shadow.coins.events;

import org.bukkit.entity.Player;

public class CoinsAddEvent extends CallableEvent {

    protected Player sender;
    protected Player reciver;
    protected Double d;

    public CoinsAddEvent(Player sender, Double d, Player reciver) {
        this.d = d;
        this.sender = sender;
        this.reciver = reciver;

    }

    public Player getSender() {
        return this.sender;
    } public Player getReciver() {
        return this.reciver;
    }

    public Double getAmount() {
        return this.d;
    }

}
