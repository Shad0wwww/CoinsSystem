package dk.shadow.coins.userinterfaces.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.shadow.coins.Coins;
import dk.shadow.coins.account.Account;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.database.SQLITEConnector;
import dk.shadow.coins.userinterfaces.GuiManager;
import dk.shadow.coins.userinterfaces.SubGui;
import dk.shadow.coins.utils.ColorUtils;
import dk.shadow.coins.utils.GUI;
import dk.shadow.coins.utils.GlassColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
        ItemStack arrow_right = GUI.getSkull(Messages.get("gui.arrow-right")[0]);

        String top_row = Messages.get("gui.top-row")[0];
        String bottom_row = Messages.get("gui.bottom-row")[0];

        GuiItem top_row_item = ItemBuilder.from(GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(ColorUtils.plain(top_row)), "&f")).name(Component.text(ColorUtils.getColored("&7"))).asGuiItem();
        GuiItem bottom_row_item = ItemBuilder.from(GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(ColorUtils.plain(bottom_row)), "&f")).name(Component.text(ColorUtils.getColored("&7"))).asGuiItem();


        PaginatedGui gui = Gui.paginated()
                .title(Component.text(title))
                .rows(rows)
                .disableAllInteractions()
                .pageSize(37)
                .create();



        int n = 8;
        for (Map.Entry<UUID, Account> entry : SQLITEConnector.getAccountManager().getBalances().entrySet()) {

            UUID p_uuid = entry.getKey();
            NumberFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMANY));
            String string_amount = formatter.format(entry.getValue().getAmount());

            OfflinePlayer p = Bukkit.getOfflinePlayer(p_uuid);
            String playerName = p.getName();

            String title_head = Messages.get("gui.spiller_head_name", "%player%", playerName)[0];
            String[] lore = Messages.get("gui.spiller_lore", "%balance%", string_amount, "%player%", playerName);

            ItemStack head = GUI.getPlayerSkull(playerName);

            GuiItem player_heads = ItemBuilder.from(head).name(Component.text(ColorUtils.getColored(title_head))).setLore(ColorUtils.getColored(lore)).asGuiItem(event -> {
                Player clicked_player = (Player) event.getWhoClicked();
                if (event.getCurrentItem() == null) return;

                ItemStack clickedItem = event.getCurrentItem();

                // Check if the clicked item is a skull
                if (clickedItem.getType() == Material.SKULL || clickedItem.getType() == Material.SKULL_ITEM) {
                    SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                    // Check if the skull has an owner
                    if (skullMeta.hasOwner()) {
                        String owner = skullMeta.getOwner();
                        // Do something with the owner's nam
                        Bukkit.broadcastMessage(owner);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.5F, 0.5F);
                        open(player, Bukkit.getOfflinePlayer(owner));

                    }
                }


            });
            n++;
            gui.setItem(n, player_heads);

        }

        GuiItem arrow_left_gui = ItemBuilder.from(arrow_left)
                .name(Component.text(ColorUtils.getColored("&f&lForrige Side")))
                .setLore(ColorUtils.getColored("&7" + gui.getNextPageNum()))

                .asGuiItem(event -> {
                    gui.next();
                });

        GuiItem middle_gui = ItemBuilder.from(middle)
                .name(Component.text(ColorUtils.getColored("&f&lSide")))
                .setLore(ColorUtils.getColored("&fSide: &70/" + gui.getPagesNum()))
                .asGuiItem();

        GuiItem arrow_right_gui = ItemBuilder.from(arrow_right)
                .name(Component.text(ColorUtils.getColored("&f&lNæste Side")))
                .setLore(ColorUtils.getColored("&7" + gui.getPrevPageNum()))
                .asGuiItem(event -> {
                    gui.previous();
                });

        for (int i = 0; i < 9; i++) {
           gui.setItem(i, top_row_item);
        }

        gui.setItem(49, middle_gui);
        gui.setItem(48, arrow_left_gui);
        gui.setItem(50, arrow_right_gui);

        gui.open(player);

    }


    public void open(Player you, OfflinePlayer player) {
        ItemStack tilbage = GUI.getSkull(Messages.get("admin-gui.tilbage-head")[0]);

        String title = Messages.get("admin-gui.title", "%player%", player.getName())[0];

        Gui admin_gui = Gui.gui()
                .title(Component.text(title))
                .type(GuiType.HOPPER)
                .disableAllInteractions()
                .create();

        GuiItem arrow_left_gui = ItemBuilder.from(tilbage)
                .name(Component.text(ColorUtils.getColored("&c&lTILBAGE")))
                .setLore(ColorUtils.getColored("&7 tilbage")).asGuiItem(event -> {
                    you.playSound(you.getLocation(), Sound.NOTE_BASS, 1, 5);
                    open(you);
                });



        admin_gui.setItem(0, arrow_left_gui);
        admin_gui.open(you);
    }


}
