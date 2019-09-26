package me.ezjamo.managers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
			try {
				file.createNewFile();
				plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + file));
			} catch (Exception e) {
				plugin.getServer().getConsoleSender().sendMessage(color("&cCould not create: " + file));
			}
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
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long getPlayerStatistic(UUID player, String stat, Statistic statistic) {
        File worldFolder = new File(Bukkit.getServer().getWorlds().get(0).getWorldFolder(), "stats");
        File playerStatistics = new File(worldFolder, player + ".json");
        if (!playerStatistics.exists()) {
            return 0L;
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            try {
                jsonObject = (JSONObject)parser.parse((Reader)new FileReader(playerStatistics));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e2) {
            Bukkit.getLogger().log(Level.WARNING, "Error while running LW-Core.", e2);
        }
        if (stat.equalsIgnoreCase("PLAYTIME")) {
            StringBuilder statisticNmsName = new StringBuilder("stat.playOneMinute");
            if (jsonObject.containsKey((Object)statisticNmsName.toString())) {
                return (long)jsonObject.get((Object)statisticNmsName.toString());
            }
            return 0L;
        }
        else {
            StringBuilder statisticNmsName = new StringBuilder("stat.");
            char[] charArray;
            for (int length = (charArray = statistic.name().toCharArray()).length, i = 0; i < length; ++i) {
                char character = charArray[i];
                if (statisticNmsName.charAt(statisticNmsName.length() - 1) == '_') {
                    statisticNmsName.setCharAt(statisticNmsName.length() - 1, Character.toUpperCase(character));
                }
                else {
                    statisticNmsName.append(Character.toLowerCase(character));
                }
            }
            if (jsonObject.containsKey((Object)statisticNmsName.toString())) {
                return (long)jsonObject.get((Object)statisticNmsName.toString());
            }
            return 0L;
        }
    }
}
