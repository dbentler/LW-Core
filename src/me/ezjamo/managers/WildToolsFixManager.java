package me.ezjamo.managers;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class WildToolsFixManager implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&f&lLone&4&lWolves &f&lBuild Wand")))	{	
			return;
		}	
		FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
		if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&f&lLone&4&lWolves &f&lBuild Wand"))) {
			if (!fPlayer.isInOwnTerritory()) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou can only use build wands in your own claim."));
						}	
				}
		}
}
