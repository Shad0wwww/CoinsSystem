package dk.shadow.coins.account;



import java.sql.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class AccountManager {
    private Connection connection;
    private TreeMap<UUID, Account> accounts = new TreeMap<>();
    public AccountManager(Connection connection) {
        this.connection = connection;
    }

    public void addCoins(UUID uuid, double amount){
        if (this.accounts.containsKey(uuid)) {
            Account account = this.accounts.get(uuid);
            account.addAmount(amount);
        } else {
            Account newAccount = new Account(uuid, amount);
            this.accounts.put(uuid, newAccount);
        }
    }

    public void removeCoins(UUID uuid, double amount){
        if (this.accounts.containsKey(uuid)) {
            Account account = this.accounts.get(uuid);
            account.removeAmount(amount);
        } else {
            Account newAccount = new Account(uuid, amount);
            this.accounts.put(uuid, newAccount);
        }
    }

    public void setCoins(UUID uuid, double amount) {
        if (this.accounts.containsKey(uuid)) {
            Account account = this.accounts.get(uuid);
            account.setAmount(amount);
        } else {
            Account newAccount = new Account(uuid, amount);
            this.accounts.put(uuid, newAccount);
        }
    }

    public void payCoins(UUID uuid, double amount, UUID uuid2, double amount2){

    }

    public Account getBalance(UUID uuid) {
        return this.accounts.get(uuid);
    }

    public Map<UUID, Account> getBalances() {
        return this.accounts;
    }

    public void loadAllAccounts() throws SQLException {
        String selectSql = "SELECT player_uuid, player_coins FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(selectSql);

            if (!result.isBeforeFirst()) {
                System.out.println("Result set is empty");
            }

            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("player_uuid"));
                double coins = result.getLong("player_coins");
                this.addCoins(uuid, coins);
            }
        }
    }

    public void saveAllAccounts() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT player_coins FROM users WHERE player_uuid = ?")) {
            for (Map.Entry<UUID, Account> entry : this.accounts.entrySet()) {
                UUID uuid = entry.getKey();
                double coins = entry.getValue().getAmount();
                statement.setString(1, uuid.toString());
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    // UUID already exists in the users table, update the player_coins value
                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET player_coins = ? WHERE player_uuid = ?")) {
                        updateStatement.setDouble(1, coins);
                        updateStatement.setString(2, uuid.toString());
                        updateStatement.executeUpdate();
                    }
                } else {
                    // UUID does not exist in the users table, insert a new row
                    try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (player_uuid, player_coins) VALUES (?, ?)")) {
                        insertStatement.setString(1, uuid.toString());
                        insertStatement.setDouble(2, coins);
                        insertStatement.executeUpdate();
                    }
                }
            }
        }
    }




}
