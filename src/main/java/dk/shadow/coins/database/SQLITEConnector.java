package dk.shadow.coins.database;

import dk.shadow.coins.Coins;
import dk.shadow.coins.account.AccountManager;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLITEConnector {
    private final Plugin plugin;
    public static AccountManager accountManager;

    private final String connectionString;

    private Connection connection;

    public SQLITEConnector(Plugin plugin) {
        this.plugin = plugin;
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.connectionString = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + plugin.getDescription().getName().toLowerCase() + ".db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                accountManager.saveAllAccounts();
                this.connection.close();
            }
        } catch (SQLException ex) {
            this.plugin.getLogger().severe("An error occurred closing the SQLite database connection: " + ex.getMessage());
        }
    }

    public void connect() {
        if (this.connection == null) {
            try {
                this.connection = DriverManager.getConnection(this.connectionString);
                accountManager = new AccountManager(this.connection);
                createTable();
                accountManager.loadAllAccounts();

            } catch (SQLException ex) {
                this.plugin.getLogger().severe("An error occurred retrieving the SQLite database connection: " + ex.getMessage());
            }
        }
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "player_uuid VARCHAR(36) NOT NULL,"
                + "player_coins DOUBLE NOT NULL"
                + ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }
}