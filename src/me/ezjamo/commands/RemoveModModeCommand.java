package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RemoveModModeCommand implements CommandExecutor {
	
	// This command removes staff mode from a target. 

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;
		if (!p.isOp()) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("removemm")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + ("You must specify a player to remove mod mode from."));
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				Modmode.modmode.remove(target.getName());
				target.setGameMode(GameMode.SURVIVAL);
				target.performCommand("inv prev");
			}
			else {
				sender.sendMessage(ChatColor.RED + "Player not found.");
				return true;
			}
		}
		return true;
	}
}
