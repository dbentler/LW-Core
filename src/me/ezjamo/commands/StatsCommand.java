package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;

public class StatsCommand implements CommandExecutor {
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length < 1)
			for (final String message : Lonewolves.plugin.getConfig().getStringList("Stats.Self")) {
			String placeholders = PlaceholderAPI.setPlaceholders(p, message);
	        p.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders));
			}
            else {
                Player target = Bukkit.getPlayer(args[0]);
                OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
                if (target == null) {
                	for (final String message : Lonewolves.plugin.getConfig().getStringList("Stats.Others")) {
            			String placeholders = PlaceholderAPI.setPlaceholders(offline, message);
            			p.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders));
            			}
                }
                else
                	if (target != null) {
                		for (final String message : Lonewolves.plugin.getConfig().getStringList("Stats.Others")) {
                			String placeholders = PlaceholderAPI.setPlaceholders(target, message);
                			p.sendMessage(ChatColor.translateAlternateColorCodes('&', placeholders));
                			}
                	}
            }
		}
		return true;
	}
}