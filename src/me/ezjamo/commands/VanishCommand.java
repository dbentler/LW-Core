package me.ezjamo.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.ezjamo.Lonewolves;
import me.ezjamo.managers.ActionBarMgr;
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
		File file = new File("plugins//LW-Essentials//Vanished//" + player.getName() + ".yml");
		if (cmd.getName().equalsIgnoreCase("vanish")) {
			if (player.hasPermission("lw.vanish")) {
				if (args.length == 0) {
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							player.sendMessage("The specified path could not be found.");
						}
						YamlConfiguration saveVanished = YamlConfiguration.loadConfiguration(file);
						try {
							saveVanished.save(file);
						} catch (IOException e) {
							player.sendMessage("The specified path could not be found.");
						}
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (!online.hasPermission("lw.vanish.see")) {
								online.hidePlayer(player);
							}
							if (online.hasPermission("lw.vanish.see")) {
								online.showPlayer(player);
							}
						}
						ActionBarMgr.addActionBar(player);
						player.spigot().setCollidesWithEntities(false);
						TABAPI.setTagPrefixPermanently(player, "&7&o");
						TABAPI.setCustomTabNamePermanently(player, "&7&o" + player.getName());
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &aYou are now vanished!"));
						return true;
					}
					else {
						if (file.exists()) {
							for (Player online : Bukkit.getServer().getOnlinePlayers()) {
								online.showPlayer(player);
							}
							ActionBarMgr.removeActionBar(player);
							player.spigot().setCollidesWithEntities(true);
							TABAPI.setTagPrefixPermanently(player, "%rel_factionsuuid_relation_color%");
							TABAPI.setCustomTabNamePermanently(player, "%rel_factionsuuid_relation_color%" + player.getName());
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &cYou are no longer vanished!"));
							file.delete();
							return true;
						}
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
					File fileTarget = new File("plugins//LW-Essentials//Vanished//" + target.getName() + ".yml");
					if (!fileTarget.exists()) {
						try {
							fileTarget.createNewFile();
						} catch (IOException e) {
							player.sendMessage("The specified path could not be found.");
						}
						YamlConfiguration saveVanished = YamlConfiguration.loadConfiguration(fileTarget);
						try {
							saveVanished.save(fileTarget);
						} catch (IOException e) {
							player.sendMessage("The specified path could not be found.");
						}
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (!online.hasPermission("lw.vanish.see")) {
								online.hidePlayer(target);
							}
							if (online.hasPermission("lw.vanish.see")) {
								online.showPlayer(target);
							}
						}
						ActionBarMgr.addActionBar(target);
						target.spigot().setCollidesWithEntities(false);
						TABAPI.setTagPrefixPermanently(target, "&7&o");
						TABAPI.setCustomTabNamePermanently(target, "&7&o" + target.getName());
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + player.getName() + " &chas vanished you!"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + target.getName() + " &cis now vanished!"));
						return true;
					}
					else {
						if (fileTarget.exists()) {
							for (Player online : Bukkit.getServer().getOnlinePlayers()) {
								online.showPlayer(target);
							}
							ActionBarMgr.removeActionBar(target);
							target.spigot().setCollidesWithEntities(true);
							TABAPI.setTagPrefixPermanently(target, "%rel_factionsuuid_relation_color%");
							TABAPI.setCustomTabNamePermanently(target, "%rel_factionsuuid_relation_color%" + target.getName());
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + player.getName() + " &ahas unvanished you!"));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + target.getName() + " &ais no longer vanished!"));
							fileTarget.delete();
							return true;
						}
					}
					}
					if (target == null) {
						if (!args[0].equalsIgnoreCase("status")) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &f" + args[0] + " &cis not online."));
						}
						if (args[0].equalsIgnoreCase("status")) {
							if (file.exists()) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &cYou are currently vanished."));
							}
							if (!file.exists()) {
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
	public void checkOrder() {
		File file = new File("plugins//LW-Essentials//Vanished");
		if(!file.exists()) {
			file.mkdir();
		}
	}
}
