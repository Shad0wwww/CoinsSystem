package dk.shadow.coins.userinterfaces.guis;

import dev.triumphteam.gui.guis.Gui;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.userinterfaces.SubGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoinsAdmin implements SubGui {

    public void open(Player paramPlayer) {
        Bukkit.broadcastMessage("paramPlayer - "  + paramPlayer);
    }

    public void open(Player paramPlayer, Player palyer2) {
        Bukkit.broadcastMessage("paramPlayer2 - "  + paramPlayer);
        Bukkit.broadcastMessage("palyer2 - "  + palyer2);


        int rows = Integer.parseInt(Messages.get("admin-gui.rows")[0]);
        String title = Messages.get("admin-gui.title", "%player%", palyer2.getDisplayName())[0];

        Gui admin_gui = Gui.gui()
                .title(Component.text(title))
                .rows(rows)
                .disableAllInteractions()
                .create();



        admin_gui.open(paramPlayer);
    }
}