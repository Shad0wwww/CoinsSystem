package dk.shadow.coins.userinterfaces;

import dk.shadow.coins.userinterfaces.guis.CoinsGui;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GuiManager {
    public static HashMap<String, SubGui> guis = new HashMap<>();
    public static void openMenu(Player player, String name) {
        if (guis.containsKey(name))
            ((SubGui)guis.get(name)).open(player);
    }
    public static void addGui(String name, SubGui gui) {
        guis.put(name, gui);
    }
    public static void initialise() {
        guis.clear();
        addGui("coins", (SubGui)new CoinsGui());

    }
}
