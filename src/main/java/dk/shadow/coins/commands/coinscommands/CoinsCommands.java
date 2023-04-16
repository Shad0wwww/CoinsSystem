package dk.shadow.coins.commands.coinscommands;

import dk.shadow.coins.commands.ICommand;

import dk.shadow.coins.commands.ISubCommand;
import dk.shadow.coins.commands.coinscommands.DefaultCommand;
import dk.shadow.coins.commands.coinscommands.subs.AddCommand;
import dk.shadow.coins.commands.coinscommands.subs.BalCommand;
import dk.shadow.coins.commands.coinscommands.subs.ReloadCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinsCommands extends ICommand {

    //TODO: Removecommand, paycommand, gui with all players bal where u also can remove and add coins, setcommand.
    public CoinsCommands(JavaPlugin plugin, String command) {
        super(plugin, command);

        setDefaultCommand(new DefaultCommand());
        addSubCommands(
                new ReloadCommand(), new BalCommand(), new AddCommand());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("argssss3 " + args.length);
        if (args.length <= 0 && getDefaultCommand() != null) {
            execute(sender, getDefaultCommand(), args);
        } else if (args.length > 0) {
            ISubCommand subCommand = findSubCommand(args[0]);
            if (subCommand != null) {
                System.out.println("argssss " + args.length);
                execute(sender, subCommand, args);
            }
            return true;
        }
        return false;
    }
}
