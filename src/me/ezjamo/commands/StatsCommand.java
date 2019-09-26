package me.ezjamo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.PlayerdataManager;
import me.ezjamo.managers.TimeFormat;
import me.ezjamo.managers.UUIDFetcher;

public class StatsCommand extends Utils implements CommandExecutor, TabCompleter {
	UUID target;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				if (player.hasPermission("lw.stats")) {
					playerMessage(player);
					return true;
				}
			}
			if (args.length == 1) {
				if (player.hasPermission("lw.stats.others")) {
					OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
					Player other = Bukkit.getPlayer(args[0]);
					Player online = Bukkit.getPlayer(args[0]);
					if (online == null) {
						if (!offline.hasPlayedBefore()) {
							message(player, "&cPlayer not found.");
							return true;
						}
						try {
							target = UUIDFetcher.getUUID(args[0]);
						} catch (Exception e) {
							message(player, "&cPlayer not found.");
							return true;
						}
						offlinePlayerMessage(player, offline, target);
						return true;
					}
					otherPlayerMessage(player, other);
					return true;
				}
			}
			if (args.length > 1) {
				message(player, "&cUsage: &7/stats <player>");
				return true;
			}
			else {
				message(player, Messages.prefix + Messages.noPermission);
				return true;
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> players = PlayerdataManager.getManager().get().getConfigurationSection("players").getKeys(false).stream().map(key ->
		Bukkit.getOfflinePlayer(UUID.fromString(key))).map(OfflinePlayer::getName).collect(Collectors.toList());
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], players, completions);
			return completions;
		}
		return null;
	}
	
	public static void playerMessage(Player player) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(player, msg);
			message(player, color(placeholders).replace("%player%", player.getName()).replace("%playtime%", TimeFormat.getTime(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20))
					.replace("%kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS))));
		}
	}
	
	public static void otherPlayerMessage(Player player, Player other) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(other, msg);
			message(player, color(placeholders).replace("%player%", other.getName()).replace("%playtime%", TimeFormat.getTime(other.getStatistic(Statistic.PLAY_ONE_TICK) / 20))
					.replace("%kills%", String.valueOf(other.getStatistic(Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(other.getStatistic(Statistic.DEATHS))));
		}
	}
	
	public static void offlinePlayerMessage(Player player, OfflinePlayer offline, UUID target) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(offline, msg);
			message(player, color(placeholders).replace("%player%", offline.getName()).replace("%playtime%", TimeFormat.getTime(PlayerdataManager.getPlayerStatistic(target, "PLAYTIME", Statistic.PLAY_ONE_TICK) / 20L))
					.replace("%kills%", String.valueOf(PlayerdataManager.getPlayerStatistic(target, "KILLS", Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(PlayerdataManager.getPlayerStatistic(target, "DEATHS", Statistic.DEATHS))));
		}
	}
}
