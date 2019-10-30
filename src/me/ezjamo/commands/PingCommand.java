package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;

public class PingCommand extends Utils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length < 1) {
				String self = Lonewolves.plugin.getConfig().getString("Ping.Self");
				String placeholders = PlaceholderAPI.setPlaceholders(p, self);
				message(p, Messages.prefix + placeholders);
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					message(p, "&cPlayer not found.");
				}
				if (target != null) {
					String others = Lonewolves.plugin.getConfig().getString("Ping.Others");
					String placeholders = PlaceholderAPI.setPlaceholders(target, others);
					message(p, Messages.prefix + placeholders);
				}
			}
		}
		return true;
	}
}
