package dk.shadow.coins.commands;




import dk.shadow.coins.commands.coinscommands.CoinsCommands;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {
    public static void initialise(JavaPlugin instance) {
        new CoinsCommands(instance, "coin");

    }
}