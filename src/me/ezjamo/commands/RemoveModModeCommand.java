package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Utils;

public class RemoveModModeCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!sender.hasPermission("lw.removemm")) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("removemm")) {
			if (args.length == 0) {
				message(sender, "&cUsage: &7/removemm <player>.");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					message(sender, "&cPlayer not found.");
					return true;
				}
				if (!Modmode.modmode.contains(target.getName())) {
					message(sender, "&f" + target.getName() + " &cis not in mod mode.");
					return true;
				}
				Modmode.modmode.remove(target.getName());
				target.setGameMode(GameMode.SURVIVAL);
				Modmode.loadInventory(target);
				target.performCommand("inv prev");
				message(sender, "&f" + target.getName() + " &chas been removed from mod mode.");
			}
			else {
				message(sender, "&cPlayer not found.");
				return true;
			}
		}
		return true;
	}
}
