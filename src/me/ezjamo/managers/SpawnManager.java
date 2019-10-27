package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.commands.SpawnCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public class SpawnManager extends Utils implements Listener {
    public Lonewolves plugin;
    public File location;
    public YamlConfiguration spawnCoords;
    public static SpawnManager manager;
    
    static {
    	SpawnManager.manager = new SpawnManager();
    }
    
    public SpawnManager() {
        plugin = Lonewolves.getPlugin(Lonewolves.class);
    }
    
    public SpawnManager(Lonewolves mainclass) {
        plugin = Lonewolves.getPlugin(Lonewolves.class);
    }
    
    public static SpawnManager getManager() {
        return SpawnManager.manager;
    }
    
    public void setupFiles() {
        location = new File(plugin.getDataFolder(), "locations.yml");
        if (!location.exists()) {
			plugin.getLogger().info("Creating default: " + location);
			location.getParentFile().mkdirs();
			try {
				location.createNewFile();
				plugin.getServer().getConsoleSender().sendMessage(color("&aSuccessfully created: " + location));
			} catch (Exception e) {
				plugin.getServer().getConsoleSender().sendMessage(color("&cCould not create: " + location));
			}
		}
        spawnCoords = new YamlConfiguration();
        try {
            spawnCoords.load(location);
        }
        catch (IOException | InvalidConfigurationException ex2) {
            ex2.printStackTrace();
        }
    }
    
    public FileConfiguration getConfig() {
        return (FileConfiguration)spawnCoords;
    }
    
    public void saveConfig() {
        try {
            spawnCoords.save(location);
        }
        catch (IOException ex) {}
    }
    
    public void reloadConfig() {
        spawnCoords = YamlConfiguration.loadConfiguration(location);
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
            BukkitTask task = SpawnCommand.tasks.get(player);
            if (task != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&cTeleportation cancelled!"));
                task.cancel();
                SpawnCommand.tasks.remove(player);
            }
        }
    }
}
