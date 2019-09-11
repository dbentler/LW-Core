package me.ezjamo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	
	public static String msg(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Inventory punish, int materialId, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItemByte(Inventory punish, int materialId, int byteId, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount, (short) byteId);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack Enchant1(Inventory punish, int materialId, Enchantment enchant, int level, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		meta.addEnchant(enchant, level, false);
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack Enchant2(Inventory punish, int materialId, Enchantment enchant, int level, Enchantment enchant1, int level1, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		meta.addEnchant(enchant, level, false);
		meta.addEnchant(enchant1, level1, false);
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack Enchant3(Inventory punish, int materialId, Enchantment enchant, int level, Enchantment enchant1, int level1, Enchantment enchant11, int level11, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		meta.addEnchant(enchant, level, false);
		meta.addEnchant(enchant1, level1, false);
		meta.addEnchant(enchant11, level11, false);
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack Enchant4(Inventory punish, int materialId, Enchantment enchant, int level, Enchantment enchant1, int level1, Enchantment enchant11, int level11, Enchantment enchant111, int level111, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		meta.addEnchant(enchant, level, false);
		meta.addEnchant(enchant1, level1, false);
		meta.addEnchant(enchant11, level11, false);
		meta.addEnchant(enchant111, level111, false);
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack Enchant5(Inventory punish, int materialId, Enchantment enchant, int level, Enchantment enchant1, int level1, Enchantment enchant11, int level11, Enchantment enchant111, int level111, Enchantment enchant1111, int level1111, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(materialId), amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName (Utils.msg(displayName));
		meta.addEnchant(enchant, level, false);
		meta.addEnchant(enchant1, level1, false);
		meta.addEnchant(enchant11, level11, false);
		meta.addEnchant(enchant111, level111, false);
		meta.addEnchant(enchant1111, level1111, false);
		for (String s : loreString) {
			lore.add(Utils.msg(s));
		}
		meta.setLore(lore);;
		item.setItemMeta(meta);
		
		punish.setItem(invSlot, item);
		return item;
	}
}