package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.commands.WarpCommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public class WarpManager extends Utils implements Listener {
	public Lonewolves plugin;
	public File file;
	public static FileConfiguration config;
	public static WarpManager manager;
	
	static {
		WarpManager.manager = new WarpManager();
	}
	
	public WarpManager() {
		plugin = Lonewolves.getPlugin(Lonewolves.class);
	}
	
	public static WarpManager getManager() {
		return WarpManager.manager;
	}

	public static FileConfiguration getWarps() {
		return WarpManager.config;
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), "warps.yml");
		if (!file.exists()) {
			plugin.getLogger().info("Creating default: " + file);
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Location getCenteredLocation(Location loc) {
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = (int) Math.round(loc.getY());
		int z = loc.getBlockZ();
		return new Location(world, x + 0.5, y, z + 0.5, loc.getYaw(), loc.getPitch());
	}
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockY() != event.getTo().getBlockY()) {
            BukkitTask task = WarpCommand.tasks.get(player);
            if (task != null) {
                message(player, Messages.prefix + "&cTeleportation cancelled!");
                task.cancel();
                WarpCommand.tasks.remove(player);
            }
        }
    }
}
