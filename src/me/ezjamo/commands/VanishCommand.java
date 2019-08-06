package me.ezjamo.commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import me.neznamy.tab.bukkit.api.TABAPI;

@SuppressWarnings("deprecation")
public class VanishCommand implements CommandExecutor {
	public static ArrayList<Player> vanish = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can vanish!");
		}
		if (cmd.getName().equalsIgnoreCase("vanish")) {
			if (player.hasPermission("lw.vanish")) {
				if (args.length == 0) {
					if (!vanish.contains(player)) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (!online.hasPermission("lw.vanish.see")) {
								online.hidePlayer(player);
							}
							if (online.hasPermission("lw.vanish.see")) {
								online.showPlayer(player);
							}
						}
						vanish.add(player);
						player.spigot().setCollidesWithEntities(false);
						TABAPI.setTagPrefixTemporarily(player, "&7&o");
						TABAPI.setCustomTabNameTemporarily(player, "&7&o" + player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aYou are now vanished!"));
						return true;
					}
					else {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							online.showPlayer(player);
						}
						vanish.remove(player);
						player.spigot().setCollidesWithEntities(true);
						TABAPI.setTagPrefixTemporarily(player, "%rel_factionsuuid_relation_color%");
						TABAPI.setCustomTabNameTemporarily(player, "%rel_factionsuuid_relation_color%" + player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &cYou are no longer vanished!"));
						return true;
					}
				}
			}
			if (!player.hasPermission("lw.vanish")) {
				player.sendMessage(Lonewolves.NO_PERMS);
				return true;
			}
			if (player.hasPermission("lw.vanish.others")) {
				if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (!vanish.contains(target)) {
							for (Player online : Bukkit.getServer().getOnlinePlayers()) {
								if (!online.hasPermission("lw.vanish.see")) {
									online.hidePlayer(target);
								}
								if (online.hasPermission("lw.vanish.see")) {
									online.showPlayer(target);
								}
							}
							vanish.add(target);
							target.spigot().setCollidesWithEntities(false);
							TABAPI.setTagPrefixTemporarily(target, "&7&o");
							TABAPI.setCustomTabNameTemporarily(target, "&7&o" + target.getName());
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aYou are now vanished!"));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &c" + target.getName() + "&c is now vanished!"));
							return true;
						}
						else {
							for (Player online : Bukkit.getServer().getOnlinePlayers()) {
								online.showPlayer(target);
							}
							vanish.remove(target);
							target.spigot().setCollidesWithEntities(true);
							TABAPI.setTagPrefixTemporarily(target, "%rel_factionsuuid_relation_color%");
							TABAPI.setCustomTabNameTemporarily(target, "%rel_factionsuuid_relation_color%" + player.getName());
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aYou are no longer vanished!"));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + target.getName() + " &ais no longer vanished!"));
							return true;
						}
					}
					if (target == null) {
						if (!args[0].equalsIgnoreCase("status")) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &f" + args[0] + " &cis not online."));
						}
						if (args[0].equalsIgnoreCase("status")) {
							if (vanish.contains(player)) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &cYou are currently vanished."));
							}
							if (!vanish.contains(player)) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aYou are currently not vanished."));
							}
						}
					}
				}
			}
			if (!player.hasPermission("lw.vanish.others")) {
				player.sendMessage(Lonewolves.NO_PERMS);
				return true;
			}
		}
		return true;
	}
}
