package me.ezjamo.managers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.armorequipevent.ArmorEquipEvent;

public class DepthStriderManager implements Listener {
	
	@EventHandler
	public void armorEquipEvent(ArmorEquipEvent e) {
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("disable-depth-strider");
		if (enabled) {
			if (e.getNewArmorPiece() != null 
					&& e.getNewArmorPiece().getType() != Material.AIR
					&& e.getNewArmorPiece().containsEnchantment(Enchantment.DEPTH_STRIDER)) {
				e.getNewArmorPiece().removeEnchantment(Enchantment.DEPTH_STRIDER);
				e.getPlayer().sendMessage(Messages.prefix + Messages.depthStriderDisabledMessage);
			}
			if (!enabled) {
				return;
			}
		}
	}
}
