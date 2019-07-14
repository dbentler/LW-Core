package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class KothManager implements Listener {
	
	public KothManager(Lonewolves plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void onClicked(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String title = e.getInventory().getTitle();
		
		if (title.equals(Utils.chat("&fLone&4Wolves &fKoths"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			if ( e.getSlot() == 0 ) {
				p.performCommand("koth start Crow 5");
				p.closeInventory();
			}
				
			if ( e.getSlot() == 1) {
				p.performCommand("koth start Goliath 5");
				p.closeInventory();
			}
		}
}
}