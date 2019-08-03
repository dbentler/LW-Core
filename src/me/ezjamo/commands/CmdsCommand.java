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
		
		if (cmd.getName().equalsIgnoreCase("commands")) {
			if (args.length == 0) {
				player.sendMessage(Utils.chat("&cUsage: &7/commands <rank>"));
				player.sendMessage(Utils.chat("&cExample: &7/commands vip"));
			}
			
			if (args.length == 1) {
				String rank = args[0];
				if (args[0].equalsIgnoreCase(rank)) {
					for (String message : Lonewolves.plugin.getConfig().getStringList("Commands." + rank)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
					}
				}
				
				if (Lonewolves.plugin.getConfig().getStringList("Commands." + rank).isEmpty()) {
					player.sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] &cInvalid Rank"));
	    		}
			}
		}
		return true;
	}
}