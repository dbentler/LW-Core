package me.ezjamo.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class PlayerdataManager extends Utils implements Listener {
	public Lonewolves plugin;
	public File file;
	public FileConfiguration config;
	public static PlayerdataManager manager;
	
	static {
		PlayerdataManager.manager = new PlayerdataManager();
	}
	
	public PlayerdataManager() {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}
	
	public static PlayerdataManager getManager() {
		return PlayerdataManager.manager;
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), "playerdata.yml");
		if (!file.exists()) {
			plugin.getLogger().severe("Creating default: " + file);
			file.getParentFile().mkdirs();
			plugin.saveResource("playerdata.yml", false);
			plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + file));
		}
		config = new YamlConfiguration();
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration get() {
		return config;
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() throws IOException {
		config.save(file);
	}
}
