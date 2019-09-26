package me.ezjamo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class WorthCommand extends Utils implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("worth")) {
			if (args.length == 0) {
				for (String message : Lonewolves.plugin.getConfig().getStringList("Worth.1")) {
					message(sender, message);
				}
			}
			if (args.length == 1) {
    			String page = args[0];
    			if (args[0].equalsIgnoreCase(page)) {
    				for (String message : Lonewolves.plugin.getConfig().getStringList("Worth." + page)) {
    					message(sender, message);
    				}
    			}
    			if (Lonewolves.plugin.getConfig().getStringList("Worth." + page).isEmpty()) {
    				message(sender, "&fUnknown command. Type \"/help\" for help.");
    			}
    		}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> worth = Lonewolves.plugin.getConfig().getConfigurationSection("Worth").getKeys(false).stream().collect(Collectors.toList());
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], worth, completions);
			return completions;
		}
		return empty;
	}
}
