package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.SpawnManager;

public class SetSpawnCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
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
				message(player, Messages.prefix + "&fSpawn set.");
			}
			if (!player.hasPermission("lw.setspawn")) {
				message(player, Messages.prefix + Messages.noPermission);
			}
		}
		return true;
	}
}
