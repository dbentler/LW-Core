package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ezjamo.commands.VanishCommand;

public class VanishManager implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		for (Player player : VanishCommand.vanish) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (VanishCommand.vanish.contains(player)) {
					event.getPlayer().hidePlayer(player);
					if (event.getPlayer().hasPermission("lw.vanish.see")) {
						event.getPlayer().showPlayer(player);
					}
					if (online.hasPermission("lw.vanish.see")) {
						online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &a" + player.getName() + " &ahas joined silently."));
					}
				}
				if (!VanishCommand.vanish.contains(player)) {
					online.showPlayer(player);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		for (Player player : VanishCommand.vanish) {
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (VanishCommand.vanish.contains(player)) {
					if (online.hasPermission("lw.vanish.see")) {
						online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &c" + player.getName() + " &chas left silently."));
					}
				}
				if (!VanishCommand.vanish.contains(player)) {
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		if (VanishCommand.vanish.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
		if (!VanishCommand.vanish.contains(event.getPlayer())) {
			return;
		}
	}
	
	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent event) {
		if (VanishCommand.vanish.contains(event.getEntity())) {
			event.setCancelled(true);
		}
		if (!VanishCommand.vanish.contains(event.getEntity())) {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (VanishCommand.vanish.contains(event.getEntity())) {
			event.setCancelled(true);
		}
		if (!VanishCommand.vanish.contains(event.getEntity())) {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDamageByBlock(EntityDamageByBlockEvent event) {
		if (VanishCommand.vanish.contains(event.getEntity())) {
			event.setCancelled(true);
		}
		if (!VanishCommand.vanish.contains(event.getEntity())) {
			return;
		}
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		if (VanishCommand.vanish.contains(event.getTarget())) {
			event.setCancelled(true);
		}
		if (!VanishCommand.vanish.contains(event.getTarget())) {
			return;
		}
	}
}
