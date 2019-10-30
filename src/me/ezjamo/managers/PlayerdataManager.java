package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerdataManager extends Utils implements Listener {
    private Lonewolves plugin = Lonewolves.getPlugin(Lonewolves.class);
	private File file;
	private FileConfiguration config;

	public PlayerdataManager(UUID uuid) {
		file = new File(plugin.getDataFolder() + File.separator + "playerdata", uuid + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void createUser(Player player) {
		if (!file.exists()) {
            plugin.getLogger().info("Creating default: " + file);
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				config.set(player.getUniqueId().toString() + ".name", player.getName());
				config.set(player.getUniqueId().toString() + ".scoreboard", "enabled");
				config.save(file);
                plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + file));
			} catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(color("&cCould not create: " + file));
			}
		}
	}

	public FileConfiguration loadUser() {
		return config;
	}

	public void saveUser() {
		try {
			loadUser().save(file);
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
                jsonObject = (JSONObject)parser.parse(new FileReader(playerStatistics));
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
            if (jsonObject != null) {
				if (jsonObject.containsKey(statisticNmsName.toString())) {
					return (long)jsonObject.get(statisticNmsName.toString());
				}
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
            if (jsonObject != null) {
				if (jsonObject.containsKey(statisticNmsName.toString())) {
					return (long)jsonObject.get(statisticNmsName.toString());
				}
			}
            return 0L;
        }
    }
}
