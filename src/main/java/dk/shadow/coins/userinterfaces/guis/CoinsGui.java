package dk.shadow.coins.userinterfaces.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dk.shadow.coins.Coins;
import dk.shadow.coins.account.Account;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.userinterfaces.SubGui;
import dk.shadow.coins.utils.ColorUtils;
import dk.shadow.coins.utils.GUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

public class CoinsGui implements SubGui {

    public void open(Player player) {
        UUID uuid = player.getUniqueId();

        int rows = Integer.parseInt(Messages.get("gui.rows")[0]);
        String title = Messages.get("gui.title")[0];
        ItemStack arrow_left = GUI.getSkull(Messages.get("gui.arrow-left")[0]);
        ItemStack middle = GUI.getSkull(Messages.get("gui.middle")[0]);

        int size = !Coins.getAccountManager().getBalances().isEmpty() ? Coins.getAccountManager().getBalances().size() : 0;
        int page = 0;
        int n = 0;
        int page_start = 45*page;
        int n2 = 1;


        Gui gui = Gui.gui().title(Component.text(title)).rows(rows).disableAllInteractions().create();

        for (Map.Entry<UUID, Account> entry : Coins.getAccountManager().getBalances().entrySet()) {
            UUID p_uuid = entry.getKey();
            NumberFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMANY));
            String string_amount = formatter.format(entry.getValue().getAmount());
            OfflinePlayer p = Bukkit.getOfflinePlayer(p_uuid);
            String playerName = p.getName();
            String title_head = Messages.get("gui.spiller_head_name", "%player%", playerName)[0];
            String[] lore = Messages.get("gui.spiller_lore", "%balance%", string_amount, "%player%", playerName);



            n2++;

            if (n2 >= page_start) {

                ItemStack head = GUI.getPlayerSkull(playerName);

                GuiItem player_heads = ((ItemBuilder)((ItemBuilder)ItemBuilder.from(head)
                        .name((Component)Component.text(ColorUtils.getColored(title_head))))
                        .setLore(ColorUtils.getColored(lore))).asGuiItem();

                gui.setItem(n, player_heads);

                n++;

                if (n >= 45) {
                    break;
                }

            }

        }

        int math = Math.round((float) size / (9 * rows));
        GuiItem middle_gui = ((ItemBuilder)((ItemBuilder)ItemBuilder.from(middle)
                .name((Component)Component.text(ColorUtils.getColored("&f&lSide"))))
                .setLore(ColorUtils.getColored("&fSide: &70/" + math)))
                .asGuiItem();

        gui.setItem(49, middle_gui);

        gui.open((HumanEntity) player);

    }
}
