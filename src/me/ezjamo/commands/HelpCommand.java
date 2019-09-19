package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class HelpCommand extends Utils implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player player = (Player) sender;
    	if (cmd.getName().equalsIgnoreCase("help")) {
    		if (args.length == 0) {
    			for (String message : Lonewolves.plugin.getConfig().getStringList("Help.1")) {
    				message(player, message);
    			}
    		}
    		if (args.length == 1) {
    			String page = args[0];
    			if (args[0].equalsIgnoreCase(page)) {
    				for (String message : Lonewolves.plugin.getConfig().getStringList("Help." + page)) {
    					message(player, message);
    				}
    			}
    			if (Lonewolves.plugin.getConfig().getStringList("Help." + page).isEmpty()) {
    				message(player, "&fUnknown command. Type \"/help\" for help.");
    			}
    		}
    	}
		return true;
	}
}
