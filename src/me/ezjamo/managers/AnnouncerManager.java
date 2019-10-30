package me.ezjamo.managers;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;

public class AnnouncerManager extends Utils implements Listener, CommandExecutor {
	public Lonewolves plugin;
	public File file;
	public FileConfiguration config;
	private Utils utils = new Utils();
	public static AnnouncerManager manager;
	
	static {
		AnnouncerManager.manager = new AnnouncerManager();
	}
	
	public AnnouncerManager() {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}
	
	public static AnnouncerManager getManager() {
		return AnnouncerManager.manager;
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), "announcer.yml");
		if (!file.exists()) {
			plugin.getLogger().info("Creating default: " + file);
			file.getParentFile().mkdirs();
			plugin.saveResource("announcer.yml", false);
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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ma")) {
			if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
				message(sender, "&cUsage: &7/ma reload");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("lw.reload")) {
					getManager().reload();
					Lonewolves.size = 0;
					Bukkit.getScheduler().cancelTask(Lonewolves.task);
					message(sender, Messages.prefix + Messages.reloadConfig);
					if (!getManager().get().getBoolean("announcer-settings.enabled")) {
						return true;
					}
					if (getManager().get().getBoolean("announcer-settings.enabled")) {
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lonewolves.plugin, () -> Lonewolves.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
							Lonewolves.size++;
							Set<String> configMessages = getManager().get().getConfigurationSection("Messages").getKeys(false);
							if (Lonewolves.size > configMessages.size()) {
								Lonewolves.size = 1;
							}
							for (String message : getManager().get().getStringList("Messages." + Lonewolves.size)) {
								for (Player player : Bukkit.getOnlinePlayers()) {
									player.playSound(player.getLocation(), Sound.valueOf(getManager().get().getString("announcer-settings.sound")), 0.5f, 1.0f);
								}
								for (Player online : Bukkit.getOnlinePlayers()) {
									online.sendMessage(utils.color(message));
								}
							}
						}, 1L, getManager().get().getInt("announcer-settings.interval") * 20), 1L);
					}
				}
				else message(sender, Messages.prefix + Messages.noPermission);
			}
		}
		return true;
	}
}
