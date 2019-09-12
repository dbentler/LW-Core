package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Messages;
import me.ezjamo.Utils;

 
public class Helpop implements CommandExecutor {
	
	public String allArgs(int start , String[] args){
	    String temp = "";
	    for(int i = start ; i < args.length ; i++){
	     temp += args[i] + " "; 
	    }
	   return temp.trim();
	}
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("request")) {
    		if (args.length == 0) {
    			sender.sendMessage(Utils.msg("&cUsage: &7/request (message)"));
                return true;
    		}
    		
    		Player player = (Player) sender;
    		if (args.length >= 1) {
    			player.sendMessage(Utils.msg(Messages.helpopPlayerMessage));
    			for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
    				if (staff.hasPermission("rank.helper")) {
    					staff.sendMessage(Utils.msg(Messages.helpopPrefix + Messages.helpopStaffMessage.replaceAll("%player_name%", player.getName()) + allArgs(0, args) + ""));
    				}
    			}
    		}
    	}
    	return true;
    }
}
