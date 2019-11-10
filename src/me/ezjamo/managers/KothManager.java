package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import subside.plugins.koth.KothPlugin;
import subside.plugins.koth.areas.Koth;

public class KothManager implements Listener {
	private Utils utils = new Utils();

	public KothManager(Lonewolves plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClicked(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String title = e.getInventory().getTitle();
		if (title.equals(utils.color(Messages.prefix.replace("[", "").replace("]", "") + "&fKoths"))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) return;
			if (e.getCurrentItem().getItemMeta() == null) return;
			if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()) == null) return;
			Plugin plugin = Lonewolves.plugin.getServer().getPluginManager().getPlugin("KoTH");
			KothPlugin kothPlugin = (KothPlugin) plugin;
			for (Koth koths : kothPlugin.getKothHandler().getAvailableKoths()) {
				String koth = koths.getName();
				String item = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
				if (item.equalsIgnoreCase(koth)) {
					p.performCommand("koth start " + koth + " 5");
					p.closeInventory();
				}
			}
		}
	}
}
