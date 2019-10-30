package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;

public class Helpop extends Utils implements CommandExecutor {
	
	public String allArgs(int start , String[] args){
	    String temp = "";
	    for(int i = start ; i < args.length ; i++){
	     temp += args[i] + " "; 
	    }
	   return temp.trim();
	}
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
    	Player player = (Player) sender;
    	if (cmd.getName().equalsIgnoreCase("request")) {
    		if (args.length == 0) {
    			message(player, "&cUsage: &7/request <message>");
                return true;
    		}
    		if (args.length >= 1) {
    			message(player, Messages.helpopPlayerMessage);
    			for (Player staff : Bukkit.getOnlinePlayers()) {
    				if (staff.hasPermission("rank.helper")) {
    					message(staff, Messages.helpopPrefix + Messages.helpopStaffMessage.replaceAll("%player_name%", player.getName()) + allArgs(0, args) + "");
    				}
    			}
    		}
    	}
    	return true;
    }
}
