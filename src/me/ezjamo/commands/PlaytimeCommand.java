package me.ezjamo.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
import me.ezjamo.managers.PlayerdataManager;
import me.ezjamo.managers.TimeFormat;
import me.ezjamo.managers.UUIDFetcher;

public class PlaytimeCommand extends Utils implements CommandExecutor {
	static String message = Lonewolves.plugin.getConfig().getString("Playtime");
	UUID target;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("playtime")) {
			if (args.length == 0) {
				if (player.hasPermission("lw.playtime")) {
					playerMessage(player);
					return true;
				}
			}
			if (args.length == 1) {
				if (player.hasPermission("lw.playtime.others")) {
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
				message(player, "&cUsage: &7/playtime <player>");
				return true;
			}
			else {
				message(player, Messages.prefix + Messages.noPermission);
				return true;
			}
		}
		return true;
	}
	
	public static void playerMessage(Player player) {
		String placeholders = PlaceholderAPI.setPlaceholders(player, message);
		message(player, color(placeholders).replace("%player%", player.getName()).replace("%playtime%", TimeFormat.getTime(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20)));
	}
	
	public static void otherPlayerMessage(Player player, Player other) {
		String placeholders = PlaceholderAPI.setPlaceholders(other, message);
		message(player, color(placeholders).replace("%player%", other.getName()).replace("%playtime%", TimeFormat.getTime(other.getStatistic(Statistic.PLAY_ONE_TICK) / 20)));
	}
	
	public static void offlinePlayerMessage(Player player, OfflinePlayer offline, UUID target) {
		String placeholders = PlaceholderAPI.setPlaceholders(offline, message);
		message(player, color(placeholders).replace("%player%", offline.getName()).replace("%playtime%", TimeFormat.getTime(PlayerdataManager.getPlayerStatistic(target, "PLAYTIME", Statistic.PLAY_ONE_TICK) / 20L)));
	}
}
