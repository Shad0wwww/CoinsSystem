package dk.shadow.coins.backup;

import dk.shadow.coins.Coins;
import dk.shadow.coins.utils.FileUtils;

import java.io.IOException;
import java.util.logging.Logger;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class CopyBackup {
    private SimpleDateFormat fileNameDateFormat;
    private Logger logger;

    public CopyBackup() {
        fileNameDateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
        logger = Coins.getInstance().getLogger();
    }

    public void createBackup(File file) {
        Date date = new Date();
        File backupFolder = new File(Coins.getInstance().getDataFolder(), "backups");
        if (!backupFolder.exists()) {
            backupFolder.mkdirs();
        }
        String fileName = getFileName(date);
        File destination = new File(backupFolder, fileName + ".db");
        try {
            FileUtils.copyFiles(file, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String getFileName(Date date) {
        return formatDate(date);
    }


    private String formatDate(Date date) {
        return fileNameDateFormat.format(date);
    }

}
