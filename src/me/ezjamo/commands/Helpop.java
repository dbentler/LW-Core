package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

 
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
                sender.sendMessage(ChatColor.RED + ("Correct usage: /request (message)"));
                return true;
            }
           
    			if (args.length == 1)
            	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour help request has been sent to all online staff members"));
            	for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
            		if (staff.hasPermission("rank.helper")) {
            		staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[LW-Helpop] &9" + sender.getName() + ": &f " + this.allArgs(0, args) + ""));
            			}
            			}
            		}
        return true;
    }
}