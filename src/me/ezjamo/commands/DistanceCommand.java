package me.ezjamo.commands;
 
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
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
		
		Location pLocation = player.getLocation();
		int x2 = pLocation.getBlockX();
		int z2 = pLocation.getBlockZ();
		double spawnX = spawnCoords.getConfig().getDouble("spawn.x");
		double spawnZ = spawnCoords.getConfig().getDouble("spawn.z");
		int dist = (int) Math.sqrt((Math.pow(x2 - spawnX, 2)) + (Math.pow(z2 - spawnZ, 2)));
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fYour distance from spawn is &4" + dist + " &fblock(s)."));
		return true;
	}
}
