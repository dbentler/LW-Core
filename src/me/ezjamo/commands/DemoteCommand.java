package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DemoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	if (!sender.hasPermission("lw.demote")) {
    		return true;
    	}
    		if (cmd.getName().equalsIgnoreCase("demote")) {
    			if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + ("Usage: /demote (player)"));
                return true;
            }
                if (args.length == 1) {
                	Player target = Bukkit.getPlayer(args[0]);
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + target.getName() + " inv prev");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove Helper");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " parent remove Mod");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " permission unset *");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lp user " + target.getName() + " permission unset admin.on");
                	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "deop " + target.getName());
                	Modmode.modmode.remove(target.getName());
                	target.setGameMode(GameMode.SURVIVAL);
                	sender.sendMessage(ChatColor.RED + "Demoted " + target.getName() + ".");
                }
            else {
                sender.sendMessage(ChatColor.RED + "Invalid usage.");
                return true;
            		}
    			}
        return true;
    }
}