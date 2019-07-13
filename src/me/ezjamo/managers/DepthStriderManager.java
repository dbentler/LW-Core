package me.ezjamo.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.ezjamo.armorequipevent.ArmorEquipEvent;

public class DepthStriderManager implements Listener {
	
	@EventHandler
	public void armorEquipEvent(ArmorEquipEvent e) {
		if (e.getNewArmorPiece() != null 
				&& e.getNewArmorPiece().getType() != Material.AIR 
				&& e.getNewArmorPiece().containsEnchantment(Enchantment.DEPTH_STRIDER)) {
			e.getNewArmorPiece().removeEnchantment(Enchantment.DEPTH_STRIDER);
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fDepth Strider has been removed from your boots because its currently &cdisabled."));
			}
		}
	}
