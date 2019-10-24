package me.ezjamo.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
	private static Utils utils = new Utils();
	
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
			}
			else {
				message(player, Messages.prefix + Messages.noPermission);
			}
			return true;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		File file = new File(Lonewolves.plugin.getDataFolder(), "playerdata");
		String[] files = file.list();
		List<String> players = new ArrayList<>();
		if (files != null) {
			for (String s : files) {
				players.add(s.replace(".yml", ""));
			}
		}
		List<String> list = players.stream().map(key -> Bukkit.getOfflinePlayer(UUID.fromString(key))).map(OfflinePlayer::getName).collect(Collectors.toList());
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], list, completions);
			return completions;
		}
		return Collections.emptyList();
	}
	
	public static void playerMessage(Player player) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(player, msg);
			utils.message(player, utils.color(placeholders).replace("%player%", player.getName()).replace("%playtime%", TimeFormat.getTime(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20))
					.replace("%kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS))));
		}
	}
	
	public static void otherPlayerMessage(Player player, Player other) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(other, msg);
			utils.message(player, utils.color(placeholders).replace("%player%", other.getName()).replace("%playtime%", TimeFormat.getTime(other.getStatistic(Statistic.PLAY_ONE_TICK) / 20))
					.replace("%kills%", String.valueOf(other.getStatistic(Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(other.getStatistic(Statistic.DEATHS))));
		}
	}
	
	public static void offlinePlayerMessage(Player player, OfflinePlayer offline, UUID target) {
		List<String> message = Lonewolves.plugin.getConfig().getStringList("Stats");
		for (String msg : message) {
			String placeholders = PlaceholderAPI.setPlaceholders(offline, msg);
			utils.message(player, utils.color(placeholders).replace("%player%", offline.getName()).replace("%playtime%", TimeFormat.getTime(PlayerdataManager.getPlayerStatistic(target, "PLAYTIME", Statistic.PLAY_ONE_TICK) / 20L))
					.replace("%kills%", String.valueOf(PlayerdataManager.getPlayerStatistic(target, "KILLS", Statistic.PLAYER_KILLS)))
					.replace("%deaths%", String.valueOf(PlayerdataManager.getPlayerStatistic(target, "DEATHS", Statistic.DEATHS))));
		}
	}
}
