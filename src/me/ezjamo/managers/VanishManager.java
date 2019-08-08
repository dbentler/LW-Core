package me.ezjamo.managers;

import java.io.File;

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
import org.bukkit.event.player.PlayerQuitEvent;

import me.ezjamo.commands.VanishCommand;

public class VanishManager implements Listener {
	
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event) {
		VanishCommand.vanish.add(event.getPlayer());
		for (Player player : VanishCommand.vanish) {
			File file = new File("plugins//LW-Essentials//Vanished//" + player.getName() + ".yml");
			if (file.exists()) {
				if (VanishCommand.vanish.contains(player)) {
					if (event.getPlayer().hasPermission("lw.vanish")) {
						ActionBarMgr.addActionBar(event.getPlayer());
					}
					event.getPlayer().hidePlayer(player);
					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						if (online.hasPermission("lw.vanish.see")) {
							event.getPlayer().showPlayer(player);
						}
						else {
							online.hidePlayer(player);
						}
					}
				}
			}
			if (!file.exists()) {
				ActionBarMgr.removeActionBar(event.getPlayer());
				if (!VanishCommand.vanish.contains(player)) {
					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						online.showPlayer(player);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		ActionBarMgr.addActionBar(event.getPlayer());
		VanishCommand.vanish.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getPlayer().getName() + ".yml");
		if (file.exists()) {
			event.setCancelled(true);
		}
		if (!file.exists()) {
			return;
		}
	}
	
	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getEntity().getName() + ".yml");
		if (file.exists()) {
			event.setCancelled(true);
		}
		if (!file.exists()) {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getEntity().getName() + ".yml");
		if (file.exists()) {
			event.setCancelled(true);
		}
		if (!file.exists()) {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDamageByBlock(EntityDamageByBlockEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getEntity().getName() + ".yml");
		if (file.exists()) {
			event.setCancelled(true);
		}
		if (!file.exists()) {
			return;
		}
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getTarget().getName() + ".yml");
		if (file.exists()) {
			event.setCancelled(true);
		}
		if (!file.exists()) {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		File file = new File("plugins//LW-Essentials//Vanished//" + event.getPlayer().getName() + ".yml");
		if (file.exists()) {
			if (event.getAction().equals(Action.PHYSICAL)) {
				if (event.getClickedBlock().getType() == Material.STONE_PLATE || event.getClickedBlock().getType() == Material.WOOD_PLATE) {
					event.setCancelled(true);
				}
			}
			if (!file.exists()) {
				return;
			}
		}
	}
}