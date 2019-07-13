package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import net.md_5.bungee.api.ChatColor;

public class HelpCommand implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player player = (Player) sender;
    	if (label.equalsIgnoreCase("help")) {
    	if (args.length == 0)
        	for (final String message : Lonewolves.plugin.getConfig().getStringList("Help")) {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    	}
    	if (label.equalsIgnoreCase("help")) {
    	if (args.length == 1)
    		if (args[0].equals("2"))
        	for (final String message : Lonewolves.plugin.getConfig().getStringList("Help 2")) {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    	}
    	if (label.equalsIgnoreCase("help")) {
    	if (args.length == 1)
    		if (!(args[0].equals("2")))
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown command, type /help"));
    	}
		return false;
    }
}
