package dk.shadow.coins.commands.coinscommands.subs;

import dev.triumphteam.gui.guis.Gui;
import dk.shadow.coins.commands.ISubCommand;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.userinterfaces.GuiManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand extends ISubCommand {
    public GuiCommand() {
        super("gui");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if (!sender.hasPermission("coins.reload")) {
            Messages.send(sender, "messages.reload_no_permissions");
            return;
        }
        System.out.println("1");
        GuiManager.openMenu((Player) sender, "coins");
    }
}
