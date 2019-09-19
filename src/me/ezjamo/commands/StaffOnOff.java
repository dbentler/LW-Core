package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.ezjamo.Messages;
import me.ezjamo.Utils;

public class StaffOnOff extends Utils implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender == null) {
			message(sender, "Only players can use this command!");
		}
		if (!sender.hasPermission("admin.on")) {
			message(sender, Messages.prefix + Messages.noPermission);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("admin")) {
			if (args.length == 0) {
				message(sender, "&cUsage: &7/admin <on/off>");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("on")) {
				if (args.length == 1) {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + sender.getName() + " permission set *");
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "op " + sender.getName());
					message(sender, "&aAdmin permissions granted.");
				}
			}
			else if (args[0].equalsIgnoreCase("off")) {
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + sender.getName() + " permission unset *");
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + sender.getName());
				message(sender, "&cAdmin permissions removed.");
			}

			else {
				message(sender, "&cInvalid usage.");
				return true;
			}
		}
		return true;
	}
}
