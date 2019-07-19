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
			if ( e.getRawSlot() == 11 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview vip");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit vip");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 13 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview vip+");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit vip+");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 15 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview mvp");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit mvp");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 29 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview noble");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit noble");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 31 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview mystic");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit mystic");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 33 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview kingpin");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit kingpin");
				p.closeInventory();
			}
		}//need to finish below this
			if ( e.getRawSlot() == 0 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview member");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit member");
				p.closeInventory();
			}
		}
			if ( e.getRawSlot() == 8 ) {
				if (e.getClick().isRightClick()) {
					p.performCommand("preview beta");
				}
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit beta");
				p.closeInventory();
			}
		}
			if (p.hasPermission("essentials.kits.tournament")) {
			if ( e.getRawSlot() == 36 ) {
			if (e.getClick().isLeftClick()) {
				p.performCommand("ekit tournament");
				p.closeInventory();
			}
		}
			}
			if (!p.hasPermission("essentials.kits.tournament")) {
				e.setCancelled(true);
			}
		}
}
}