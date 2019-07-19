package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FTopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("ftop")) {
		if (args.length == 1) {
		if (args[0].equalsIgnoreCase("update")) {
			player.performCommand("factiontop update");
		}
		}
		}
		return false;
	}

}
