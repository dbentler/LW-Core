package me.ezjamo.commands;

import me.ezjamo.Lonewolves;
import me.ezjamo.managers.SpawnManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SpawnCommand implements CommandExecutor {
    public static Map<Player, BukkitTask> tasks;
    public SpawnCommand() {
        tasks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        SpawnManager spawnCoords = SpawnManager.getManager();
        if (cmd.getName().equalsIgnoreCase("spawn")) {
        	if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &cSpawn not set. Use &f/setspawn &cto set a spawnpoint."));
        		return true;
        	}
        	World w = Bukkit.getServer().getWorld(spawnCoords.getConfig().getString("spawn.world"));
            double x = spawnCoords.getConfig().getDouble("spawn.x");
            double y = spawnCoords.getConfig().getDouble("spawn.y");
            double z = spawnCoords.getConfig().getDouble("spawn.z");
            float yaw = (float)spawnCoords.getConfig().getDouble("spawn.yaw");
            float pitch = (float)spawnCoords.getConfig().getDouble("spawn.pitch");
            Location spawn = new Location(w, x, y, z, yaw, pitch);
            if (player.hasPermission("lw.spawn")) {
                if (args.length < 1) {
                	if (player.hasPermission("lw.spawn.bypassdelay")) {
                    	player.teleport(spawn);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fTeleportation successful!"));
                        return true;
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fYou will be teleported in &c" + Lonewolves.plugin.getConfig().getInt("spawn-delay") + " &fseconds."));
                    if (!tasks.containsKey(player)) {
                        tasks.put(player, new BukkitRunnable() {
                            public void run() {
                                player.teleport(spawn);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fTeleportation successful!"));
                                tasks.remove(player);
                            }
                        }.runTaskLater(Lonewolves.plugin, 20L * Lonewolves.plugin.getConfig().getInt("spawn-delay")));
                        return true;
                    }
                }
            }
            if (!player.hasPermission("lw.spawn")) {
                player.sendMessage(Lonewolves.NO_PERMS);
            }
            if (args.length == 1) {
                if (player.hasPermission("lw.spawn.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    target.teleport(spawn);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fTeleportation successful!"));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fTeleportation successful!"));
                }
                if (!player.hasPermission("lw.spawn.others")) {
                	player.sendMessage(Lonewolves.NO_PERMS);
                }
            }
        }
        return true;
    }
}
