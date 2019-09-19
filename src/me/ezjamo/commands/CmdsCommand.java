package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class CmdsCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("commands")) {
			if (args.length == 0) {
				message(player, "&cUsage: &7/commands <rank>");
				message(player, "&cExample: &7/commands vip");
			}
			
			if (args.length == 1) {
				String rank = args[0];
				if (args[0].equalsIgnoreCase(rank)) {
					for (String message : Lonewolves.plugin.getConfig().getStringList("Commands." + rank)) {
						String placeholders = PlaceholderAPI.setPlaceholders(player, message);
						message(player, placeholders);
					}
				}
				
				if (Lonewolves.plugin.getConfig().getStringList("Commands." + rank).isEmpty()) {
					message(player, Messages.prefix + "&cInvalid Rank");
	    		}
			}
		}
		return true;
	}
}
