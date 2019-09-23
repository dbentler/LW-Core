package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class HelpCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("help")) {
			if (args.length == 0) {
				for (String message : Lonewolves.plugin.getConfig().getStringList("Help.1")) {
					message(sender, message);
				}
			}
			if (args.length == 1) {
				String page = args[0];
				if (args[0].equalsIgnoreCase(page)) {
					for (String message : Lonewolves.plugin.getConfig().getStringList("Help." + page)) {
						message(sender, message);
					}
				}
				if (Lonewolves.plugin.getConfig().getStringList("Help." + page).isEmpty()) {
					message(sender, "&fUnknown command. Type \"/help\" for help.");
				}
			}
		}
		return true;
	}
}
