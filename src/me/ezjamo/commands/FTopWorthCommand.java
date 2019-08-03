package me.ezjamo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import net.md_5.bungee.api.ChatColor;

public class FTopWorthCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("worth")) {
			for (final String message : Lonewolves.plugin.getConfig().getStringList("FTOP Worth")) {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
		}
		return false;
	}

}
