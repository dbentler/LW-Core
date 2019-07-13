package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

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
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fStrength II is currently disabled."));
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
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou cannot place blocks above the nether."));
			}
			else
				return;
		}
	}
	
	@EventHandler
	public void onSpawnerPlace(BlockPlaceEvent e) {
		Player p = (Player) e.getPlayer();
		if (p.getWorld().getName().equalsIgnoreCase("world_nether")) {
			if (e.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou cannot place spawners in the nether."));
			}
			else
				return;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			Location loc = new Location(player.getWorld(), 0.500, 80, 0.500, 179, (float) 4.5);
			player.teleport(loc);
		}
	}
	
	@EventHandler
	public void setRespawnLocation(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Location loc = new Location(player.getWorld(), 0.500, 80, 0.500, 179, (float) 4.5);
		event.setRespawnLocation(loc);
	}
}