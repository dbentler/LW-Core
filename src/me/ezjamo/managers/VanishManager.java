package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.ezjamo.commands.VanishCommand;

public class VanishManager implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		for (Player player : VanishCommand.vanish) {
			if (VanishCommand.vanish.contains(player)) {
				event.getPlayer().hidePlayer(player);
				if (event.getPlayer().hasPermission("lw.vanish.see")) {
					event.getPlayer().showPlayer(player);
				}
			}
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				if (!VanishCommand.vanish.contains(player)) {
					online.showPlayer(player);
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
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (VanishCommand.vanish.contains(event.getPlayer())) {
			if (event.getAction().equals(Action.PHYSICAL)) {
				if (event.getClickedBlock().getType() == Material.STONE_PLATE || event.getClickedBlock().getType() == Material.WOOD_PLATE) {
					event.setCancelled(true);
				}
			}
			if (!VanishCommand.vanish.contains(event.getPlayer())) {
				return;
			}
		}
	}
}