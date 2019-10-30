package me.ezjamo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;

public class CmdsCommand extends Utils implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("commands")) {
			if (args.length == 0) {
				message(player, "&cUsage: &7/commands <rank>");
				message(player, "&cExample: &7/commands vip");
			}
			if (args.length == 1) {
				String rank = args[0];
				if (args[0].equalsIgnoreCase(rank)) {
					for (String message : Lonewolves.plugin.getConfig().getStringList("Commands." + rank)) {
						String placeholders = PlaceholderAPI.setPlaceholders(player, message);
						message(player, placeholders);
					}
				}

				if (Lonewolves.plugin.getConfig().getStringList("Commands." + rank).isEmpty()) {
					message(player, Messages.prefix + "&cInvalid Rank");
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> commands = Lonewolves.plugin.getConfig().getConfigurationSection("Commands").getKeys(false).stream().collect(Collectors.toList());
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], commands, completions);
			return completions;
		}
		return empty;
	}
}
