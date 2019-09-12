package me.ezjamo.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import me.ezjamo.Lonewolves;

public class BlockedWordsManager implements Listener {
    public Lonewolves plugin;
    public File blockedwords;
    public YamlConfiguration words;
    public static BlockedWordsManager manager;
    
    static {
    	BlockedWordsManager.manager = new BlockedWordsManager();
    }
    
    public BlockedWordsManager() {
        plugin = Lonewolves.getPlugin(Lonewolves.class);
    }
    
    public BlockedWordsManager(Lonewolves mainclass) {
        plugin = Lonewolves.getPlugin(Lonewolves.class);
    }
    
    public static BlockedWordsManager getManager() {
        return BlockedWordsManager.manager;
    }
    
    public void setupFiles() {
    	blockedwords = new File(plugin.getDataFolder(), "blockedwords.yml");
        if (!blockedwords.exists()) {
        	blockedwords.getParentFile().mkdirs();
            plugin.saveResource("blockedwords.yml", false);
        }
        words = new YamlConfiguration();
        try {
        	words.load(blockedwords);
        }
        catch (IOException | InvalidConfigurationException ex2) {
            ex2.printStackTrace();
        }
    }
    
    public FileConfiguration getConfig() {
        return (FileConfiguration)words;
    }
    
    public void saveConfig() {
        try {
        	words.save(blockedwords);
        }
        catch (IOException ex) {}
    }
    
    public void reloadConfig() {
    	words = YamlConfiguration.loadConfiguration(blockedwords);
    }
}
