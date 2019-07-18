package me.ezjamo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class PreviewCommand implements CommandExecutor {
	
	public static void vip(Player player) {
		Inventory vip = Bukkit.createInventory(null, 54, Utils.chat("&eVIP Preview"));
		Utils.createItem(vip, 310, 1, 0, "", "");
		Utils.createItem(vip, 311, 1, 1, "", "");
		Utils.createItem(vip, 312, 1, 2, "", "");
		Utils.createItem(vip, 313, 1, 3, "", "");
		Utils.Enchant2(vip, 267, Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 1, 1, 4, "", "");
		Utils.createItem(vip, 261, 1, 5, "", "");
		Utils.createItem(vip, 262, 64, 6, "", "");
		Utils.createItem(vip, 384, 64, 7, "", "");
		Utils.createItem(vip, 384, 64, 8, "", "");
		Utils.Enchant1(vip, 257, Enchantment.DIG_SPEED, 1, 1, 9, "", "");
		Utils.Enchant1(vip, 258, Enchantment.DIG_SPEED, 1, 1, 10, "", "");
		Utils.Enchant1(vip, 256, Enchantment.DIG_SPEED, 1, 1, 11, "", "");
		Utils.createItem(vip, 264, 14, 12, "", "");
		Utils.createItem(vip, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(vip);
	}
	
	public static void vipplus(Player player) {
		Inventory vipplus = Bukkit.createInventory(null, 54, Utils.chat("&bVIP+ Preview"));
		Utils.Enchant1(vipplus, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 0, "", "");
		Utils.Enchant1(vipplus, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 1, "", "");
		Utils.Enchant1(vipplus, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 2, "", "");
		Utils.Enchant1(vipplus, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 1, 1, 3, "", "");
		Utils.Enchant2(vipplus, 276, Enchantment.DAMAGE_ALL, 1, Enchantment.DURABILITY, 1, 1, 4, "", "");
		Utils.Enchant1(vipplus, 261, Enchantment.ARROW_DAMAGE, 1, 1, 5, "", "");
		Utils.createItem(vipplus, 262, 64, 6, "", "");
		Utils.createItem(vipplus, 384, 64, 7, "", "");
		Utils.createItem(vipplus, 384, 64, 8, "", "");
		Utils.createItem(vipplus, 384, 32, 9, "", "");
		Utils.Enchant1(vipplus, 278, Enchantment.DIG_SPEED, 1, 1, 10, "", "");
		Utils.Enchant1(vipplus, 279, Enchantment.DIG_SPEED, 1, 1, 11, "", "");
		Utils.Enchant1(vipplus, 277, Enchantment.DIG_SPEED, 1, 1, 12, "", "");
		Utils.createItem(vipplus, 49, 16, 13, "", "");
		Utils.createItem(vipplus, 264, 24, 14, "", "");
		Utils.createItem(vipplus, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(vipplus);
	}
	
	public static void mvp(Player player) {
		Inventory mvp = Bukkit.createInventory(null, 54, Utils.chat("&cMVP Preview"));
		Utils.Enchant1(mvp, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 0, "", "");
		Utils.Enchant1(mvp, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 1, "", "");
		Utils.Enchant1(mvp, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 2, "", "");
		Utils.Enchant1(mvp, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1, 3, "", "");
		Utils.Enchant2(mvp, 276, Enchantment.DAMAGE_ALL, 2, Enchantment.DURABILITY, 1, 1, 4, "", "");
		Utils.Enchant2(mvp, 278, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 5, "", "");
		Utils.Enchant2(mvp, 277, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 6, "", "");
		Utils.Enchant2(mvp, 279, Enchantment.DIG_SPEED, 2, Enchantment.DURABILITY, 1, 1, 7, "", "");
		Utils.Enchant3(mvp, 261, Enchantment.ARROW_DAMAGE, 2, Enchantment.ARROW_KNOCKBACK, 1, Enchantment.DURABILITY, 1, 1, 8, "", "");
		Utils.createItem(mvp, 262, 64, 9, "", "");
		Utils.createItem(mvp, 264, 34, 10, "", "");
		Utils.createItem(mvp, 322, 5, 11, "", "");
		Utils.createItem(mvp, 384, 64, 12, "", "");
		Utils.createItem(mvp, 384, 64, 13, "", "");
		Utils.createItem(mvp, 384, 64, 14, "", "");
		Utils.createItem(mvp, 49, 32, 15, "", "");
		Utils.createItem(mvp, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(mvp);
	}

	public static void noble(Player player) {
		Inventory noble = Bukkit.createInventory(null, 54, Utils.chat("&3Noble Preview"));
		Utils.Enchant2(noble, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 0, "", "");
		Utils.Enchant2(noble, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 1, "", "");
		Utils.Enchant2(noble, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 2, "", "");
		Utils.Enchant2(noble, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 1, 1, 3, "", "");
		Utils.Enchant3(noble, 276, Enchantment.DAMAGE_ALL, 3, Enchantment.DURABILITY, 2, Enchantment.FIRE_ASPECT, 1, 1, 4, "", "");
		Utils.Enchant4(noble, 261, Enchantment.ARROW_DAMAGE, 2, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 2, 1, 5, "", "");
		Utils.createItem(noble, 262, 64, 6, "", "");
		Utils.Enchant2(noble, 278, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 7, "", "");
		Utils.Enchant2(noble, 279, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 8, "", "");
		Utils.Enchant2(noble, 277, Enchantment.DIG_SPEED, 3, Enchantment.DURABILITY, 2, 1, 9, "", "");
		Utils.createItem(noble, 384, 64, 10, "", "");
		Utils.createItem(noble, 384, 64, 11, "", "");
		Utils.createItem(noble, 384, 64, 12, "", "");
		Utils.createItem(noble, 384, 32, 13, "", "");
		Utils.createItem(noble, 49, 64, 14, "", "");
		Utils.createItem(noble, 264, 44, 15, "", "");
		Utils.createItem(noble, 322, 10, 16, "", "");
		Utils.createItem(noble, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(noble);
	}
	
	public static void mystic(Player player) {
		Inventory mystic = Bukkit.createInventory(null, 54, Utils.chat("&5Mystic Preview"));
		Utils.Enchant1(mystic, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 0, "", "");
		Utils.Enchant1(mystic, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 1, "", "");
		Utils.Enchant1(mystic, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 2, "", "");
		Utils.Enchant1(mystic, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 4, 1, 3, "", "");
		Utils.Enchant3(mystic, 276, Enchantment.DAMAGE_ALL, 4, Enchantment.DURABILITY, 3, Enchantment.FIRE_ASPECT, 1, 1, 4, "", "");
		Utils.Enchant2(mystic, 278, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 5, "", "");
		Utils.Enchant2(mystic, 279, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 6, "", "");
		Utils.Enchant2(mystic, 277, Enchantment.DIG_SPEED, 4, Enchantment.DURABILITY, 3, 1, 7, "", "");
		Utils.Enchant5(mystic, 261, Enchantment.ARROW_DAMAGE, 3, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 3, Enchantment.ARROW_INFINITE, 1, 1, 8, "", "");
		Utils.createItem(mystic, 262, 64, 9, "", "");
		Utils.createItem(mystic, 384, 64, 10, "", "");
		Utils.createItem(mystic, 384, 64, 11, "", "");
		Utils.createItem(mystic, 384, 64, 12, "", "");
		Utils.createItem(mystic, 384, 64, 13, "", "");
		Utils.createItem(mystic, 49, 64, 14, "", "");
		Utils.createItem(mystic, 49, 16, 15, "", "");
		Utils.createItem(mystic, 264, 54, 16, "", "");
		Utils.createItemByte(mystic, 322, 1, 1, 17, "", "");
		Utils.createItem(mystic, 322, 16, 18, "", "");
		Utils.createItem(mystic, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(mystic);
	}
	
	public static void kingpin(Player player) {
		Inventory kingpin = Bukkit.createInventory(null, 54, Utils.chat("&2Kingpin Preview"));
		Utils.Enchant2(kingpin, 310, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 0, "", "");
		Utils.Enchant2(kingpin, 311, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 1, "", "");
		Utils.Enchant2(kingpin, 312, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 2, "", "");
		Utils.Enchant2(kingpin, 313, Enchantment.PROTECTION_ENVIRONMENTAL, 4, Enchantment.DURABILITY, 3, 1, 3, "", "");
		Utils.Enchant3(kingpin, 276, Enchantment.DAMAGE_ALL, 5, Enchantment.DURABILITY, 3, Enchantment.FIRE_ASPECT, 2, 1, 4, "", "");
		Utils.Enchant5(kingpin, 261, Enchantment.ARROW_DAMAGE, 5, Enchantment.ARROW_KNOCKBACK, 2, Enchantment.ARROW_FIRE, 1, Enchantment.DURABILITY, 3, Enchantment.ARROW_INFINITE, 1, 1, 5, "", "");
		Utils.createItem(kingpin, 262, 64, 6, "", "");
		Utils.Enchant2(kingpin, 278, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 7, "", "");
		Utils.Enchant2(kingpin, 279, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 8, "", "");
		Utils.Enchant2(kingpin, 277, Enchantment.DIG_SPEED, 5, Enchantment.DURABILITY, 3, 1, 9, "", "");
		Utils.createItem(kingpin, 384, 64, 10, "", "");
		Utils.createItem(kingpin, 384, 64, 11, "", "");
		Utils.createItem(kingpin, 384, 64, 12, "", "");
		Utils.createItem(kingpin, 384, 64, 13, "", "");
		Utils.createItem(kingpin, 384, 32, 14, "", "");
		Utils.createItem(kingpin, 49, 64, 15, "", "");
		Utils.createItem(kingpin, 49, 32, 16, "", "");
		Utils.createItem(kingpin, 264, 64, 17, "", "");
		Utils.createItemByte(kingpin, 322, 1, 4, 18, "", "");
		Utils.createItem(kingpin, 322, 32, 19, "", "");
		Utils.createItem(kingpin, 399, 1, 49, "&dBack", "&fGo back to the kits menu.");
		player.openInventory(kingpin);
	}
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("preview")) {
        if (player.hasPermission("lw.preview"))
        	if (args.length == 1)
        		if (args[0].equalsIgnoreCase("vip"))
            PreviewCommand.vip(player);
        }
        if (label.equalsIgnoreCase("preview")) {
            if (player.hasPermission("lw.preview"))
            	if (args.length == 1)
            		if (args[0].equalsIgnoreCase("vip+"))
                PreviewCommand.vipplus(player);
            }
        if (label.equalsIgnoreCase("preview")) {
            if (player.hasPermission("lw.preview"))
            	if (args.length == 1)
            		if (args[0].equalsIgnoreCase("mvp"))
                PreviewCommand.mvp(player);
            }
        if (label.equalsIgnoreCase("preview")) {
            if (player.hasPermission("lw.preview"))
            	if (args.length == 1)
            		if (args[0].equalsIgnoreCase("noble"))
                PreviewCommand.noble(player);
            }
        if (label.equalsIgnoreCase("preview")) {
            if (player.hasPermission("lw.preview"))
            	if (args.length == 1)
            		if (args[0].equalsIgnoreCase("mystic"))
                PreviewCommand.mystic(player);
            }
        if (label.equalsIgnoreCase("preview")) {
            if (player.hasPermission("lw.preview"))
            	if (args.length == 1)
            		if (args[0].equalsIgnoreCase("kingpin"))
                PreviewCommand.kingpin(player);
            }
        	if (label.equalsIgnoreCase("preview")) {
        		if (args.length == 1)
        			if (!args[0].equalsIgnoreCase("vip"))
        			if (!args[0].equalsIgnoreCase("vip+"))
        			if (!args[0].equalsIgnoreCase("mvp"))
            		if (!args[0].equalsIgnoreCase("noble"))
            		if (!args[0].equalsIgnoreCase("mystic"))
            		if (!args[0].equalsIgnoreCase("kingpin"))
        			player.sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] &cInvalid kit."));
        	}
        	
        if (label.equalsIgnoreCase("preview")) {
        if(player.hasPermission("lw.preview"))
        	if (!(args.length == 1))
        		player.sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] /preview (kit)"));
        }
        	
        if(!player.hasPermission("lw.preview"))
            player.sendMessage(Lonewolves.NO_PERMS);
        return false;
    }
}