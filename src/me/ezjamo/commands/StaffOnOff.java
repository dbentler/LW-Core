package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

 
public class StaffOnOff implements CommandExecutor {

   
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (!sender.hasPermission("admin.on")) {
    		return true;
    	}
    		if (cmd.getName().equalsIgnoreCase("admin")) {
    			if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + ("Not enough args"));
                return true;
            }
    		
           
            if (args[0].equalsIgnoreCase("on")) {
                if (args.length == 1) {
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + sender.getName() + " permission set *");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "op " + sender.getName());
                	sender.sendMessage(ChatColor.GREEN + "Admin permissions granted.");
                }
            }
                else if (args[0].equalsIgnoreCase("off")) {
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + sender.getName() + " permission unset *");
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + sender.getName());
                    sender.sendMessage(ChatColor.RED + "Admin permissions removed.");
            }
           
            else {
                sender.sendMessage(ChatColor.RED + "Invalid usage.");
                return true;
            		}
    			}
        return true;
    }
}