package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class LinksCommand extends Utils implements CommandExecutor {
	FileConfiguration config = Lonewolves.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("links")) {
			if (args.length == 0)
				for (String message : config.getStringList("Links")) {
					message(sender, message);
				}
		}
		return true;
	}
}
