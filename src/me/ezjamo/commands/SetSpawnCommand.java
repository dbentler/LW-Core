package me.ezjamo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.managers.SpawnManager;

public class SetSpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		SpawnManager spawnCoords = SpawnManager.getManager();
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (player.hasPermission("lw.setspawn")) {
				spawnCoords.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
				spawnCoords.getConfig().set("spawn.x", player.getLocation().getX());
				spawnCoords.getConfig().set("spawn.y", player.getLocation().getY());
				spawnCoords.getConfig().set("spawn.z", player.getLocation().getZ());
				spawnCoords.getConfig().set("spawn.yaw", player.getLocation().getYaw());
				spawnCoords.getConfig().set("spawn.pitch", player.getLocation().getPitch());
				spawnCoords.saveConfig();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fSpawn set."));
			}
			if (!player.hasPermission("lw.setspawn")) {
				player.sendMessage(Messages.prefix + Messages.noPermission);
			}
		}
		return true;
	}
}
