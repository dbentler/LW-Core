package me.ezjamo.managers;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class AnnouncerManager implements Listener, CommandExecutor {
	public Lonewolves plugin;
	public File announcer;
	public YamlConfiguration messages;
	public static AnnouncerManager manager;

	static {
		AnnouncerManager.manager = new AnnouncerManager();
	}

	public AnnouncerManager() {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}

	public AnnouncerManager(Lonewolves mainclass) {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}

	public static AnnouncerManager getManager() {
		return AnnouncerManager.manager;
	}

	public void setupFiles() {
		announcer = new File(plugin.getDataFolder(), "announcer.yml");
		if (!announcer.exists()) {
			announcer.getParentFile().mkdirs();
			plugin.saveResource("announcer.yml", false);
		}
		messages = new YamlConfiguration();
		try {
			messages.load(announcer);
		}
		catch (IOException | InvalidConfigurationException ex2) {
			ex2.printStackTrace();
		}
	}

	public FileConfiguration getConfig() {
		return (FileConfiguration)messages;
	}

	public void saveConfig() {
		try {
			messages.save(announcer);
		}
		catch (IOException ex) {}
	}

	public void reloadConfig() {
		messages = YamlConfiguration.loadConfiguration(announcer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ma")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("lw.reload")) {
						if (AnnouncerManager.getManager().getConfig().getBoolean("announcer-enabled")) {
							AnnouncerManager.getManager().reloadConfig();
							Lonewolves.size = 1;
							Bukkit.getScheduler().cancelTask(Lonewolves.task);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> Lonewolves.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
								Set<String> configMessages = AnnouncerManager.getManager().getConfig().getConfigurationSection("Messages").getKeys(false);
								if (Lonewolves.size > configMessages.size()) {
									Lonewolves.size = 1;
								}
								for (String message : AnnouncerManager.getManager().getConfig().getStringList("Messages." + Lonewolves.size)) {
									for (Player player : Bukkit.getOnlinePlayers()) {
				                    	player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
				                    }
									Bukkit.getServer().broadcastMessage(Utils.msg(message));
									Lonewolves.size++;
								}
							}, 1L, AnnouncerManager.getManager().getConfig().getInt("announcer-interval") * 20), 1L);
							sender.sendMessage(Messages.prefix + Messages.reloadConfig);
						}
						else sender.sendMessage(Utils.msg(Messages.prefix + "&cThe message announcer is disabled."));
					}
					else sender.sendMessage(Messages.prefix + Messages.noPermission);
				}
			}
		}
		return true;
	}
}
