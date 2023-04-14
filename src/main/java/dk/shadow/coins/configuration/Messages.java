package dk.shadow.coins.configuration;

import dk.shadow.coins.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {
    private static HashMap<String, String[]> messages;

    public static void initialise(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        messages = (HashMap)new HashMap<>();
        for (String key : config.getConfigurationSection("").getKeys(true)) {
            if (config.isConfigurationSection(key))
                continue;
            if (config.getStringList(key) != null && config.isList(key)) {
                List<String> stringList = ColorUtils.getColored(config.getStringList(key));
                messages.put(key, stringList.toArray(new String[0]));
                continue;
            }
            if (config.getString(key) != null) {
                List<String> stringList = Collections.singletonList(ColorUtils.getColored(config.getString(key)));
                messages.put(key, stringList.toArray(new String[0]));
            }
        }
        if (messages.containsKey("prefix"))
            for (Map.Entry<String, String[]> entry : messages.entrySet()) {
                for (int i = 0; i < ((String[])entry.getValue()).length; i++)
                    ((String[])entry.getValue())[i] = ((String[])entry.getValue())[i].replaceAll("%prefix%", ((String[])messages.get("prefix"))[0]);
            }

    }

    public static String[] get(String path) {
        if (messages.containsKey(path))
            return messages.get(path);
        return new String[] { "" };
    }



    public static void send(CommandSender player, String path, String... replacements) {
        String[] messages = get(path);
        for (String message : messages) {
            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replaceAll(replacements[i], replacements[i+1]);
            }
            player.sendMessage(message);
        }
    }

    public static void send(CommandSender player, String path) {
        if (messages.containsKey(path))
            player.sendMessage(messages.get(path));

    }

}
