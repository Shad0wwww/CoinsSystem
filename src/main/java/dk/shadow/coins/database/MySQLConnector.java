package dk.shadow.coins.database;

import org.bukkit.plugin.Plugin;

import java.sql.*;

public class MySQLConnector {

    private Plugin plugin;
    private Connection connection;

    private Statement statement;
    public MySQLConnector(Plugin plugin, String host, int port, String database, String username, String password) throws SQLException, ClassNotFoundException {
        this.plugin = plugin;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
    }

    //Function to create a table in mysql
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT NOT NULL AUTO_INCREMENT,"
                + "player_uuid VARCHAR(36) NOT NULL,"
                + "player_coins DOUBLE NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
    //Function to delete tables and the boolean to turn of and on
    public void deleteTable(boolean b) throws SQLException {
        if (b) {
            try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
                statement.executeUpdate();
            }
        }
    }



    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }





}
