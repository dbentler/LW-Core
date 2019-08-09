package me.ezjamo.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class WildToolsFixManager implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
		ItemStack i = e.getPlayer().getItemInHand();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && i.getType() == Material.GOLD_AXE && i.getItemMeta().hasDisplayName()) {
			if (i.getItemMeta().getDisplayName().contains("LoneWolves Build Wand")) {
				if (!fPlayer.isInOwnTerritory()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou can only use build wands in your own claim."));
				}
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && i.getType() == Material.SHEARS && i.getItemMeta().hasDisplayName()) {
			if (i.getItemMeta().getDisplayName().contains("LoneWolves SellWand")) {
				if (!fPlayer.isInOwnTerritory()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou can only use sell wands in your own claim."));
				}
			}
		}
	}
}