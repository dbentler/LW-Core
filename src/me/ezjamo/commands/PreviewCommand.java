package me.ezjamo.commands;

import me.ezjamo.Messages;
import me.ezjamo.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PreviewCommand extends Utils implements CommandExecutor, TabCompleter {
	private static Utils utils = new Utils();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			message(sender, "&cThis command can only be used by players!");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("preview")) {
			if (player.hasPermission("lw.preview")) {
				if (args.length == 0) {
					message(player, Messages.prefix + "&cUsage: &7/preview <kit>");
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("vip")) {
						PreviewCommand.vip(player);
					}
					if (args[0].equalsIgnoreCase("vip+")) {
						PreviewCommand.vipplus(player);
					}
					if (args[0].equalsIgnoreCase("mvp")) {
						PreviewCommand.mvp(player);
					}
					if (args[0].equalsIgnoreCase("noble")) {
						PreviewCommand.noble(player);
					}
					if (args[0].equalsIgnoreCase("mystic")) {
						PreviewCommand.mystic(player);
					}
					if (args[0].equalsIgnoreCase("kingpin")) {
						PreviewCommand.kingpin(player);
					}
					if (!args[0].equalsIgnoreCase("vip"))
						if (!args[0].equalsIgnoreCase("vip+"))
							if (!args[0].equalsIgnoreCase("mvp"))
								if (!args[0].equalsIgnoreCase("noble"))
									if (!args[0].equalsIgnoreCase("mystic"))
										if (!args[0].equalsIgnoreCase("kingpin")) {
											message(player, Messages.prefix + "&cInvalid kit.");
										}
				}
			}
			if(!player.hasPermission("lw.preview")) {
				message(player, Messages.prefix + Messages.noPermission);
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> preview = new ArrayList<>();
		preview.add("vip");
		preview.add("vip+");
		preview.add("mvp");
		preview.add("noble");
		preview.add("mystic");
		preview.add("kingpin");
		List<String> empty = new ArrayList<>();
		if (args.length == 1) {
			List<String> completions = new ArrayList<>();
			StringUtil.copyPartialMatches(args[0], preview, completions);
			return completions;
		}
		return empty;
	}

	public static void vip(Player player) {
		Inventory vip = Bukkit.createInventory(null, 54, utils.color("&eVIP Preview"));
		utils.createItem(vip, 310, 1, 0, "", "");
		utils.createItem(vip, 311, 1, 1, "", "");
		utils.createItem(vip, 312, 1, 2, "", "");
		utils.createItem(vip, 313, 1, 3, "", "");
		utils.Enchant2(vip, 267, Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 1, 1, 4, "", "");
		utils.createItem(vip, 261, 1, 5, "", "");
		utils.createItem(vip, 262, 64, 6, "", "");
		utils.createItem(vip, 384, 64, 7, "", "");
		utils.createItem(vip, 384, 64, 8, "", "");
		utils.Enchant1(vip, 257, Enchantment.DIG_SPEED, 1, 1, 9, "", "");
		utils.Enchant1(vip, 258, Enchantment.DIG_SPEED, 1, 1, 10, "", "");
		utils.Enchant1(vip, 256, Enchantment.DIG_SPEED, 1, 1, 11, "", "");
		utils.createItem(vip, 264, 14, 12, "", "");
		utils.createItem(vip, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(vip);
	}

	public static void vipplus(Player player) {
		Inventory vipplus = Bukkit.createInventory(null, 54, utils.color("&bVIP+ Preview"));
		utils.Enchant1(vipplus, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 0, "", "");
		utils.Enchant1(vipplus, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 1, "", "");
		utils.Enchant1(vipplus, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 2, "", "");
		utils.Enchant1(vipplus, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 3, "", "");
		utils.Enchant2(vipplus, 276, Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 1, 1, 4, "", "");
		utils.Enchant1(vipplus, 261, Enchantment.ARROW_DAMAGE, 1, 1, 5, "", "");
		utils.createItem(vipplus, 262, 64, 6, "", "");
		utils.createItem(vipplus, 384, 64, 7, "", "");
		utils.createItem(vipplus, 384, 64, 8, "", "");
		utils.createItem(vipplus, 384, 32, 9, "", "");
		utils.Enchant1(vipplus, 278, Enchantment.DIG_SPEED, 1, 1, 10, "", "");
		utils.Enchant1(vipplus, 279, Enchantment.DIG_SPEED, 1, 1, 11, "", "");
		utils.Enchant1(vipplus, 277, Enchantment.DIG_SPEED, 1, 1, 12, "", "");
		utils.createItem(vipplus, 49, 16, 13, "", "");
		utils.createItem(vipplus, 264, 24, 14, "", "");
		utils.createItem(vipplus, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(vipplus);
	}

	public static void mvp(Player player) {
		Inventory mvp = Bukkit.createInventory(null, 54, utils.color("&cMVP Preview"));
		utils.Enchant1(mvp, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 0, "", "");
		utils.Enchant1(mvp, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 1, "", "");
		utils.Enchant1(mvp, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 2, "", "");
		utils.Enchant1(mvp, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 3, "", "");
		utils.Enchant2(mvp, 276, Enchantment.DAMAGE_ALL, 2, Enchantment.DURABILITY, 1, 1, 4, "", "");
		utils.Enchant2(mvp, 278, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 5, "", "");
		utils.Enchant2(mvp, 277, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 6, "", "");
		utils.Enchant2(mvp, 279, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 7, "", "");
		utils.Enchant3(mvp, 261, Enchantment.ARROW_DAMAGE, 2, Enchantment.ARROW_KNOCKBACK, 1, Enchantment.DURABILITY, 1, 1, 8, "", "");
		utils.createItem(mvp, 262, 64, 9, "", "");
		utils.createItem(mvp, 264, 34, 10, "", "");
		utils.createItem(mvp, 322, 5, 11, "", "");
		utils.createItem(mvp, 384, 64, 12, "", "");
		utils.createItem(mvp, 384, 64, 13, "", "");
		utils.createItem(mvp, 384, 64, 14, "", "");
		utils.createItem(mvp, 49, 32, 15, "", "");
		utils.createItem(mvp, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(mvp);
	}

	public static void noble(Player player) {
		Inventory noble = Bukkit.createInventory(null, 54, utils.color("&3Noble Preview"));
		utils.Enchant2(noble, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 0, "", "");
		utils.Enchant2(noble, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 1, "", "");
		utils.Enchant2(noble, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 2, "", "");
		utils.Enchant2(noble, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 3, "", "");
		utils.Enchant3(noble, 276, Enchantment.DAMAGE_ALL, 3, Enchantment.DURABILITY, 2, Enchantment.FIRE_ASPECT, 1, 1, 4, "", "");
		utils.Enchant4(noble, 261, Enchantment.ARROW_DAMAGE, 2, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 2, 1, 5, "", "");
		utils.createItem(noble, 262, 64, 6, "", "");
		utils.Enchant2(noble, 278, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 7, "", "");
		utils.Enchant2(noble, 279, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 8, "", "");
		utils.Enchant2(noble, 277, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 9, "", "");
		utils.createItem(noble, 384, 64, 10, "", "");
		utils.createItem(noble, 384, 64, 11, "", "");
		utils.createItem(noble, 384, 64, 12, "", "");
		utils.createItem(noble, 384, 32, 13, "", "");
		utils.createItem(noble, 49, 64, 14, "", "");
		utils.createItem(noble, 264, 44, 15, "", "");
		utils.createItem(noble, 322, 10, 16, "", "");
		utils.createItem(noble, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(noble);
	}

	public static void mystic(Player player) {
		Inventory mystic = Bukkit.createInventory(null, 54, utils.color("&5Mystic Preview"));
		utils.Enchant1(mystic, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 0, "", "");
		utils.Enchant1(mystic, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 1, "", "");
		utils.Enchant1(mystic, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 2, "", "");
		utils.Enchant1(mystic, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 3, "", "");
		utils.Enchant3(mystic, 276, Enchantment.DAMAGE_ALL, 4, Enchantment.DURABILITY, 3, Enchantment.FIRE_ASPECT, 1, 1, 4, "", "");
		utils.Enchant2(mystic, 278, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 5, "", "");
		utils.Enchant2(mystic, 279, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 6, "", "");
		utils.Enchant2(mystic, 277, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 7, "", "");
		utils.Enchant5(mystic, 261, Enchantment.ARROW_DAMAGE, 3, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 3, Enchantment.ARROW_INFINITE, 1, 1, 8, "", "");
		utils.createItem(mystic, 262, 64, 9, "", "");
		utils.createItem(mystic, 384, 64, 10, "", "");
		utils.createItem(mystic, 384, 64, 11, "", "");
		utils.createItem(mystic, 384, 64, 12, "", "");
		utils.createItem(mystic, 384, 64, 13, "", "");
		utils.createItem(mystic, 49, 64, 14, "", "");
		utils.createItem(mystic, 49, 16, 15, "", "");
		utils.createItem(mystic, 264, 54, 16, "", "");
		utils.createItemByte(mystic, 322, 1, 1, 17, "", "");
		utils.createItem(mystic, 322, 16, 18, "", "");
		utils.createItem(mystic, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(mystic);
	}

	public static void kingpin(Player player) {
		Inventory kingpin = Bukkit.createInventory(null, 54, utils.color("&2Kingpin Preview"));
		utils.Enchant2(kingpin, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 0, "", "");
		utils.Enchant2(kingpin, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 1, "", "");
		utils.Enchant2(kingpin, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 2, "", "");
		utils.Enchant2(kingpin, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 3, "", "");
		utils.Enchant3(kingpin, 276, Enchantment.DAMAGE_ALL, 5, Enchantment.DURABILITY, 3, Enchantment.FIRE_ASPECT, 2, 1, 4, "", "");
		utils.Enchant5(kingpin, 261, Enchantment.ARROW_DAMAGE, 5, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 3, Enchantment.ARROW_INFINITE, 1, 1, 5, "", "");
		utils.createItem(kingpin, 262, 64, 6, "", "");
		utils.Enchant2(kingpin, 278, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 7, "", "");
		utils.Enchant2(kingpin, 279, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 8, "", "");
		utils.Enchant2(kingpin, 277, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 9, "", "");
		utils.createItem(kingpin, 384, 64, 10, "", "");
		utils.createItem(kingpin, 384, 64, 11, "", "");
		utils.createItem(kingpin, 384, 64, 12, "", "");
		utils.createItem(kingpin, 384, 64, 13, "", "");
		utils.createItem(kingpin, 384, 32, 14, "", "");
		utils.createItem(kingpin, 49, 64, 15, "", "");
		utils.createItem(kingpin, 49, 32, 16, "", "");
		utils.createItem(kingpin, 264, 64, 17, "", "");
		utils.createItemByte(kingpin, 322, 1, 4, 18, "", "");
		utils.createItem(kingpin, 322, 32, 19, "", "");
		utils.createItem(kingpin, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(kingpin);
	}
}
