package dk.shadow.coins;

import dk.shadow.coins.account.AccountManager;
import dk.shadow.coins.commands.CommandManager;
import dk.shadow.coins.configuration.Messages;
import dk.shadow.coins.database.MySQLConnector;
import dk.shadow.coins.listener.RegisterListener;
import dk.shadow.coins.task.SaveCoins;
import dk.shadow.coins.userinterfaces.GuiManager;
import dk.shadow.coins.utils.ColorUtils;
import dk.shadow.coins.websocket.WebsocketHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.enums.ReadyState;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;

public final class Coins extends JavaPlugin {
    private static Coins instance;
    private static MySQLConnector mySQLConnector;
    private static ConsoleCommandSender log;

    private static AccountManager accountManager;
    public static String MysqlStatus;
    public static String websocketHandlerStatus;
    public WebsocketHandler websocketHandler;

    public static String websocketURL = "ws://83.92.176.64:8080";

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();
        try {
            mySQLConnector = new MySQLConnector(this, "62.243.56.2", 56624, "coins", "root", "cz8MjtOK" );
            mySQLConnector.deleteTable(false);
            Coins.getMySQLConnector().createTable();
            accountManager = new AccountManager(mySQLConnector.getConnection());
            accountManager.loadAllAccounts();
            if (mySQLConnector.getConnection().isValid(1)) {
                log.sendMessage(ColorUtils.getColored("&a&lCONNECTED TO DATABASE"));
                MysqlStatus = "&a&lCONNECTED TO DATABASE";
            } else {
                log.sendMessage(ColorUtils.getColored("&c&lFAILED TO CONNECT TO DATABASE"));
                MysqlStatus = "&c&lFAILED TO CONNECT TO DATABASE";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RegisterListener.registerListeners(this);
        CommandManager.initialise(this);
        reload();
        GuiManager.initialise();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SaveCoins(), 12000, 12000);
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
        websocketHandler.close();
    }

    public static Coins getInstance() {
        return instance;
    }

    public static MySQLConnector getMySQLConnector() {
        return mySQLConnector;
    }

    public void connectSocket() {
        try {
            System.out.println("websocketHandler" );
            websocketHandler = new WebsocketHandler(new URI(websocketURL));
            websocketHandler.connect();

            if (websocketHandler.getReadyState() == ReadyState.OPEN) {
                log.sendMessage(ColorUtils.getColored("&a&lCONNECTED TO WEBSOCKET"));
                websocketHandlerStatus = "&a&lCONNECTED TO WEBSOCKET";
            } else {
                log.sendMessage(ColorUtils.getColored("&c&lFAILED TO CONNECT TO WEBSOCKET"));
                websocketHandlerStatus = "&c&lFAILED TO CONNECT TO WEBSOCKET";
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
