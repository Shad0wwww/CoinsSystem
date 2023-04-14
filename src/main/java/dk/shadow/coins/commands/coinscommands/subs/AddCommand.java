package dk.shadow.coins.commands.coinscommands.subs;

import dk.shadow.coins.Coins;
import dk.shadow.coins.commands.ISubCommand;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.listener.events.CoinsAddEvent;
import dk.shadow.coins.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddCommand extends ISubCommand {


    public AddCommand() {
        super("add",  "tilføj");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        System.out.println(args.length);
        if (!sender.hasPermission("coins.admin")) {
            Messages.send(sender, "messages.reload_no_permissions");
            return;
        }

        if(args.length == 2) {

            UUID arg_0 = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
            double amount = Double.parseDouble(args[1]);
            Messages.send(sender, "messages.coins_add_en_anden", "%prefix%", String.valueOf(Messages.get("prefix")), "%amount%", String.valueOf(amount), "%player%", Bukkit.getPlayer(arg_0).getDisplayName());
            Messages.send(Bukkit.getOfflinePlayer(arg_0).getPlayer(), "coins_add_modtog", "%prefix%", String.valueOf(Messages.get("prefix")), "%amount%", String.valueOf(amount));

            Coins.getAccountManager().addCoins(arg_0, amount);
            Bukkit.getServer().getPluginManager().callEvent(new CoinsAddEvent(Bukkit.getOfflinePlayer(arg_0).getPlayer(), amount));
            return;

        } else {
            sender.sendMessage(ColorUtils.getColored("&8▌ &7/coins add <spiller> <antal>"));
        }



    }
}
