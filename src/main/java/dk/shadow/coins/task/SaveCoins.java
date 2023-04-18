package dk.shadow.coins.task;

import dk.shadow.coins.Coins;

import java.sql.SQLException;

public class SaveCoins implements Runnable {
    @Override
    public void run() {


        try {
            Coins.getAccountManager().saveAllAccounts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
