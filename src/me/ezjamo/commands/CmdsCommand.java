package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
import net.md_5.bungee.api.ChatColor;

public class CmdsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (label.equalsIgnoreCase("commands")) {
		if (player.hasPermission("litebans.mute")) {
			if (args.length == 0) {
				player.sendMessage(Utils.chat("&cUsage: &7/commands <rank>"));
				player.sendMessage(Utils.chat("&cExample: &7/commands helper"));
			}
			
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("helper")) {
					for (final String message : Lonewolves.plugin.getConfig().getStringList("Commands.Helper")) {
		        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		    }
				}
				
				if (args[0].equalsIgnoreCase("mod")) {
					for (final String message : Lonewolves.plugin.getConfig().getStringList("Commands.Mod")) {
		        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		    }
				}
				
				if (!args[0].equalsIgnoreCase("helper")) {
				if (!args[0].equalsIgnoreCase("mod"))
					player.sendMessage(Utils.chat("&cInvalid Rank"));
				}
			}
		}
		if (args.length == 0 | args.length == 1) {
		if (!player.hasPermission("litebans.mute")) {
			player.sendMessage(Lonewolves.NO_PERMS);
		}
		}
		}
		return false;
	}

}
