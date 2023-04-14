package dk.shadow.coins.commands.coinscommands.subs;

import dk.shadow.coins.Coins;
import dk.shadow.coins.commands.ISubCommand;

import dk.shadow.coins.configuration.Messages;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends ISubCommand {
    public ReloadCommand() {
        super("reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        //Checks, if player have permission.
        if (!sender.hasPermission("coins.reload")) {
            Messages.send(sender, "messages.reload_no_permissions");
            return;
        }

        Messages.send(sender, "messages.reload_starting");
        try {
            Coins.getInstance().reload();
            Messages.send(sender, "messages.reload_succes");
        } catch (Exception e) {
            Messages.send(sender, "messages.reload_error");
            e.printStackTrace();
        }

    }
}
