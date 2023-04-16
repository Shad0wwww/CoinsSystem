package dk.shadow.coins.commands.coinscommands.subs;

import dk.shadow.coins.Coins;
import dk.shadow.coins.commands.ISubCommand;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.utils.ColorUtils;
import dk.shadow.coins.utils.IntUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetCommand extends ISubCommand {

    public SetCommand() {
        super("set");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if (!sender.hasPermission("coins.admin")) {
            Messages.send(sender, "messages.reload_no_permissions");
            return;
        }



        if(args.length == 2) {
            UUID arg_0 = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
            Player arg_0_player = Bukkit.getPlayer(args[0]);

            if (!IntUtil.isInteger(args[1])) {
                sender.sendMessage(ColorUtils.getColored("&8▌ &7Du skal skrive et tal."));
                return;
            }

            double amount = Double.parseDouble(args[1]);
            if (!(amount > 0)) {
                sender.sendMessage(ColorUtils.getColored("&8▌ &7Du kan &cikke &7sende negative &eCoins &7til andre."));
                return;
            }

            Messages.send(sender, "messages.coins_set", "%amount%", String.valueOf(amount), "%player%", arg_0_player.getDisplayName());
            Coins.getAccountManager().setCoins(arg_0, amount);


        } else {
            sender.sendMessage(ColorUtils.getColored("&8▌ &7/coins set <spiller> <antal>"));
        }




    }
}
