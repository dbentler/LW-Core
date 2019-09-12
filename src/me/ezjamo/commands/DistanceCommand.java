package me.ezjamo.commands;
 
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.SpawnManager;
 
public class DistanceCommand implements CommandExecutor {
	
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		SpawnManager spawnCoords = SpawnManager.getManager();
		
		if  (!(sender instanceof Player)) {
			sender.sendMessage("Only players may check their distance from spawn!");
			return true;
		}
		
		if (spawnCoords.getConfig().getConfigurationSection("spawn") == null) {
			player.sendMessage(Utils.msg(Messages.prefix + "&fThere is no spawn set."));
			return true;
		}
		
		Location pLocation = player.getLocation();
		int playerX = pLocation.getBlockX();
		int playerZ = pLocation.getBlockZ();
		double spawnX = spawnCoords.getConfig().getDouble("spawn.x");
		double spawnZ = spawnCoords.getConfig().getDouble("spawn.z");
		int dist = (int) Math.sqrt((Math.pow(playerX - spawnX, 2)) + (Math.pow(playerZ - spawnZ, 2)));
		
		player.sendMessage(Messages.prefix + Messages.distanceCommand.replaceAll("%distance%", Integer.toString(dist)));
		return true;
	}
}
