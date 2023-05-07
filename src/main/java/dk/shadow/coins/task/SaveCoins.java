package dk.shadow.coins.task;

import dk.shadow.coins.Coins;
import dk.shadow.coins.database.SQLITEConnector;

import java.sql.SQLException;

public class SaveCoins implements Runnable {
    @Override
    public void run() {


        try {
            SQLITEConnector.getAccountManager().saveAllAccounts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
