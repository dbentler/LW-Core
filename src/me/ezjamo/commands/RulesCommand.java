package me.ezjamo.commands;
 
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
import net.md_5.bungee.api.ChatColor;
 
public class RulesCommand implements CommandExecutor {
 
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player player = (Player) sender;
    	if (cmd.getName().equalsIgnoreCase("rules")) {
    		if (args.length == 0) {
    			for (String message : Lonewolves.plugin.getConfig().getStringList("Rules.1")) {
    				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    			}
    		}
    		if (args.length == 1) {
    			String page = args[0];
    			if (args[0].equals(page)) {
    				for (String message : Lonewolves.plugin.getConfig().getStringList("Rules." + page)) {
    					player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    				}
    			}
    			if (Lonewolves.plugin.getConfig().getStringList("Rules." + page).isEmpty()) {
    				player.sendMessage(Utils.msg("&fUnknown command. Type \"/help\" for help."));
    			}
    		}
    	}
		return true;
	}
}