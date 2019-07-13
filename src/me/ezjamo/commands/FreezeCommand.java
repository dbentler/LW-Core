package me.ezjamo.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import me.ezjamo.Lonewolves;

public class FreezeCommand implements CommandExecutor, Listener {
	
    public static Set<UUID> frozenPlayers;
    
    static {
        FreezeCommand.frozenPlayers = new HashSet<UUID>();
    }
    
	public void freezemenu(Player p) {

		 Inventory freezemenu = Bukkit.getServer().createInventory(null, 9, ChatColor.RED + "You have been frozen.");
		 ItemStack frozen = new ItemStack(Material.WOOL);
		 ItemMeta frozenmeta = frozen.getItemMeta();
		 frozenmeta.setDisplayName(ChatColor.RED + "You have been frozen.");
		 ArrayList<String> frozenlore = new ArrayList<String>();
		 frozenlore.add(ChatColor.RED + "");
		 frozenlore.add(ChatColor.RED + "DO NOT LOGOUT!");
		 frozenlore.add(ChatColor.RED + "You have been frozen, please join discord");
    	 frozenlore.add(ChatColor.RED + "lonewolves.net/discord");
		 frozenlore.add(ChatColor.RED + "");
		 frozenmeta.setLore(frozenlore);
		 frozen.setItemMeta(frozenmeta);
		 freezemenu.setItem(4, frozen);
		 
		 p.openInventory(freezemenu);
		
	}

	@EventHandler
    public void onInvClose(InventoryCloseEvent e) {
         Player p = (Player) e.getPlayer();
         BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
         if (frozenPlayers.contains(p.getUniqueId()))
            scheduler.scheduleSyncDelayedTask(Lonewolves.plugin, new Runnable() {
                @Override
                public void run() {
                	freezemenu(p);
                }
            }, 2L);
        }
    
	
	
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Console cant perform this command.");
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("lw.freeze")) {
            if (args.length < 1) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/freeze (player)"));
            }
            else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player is not online or doesn't exist !"));
                }
                else if (FreezeCommand.frozenPlayers.contains(target.getUniqueId())) {
                    FreezeCommand.frozenPlayers.remove(target.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + target.getName() + " has been unfrozen.");
                    target.closeInventory();
                }
                else {
                    FreezeCommand.frozenPlayers.add(target.getUniqueId());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',  "&cYou have frozen " + target.getName() + "."));
                    freezemenu(target);
                }
            } 
        }
        else {
            player.sendMessage(Lonewolves.NO_PERMS);
        }
        return true;
    }
}
