package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Utils;
import net.md_5.bungee.api.ChatColor;

public class LWManagers implements Listener {

	   
	 // beacon shit needs to be added  
    @EventHandler
    public void onDrink(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
             ItemStack it = player.getItemInHand();
            if ((it.getType() == Material.POTION && it.getDurability() == 8233) || it.getDurability() == 16425) {
                 Potion potion = Potion.fromItemStack(it);
                 PotionEffectType effecttype = potion.getType().getEffectType();
                if (effecttype == PotionEffectType.INCREASE_DAMAGE) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fStrength II is currently disabled."));
                }
            }
        }
    }
	   
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether")) {
			if (p.getLocation().getBlockY() >= 128) {
				e.setCancelled(true);
				p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 86, 0.5));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fYou cannot place blocks above the nether."));
			}
			else
				return;
		}
	}
	
	@EventHandler
	public void onSpawnerPlace(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		ItemStack i = e.getPlayer().getItemInHand();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether")) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && i.getType() == Material.MOB_SPAWNER && i.getItemMeta().hasDisplayName()) {
				if (i.getItemMeta().getDisplayName().contains("Skeleton")) {
					e.setCancelled(true);
					p.sendMessage(Utils.chat("&8[&f&lLone&4&lWolves&8] &fYou cannot spawn skeletons in the nether"));
				}
			}
		}
	}
	
	@EventHandler
	public void SpawnerToggle(BlockPlaceEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether") || p.getWorld().getName().equalsIgnoreCase("world")) {
			boolean enabled = Lonewolves.plugin.getConfig().getBoolean("enable-spawner-placement");
			if (enabled) {
				return;
			}
			if (!enabled) {
			if (e.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fSpawn placement is now disabled."));
			}
			}
			else
				return;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			String unsetPlayer = "%player_name%";
            String setPlayer = PlaceholderAPI.setPlaceholders(player, unsetPlayer);
			event.setJoinMessage(Utils.chat("&fWelcome &4") + setPlayer + " " + Utils.chat("&fto &lLone&4&lWolves&f!"));
			player.performCommand("ekit member");
			Location loc = new Location(player.getWorld(), 0.500, 80, 0.500, 180, (float) 4.5);
			player.teleport(loc);
		}
	}
	
	@EventHandler
	public void setRespawnLocation(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Location loc = new Location(player.getWorld(), 0.500, 80, 0.500, 180, (float) 4.5);
		event.setRespawnLocation(loc);
	}
	
	@EventHandler
	public void setDeathMessage(PlayerDeathEvent event) {
		event.setDeathMessage(null);
	}
}