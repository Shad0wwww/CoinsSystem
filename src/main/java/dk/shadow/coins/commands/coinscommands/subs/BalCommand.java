package dk.shadow.coins.commands.coinscommands.subs;

import dk.shadow.coins.Coins;
import dk.shadow.coins.commands.ISubCommand;

import dk.shadow.coins.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.UUID;

public class BalCommand extends ISubCommand {


    public BalCommand() {
        super("balance", "bal");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {

        if(args.length == 1) {
            Player arg_0 = Bukkit.getPlayer(args[0]);
            UUID uuid1 = arg_0.getUniqueId();
            Messages.send(sender, "messages.coins_balance_en_anden", "%prefix%", String.valueOf(Messages.get("prefix")), "%amount%", String.valueOf(Coins.getAccountManager().getBalance(uuid1).getAmount()), "%player%", arg_0.getDisplayName());
            return;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        Messages.send(sender, "messages.coins_balance_dig_selv", "%prefix%", String.valueOf(Messages.get("prefix")), "%amount%", String.valueOf(Coins.getAccountManager().getBalance(uuid).getAmount()));



    }
}
