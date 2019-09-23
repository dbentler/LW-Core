package me.ezjamo.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.SpawnManager;

public class DistanceCommand extends Utils implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		SpawnManager spawnCoords = SpawnManager.getManager();
		if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
			message(player, Messages.prefix + "&fThere is no spawn set.");
			return true;
		}
		Location pLocation = player.getLocation();
		int playerX = pLocation.getBlockX();
		int playerZ = pLocation.getBlockZ();
		double spawnX = spawnCoords.getConfig().getDouble("spawn.x");
		double spawnZ = spawnCoords.getConfig().getDouble("spawn.z");
		int dist = (int) Math.sqrt((Math.pow(playerX - spawnX, 2)) + (Math.pow(playerZ - spawnZ, 2)));

		message(player, Messages.prefix + Messages.distanceCommand.replace("%distance%", Integer.toString(dist)));
		return true;
	}
}
