package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.ezjamo.Messages;
import me.ezjamo.commands.FreezeCommand;
import net.md_5.bungee.api.ChatColor;

public class FreezeManager implements Listener {
	
	
 @EventHandler
 public void onInventoryClick(InventoryClickEvent e) {
	 if (e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "You have been frozen.")) {
		 e.setCancelled(true);
	 }
	 else 
		 return;
 }
 
 @EventHandler
 public void onFreezeQuit(PlayerQuitEvent e) {
	 if (FreezeCommand.frozenPlayers.contains(e.getPlayer().getUniqueId())) {
     	for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
    		if (staff.hasPermission("rank.helper")) {
    			staff.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&f" + e.getPlayer().getDisplayName() + " has &clogged out &fwhile frozen!"));
    			FreezeCommand.frozenPlayers.remove(e.getPlayer().getUniqueId());
    		}
     	}
	 }
 }
 @EventHandler
 public void onPlayerCommandpreProcess(PlayerCommandPreprocessEvent e) {
	 if (FreezeCommand.frozenPlayers.contains(e.getPlayer().getUniqueId())) {
		 e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fyou cannot execute commands while frozen."));
		 e.setCancelled(true);
	 }
	 else
		 return;
 }
 
 @EventHandler
 public void onEntityDamageByEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) {
     if (entityDamageByEntityEvent.getEntity() instanceof Player) {
         if (entityDamageByEntityEvent.getDamager() instanceof Player) {
             Player player = (Player)entityDamageByEntityEvent.getEntity();
             Player player2 = (Player)entityDamageByEntityEvent.getDamager();
             if (FreezeCommand.frozenPlayers.contains(player2.getUniqueId())) {
                 entityDamageByEntityEvent.setCancelled(true);
                 player2.sendMessage(ChatColor.RED + "This player is frozen");
             }
             else if (FreezeCommand.frozenPlayers.contains(player.getUniqueId())) {
                 entityDamageByEntityEvent.setCancelled(true);
                 player2.sendMessage(ChatColor.RED + "This player is frozen");
             }
         }
         else if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
             final Projectile projectile = (Projectile)entityDamageByEntityEvent.getDamager();
             if (projectile.getShooter() instanceof Player) {
                 Player player3 = (Player) projectile.getShooter();
                 if (player3 != entityDamageByEntityEvent.getEntity()) {
                      Player player4 = (Player)entityDamageByEntityEvent.getEntity();
                     if (FreezeCommand.frozenPlayers.contains(player3.getUniqueId())) {
                         entityDamageByEntityEvent.setCancelled(true);
                         player3.sendMessage(String.valueOf(ChatColor.RED + "This player is frozen"));
                     }
                     else if (FreezeCommand.frozenPlayers.contains(player4.getUniqueId())) {
                         entityDamageByEntityEvent.setCancelled(true);
                         player3.sendMessage(ChatColor.RED + "This player is frozen");
                     }
                 }
             }
         }
     }
 }
}
