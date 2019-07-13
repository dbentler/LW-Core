package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;

public class KitsManager implements Listener {
	
	public KitsManager(Lonewolves plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClicked(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String title = e.getInventory().getTitle();
		
		if (title.equals(Utils.chat("&fLone&4Wolves &fKits"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			if ( e.getSlot() == 11 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview vip");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit vip");
				p.closeInventory();
			}
		}
			if ( e.getSlot() == 13 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview vip+");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit vip+");
				p.closeInventory();
			}
		}
			if ( e.getSlot() == 15 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview mvp");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit mvp");
				p.closeInventory();
			}
		}
			if ( e.getSlot() == 29 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview noble");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit noble");
				p.closeInventory();
			}
		}
			if ( e.getSlot() == 31 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview mystic");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit mystic");
				p.closeInventory();
			}
		}
			if ( e.getSlot() == 33 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview kingpin");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("essentials:kit kingpin");
				p.closeInventory();
			}
		}
		}
}
}