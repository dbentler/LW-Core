package me.ezjamo.utils;

import com.google.common.collect.Lists;
import me.ezjamo.Lonewolves;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Utils {

	public String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public void message(CommandSender sender, String string) {
		string = color(string);
		sender.sendMessage(string);
	}

	public String getMessage(String path) {
		String message = Lonewolves.plugin.getConfig().getString("Messages." + path);
		if (message == null) {
			message = Lonewolves.plugin.getConfig().getString("Messages." + path);
		}
		return message;
	}

	public void createItem(Inventory punish, Material material, int amount, int invSlot, String displayName, String... loreString) {
		ItemStack item;
		List<String> lore = Lists.newArrayList();

		item = new ItemStack(material, amount);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(displayName));
		for (String s : loreString) {
			lore.add(color(s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);

		punish.setItem(invSlot, item);
	}

	public void createItemByte(Inventory punish, Material material, int byteId, int amount, int invSlot, String displayName, String... loreString) {
		ItemStack item;
		List<String> lore = Lists.newArrayList();

		item = new ItemStack(material, amount, (short) byteId);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(displayName));
		for (String s : loreString) {
			lore.add(color(s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);

		punish.setItem(invSlot, item);
	}
}