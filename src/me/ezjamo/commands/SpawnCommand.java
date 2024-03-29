package me.ezjamo.commands;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import me.ezjamo.managers.SpawnManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import java.util.HashMap;
import java.util.Map;

public class SpawnCommand extends Utils implements CommandExecutor {
    public static Map<Player, BukkitTask> tasks;
    private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    public SpawnCommand() {
        tasks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	SpawnManager spawnCoords = SpawnManager.getManager();
    	if (!(sender instanceof Player)) {
        	if (args.length != 1) {
        		message(sender, "&cUsage: &7/spawn <player>");
        		return true;
        	}
            if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
                message(sender, Messages.prefix + "&cSpawn not set. Use &f/setspawn &cin-game to set a spawnpoint.");
                return true;
}
            World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
            double x = spawnCoords.getConfig().getDouble("spawn.x");
            double y = spawnCoords.getConfig().getDouble("spawn.y");
            double z = spawnCoords.getConfig().getDouble("spawn.z");
            float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
            float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
            Location spawn = new Location(w, x, y, z, yaw, pitch);
            if (Lonewolves.plugin.getConfig().getBoolean("teleport-to-center")) {
                spawn = SpawnManager.getCenteredLocation(spawn);
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                message(sender, Messages.prefix + "&cPlayer not found.");
                return true;
            }
            if (ess != null) {
                User user = ess.getUser(target);
                user.setLastLocation();
            }
            target.teleport(spawn);
            message(sender, Messages.prefix + "&fTeleportation successful!");
            message(target, Messages.prefix + "&fTeleportation successful!");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("spawn")) {
        	if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
        		message(player, Messages.prefix + "&cSpawn not set. Use &f/setspawn &cto set a spawnpoint.");
        		return true;
        	}
        	World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
            double x = spawnCoords.getConfig().getDouble("spawn.x");
            double y = spawnCoords.getConfig().getDouble("spawn.y");
            double z = spawnCoords.getConfig().getDouble("spawn.z");
            float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
            float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
            Location spawn = new Location(w, x, y, z, yaw, pitch);
            if (Lonewolves.plugin.getConfig().getBoolean("teleport-to-center")) {
                spawn = SpawnManager.getCenteredLocation(spawn);
            }
            if (player.hasPermission("lw.spawn")) {
                if (args.length < 1) {
                	if (player.hasPermission("lw.bypass.teleportdelay")) {
                		if (ess != null) {
                            User user = ess.getUser(player);
                            user.setLastLocation();
                        }
                    	player.teleport(spawn);
                    	message(player, Messages.prefix + "&fTeleportation successful!");
                        return true;
                    }
                	message(player, Messages.prefix + "&fYou will be teleported in &c" + Lonewolves.plugin.getConfig().getInt("spawn-delay") + " &fseconds.");
                    if (!tasks.containsKey(player)) {
                        Location finalSpawn = spawn;
                        tasks.put(player, new BukkitRunnable() {
                            public void run() {
                            	if (ess != null) {
                                    User user = ess.getUser(player);
                                    user.setLastLocation();
                                }
                                player.teleport(finalSpawn);
                                message(player, Messages.prefix + "&fTeleportation successful!");
                                tasks.remove(player);
                            }
                        }.runTaskLater(Lonewolves.plugin, 20L * Lonewolves.plugin.getConfig().getInt("spawn-delay")));
                        return true;
                    }
                }
            }
            if (!player.hasPermission("lw.spawn")) {
                player.sendMessage(Messages.prefix + Messages.noPermission);
            }
            if (args.length == 1) {
                if (player.hasPermission("lw.spawn.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                    	message(player, Messages.prefix + "&cPlayer not found.");
                    	return true;
                    }
                    if (ess != null) {
                        User user = ess.getUser(target);
                        user.setLastLocation();
                    }
                    target.teleport(spawn);
                    message(player, Messages.prefix + "&fTeleportation successful!");
                    message(target, Messages.prefix + "&fTeleportation successful!");
                }
                if (!player.hasPermission("lw.spawn.others")) {
                	message(player, Messages.prefix + Messages.noPermission);
                }
            }
        }
        return true;
    }
}
