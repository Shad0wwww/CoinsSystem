package dk.shadow.coins.commands.coinscommands.subs;

import dk.shadow.coins.Coins;
import dk.shadow.coins.commands.ISubCommand;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.userinterfaces.GuiManager;
import dk.shadow.coins.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand extends ISubCommand {


    public StatusCommand() {
        super("status");
    }



    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if (!sender.hasPermission("coins.reload")) {
            Messages.send(sender, "messages.reload_no_permissions");
            return;
        }
        sender.sendMessage(ColorUtils.getColored(Messages.get("prefix")));
        sender.sendMessage(ColorUtils.getColored(Coins.MysqlStatus));
    }
}


