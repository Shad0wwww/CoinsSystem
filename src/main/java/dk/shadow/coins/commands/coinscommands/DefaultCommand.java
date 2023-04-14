package dk.shadow.coins.commands.coinscommands;



import dk.shadow.coins.commands.ISubCommand;

import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DefaultCommand extends ISubCommand {

    public DefaultCommand() {
        super("default");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player)) {
            Messages.send(sender,"messages.only_player_command");
        } else {
            Player player = (Player) sender;
            player.sendMessage(Messages.get("prefix"));
            player.sendMessage(ColorUtils.getColored("\n"));
            player.sendMessage(ColorUtils.getColored("&8▌ &7/coins bal"));
            player.sendMessage(ColorUtils.getColored("&8▌ &7/coins pay <spiller> <antal>"));
            if (player.hasPermission("coins.admin")) {
                player.sendMessage(ColorUtils.getColored("&8▌ &7/coins reload"));
                player.sendMessage(ColorUtils.getColored("&8▌ &7/coins add <spiller> <antal>"));
            }
        }



    }
}
