package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.File;
import java.io.IOException;

public class CmdAliasManager extends Utils implements Listener {
    private FileConfiguration config;
    private static CmdAliasManager manager;
    private Lonewolves plugin = Lonewolves.getPlugin(Lonewolves.class);
    private File file;

    static {
        manager = new CmdAliasManager();
    }

    @EventHandler
    public void cmdAlias(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        for (String command : getManager().getConfig().getConfigurationSection("").getKeys(false)) {
            String alias = getManager().getConfig().getConfigurationSection("").getString(command + ".command");
            if (args[0].equalsIgnoreCase("/" + command)) {
                event.setMessage(event.getMessage().replace(command, alias));
            }
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public static CmdAliasManager getManager() {
        return manager;
    }

    public void load() {
        file = new File(plugin.getDataFolder(), "command-aliases.yml");
        if (!file.exists()) {
            plugin.getLogger().info("Creating default: " + file);
            file.getParentFile().mkdirs();
            plugin.saveResource("command-aliases.yml", false);
            plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + file));
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(color("&cCould not save " + file));
        }
    }
}
