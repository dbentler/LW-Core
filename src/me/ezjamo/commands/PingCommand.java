package me.ezjamo.commands;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;

 
public class PingCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length < 1)
			for (final String message : Lonewolves.plugin.getConfig().getStringList("Ping.Self")) {
				String placeholders = PlaceholderAPI.setPlaceholders(p, message);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders));
			}
            else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found"));
                }
                else
                	if (target != null) {
                		for (final String message : Lonewolves.plugin.getConfig().getStringList("Ping.Others")) {
                			String placeholders = PlaceholderAPI.setPlaceholders(target, message);
            				p.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders));
                			}
                	}
            }			
		}
		return true;
	}
}