package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
import me.ezjamo.commands.KitsCommand;

public class PreviewManager implements Listener {
	
	public PreviewManager(Lonewolves plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
    @EventHandler
	public void onClicked(InventoryClickEvent e) {
    	Player player = (Player) e.getWhoClicked();
		String title = e.getInventory().getTitle();
		
		if (title.equals(Utils.chat("&eVIP Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
		
		if (title.equals(Utils.chat("&bVIP+ Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
		
		if (title.equals(Utils.chat("&cMVP Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
		
		if (title.equals(Utils.chat("&3Noble Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
		
		if (title.equals(Utils.chat("&5Mystic Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
		
		if (title.equals(Utils.chat("&2Kingpin Preview"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if ( e.getSlot() == 49 )
				KitsCommand.kits(player);
		}
    }
}