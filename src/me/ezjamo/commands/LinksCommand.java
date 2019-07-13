package me.ezjamo.commands;
 
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
 
public class LinksCommand implements CommandExecutor {
	FileConfiguration config = Lonewolves.plugin.getConfig();
   
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Player player = (Player) sender;
    if  (!(sender instanceof Player)) {
        sender.sendMessage("Only players may generate links!");
        return true;
    }
    if (label.equalsIgnoreCase("links")) {
    	if (args.length == 0)
        	for (final String message : Lonewolves.plugin.getConfig().getStringList("Links")) {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    	}
    return false;
}
}