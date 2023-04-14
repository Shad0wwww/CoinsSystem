package dk.shadow.coins;

import dk.shadow.coins.account.AccountManager;
import dk.shadow.coins.commands.CommandManager;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.database.MySQLConnector;
import dk.shadow.coins.listener.RegisterListener;
import dk.shadow.coins.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

public final class Coins extends JavaPlugin {
    private static Coins instance;
    private static MySQLConnector mySQLConnector;
    private static ConsoleCommandSender log;

    private static AccountManager accountManager;
    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();
        try {
            
            mySQLConnector.deleteTable(false);
            Coins.getMySQLConnector().createTable();
            accountManager = new AccountManager(mySQLConnector.getConnection());
            accountManager.loadAllAccounts();
            if (mySQLConnector.getConnection().isValid(1)) {
                log.sendMessage(ColorUtils.getColored(" &a&lCONNECTED TO DATABASE"));
            } else {
                log.sendMessage(ColorUtils.getColored(" &c&lFAILED TO CONNECT TO DATABASE"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        RegisterListener.registerListeners(this);

        CommandManager.initialise(this);
        reload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            accountManager.saveAllAccounts();
            mySQLConnector.getConnection().close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Coins getInstance() {
        return instance;
    }

    public static MySQLConnector getMySQLConnector() {
        return mySQLConnector;
    }



    public void reload() {
        initialiseConfigs();
    }

    private void initialiseConfigs() {
        saveDefaultConfig();
        File messages = new File(getDataFolder(), "messages.yml");
        if (!messages.exists())
            saveResource("messages.yml", false);
        Messages.initialise(this);
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }

}
