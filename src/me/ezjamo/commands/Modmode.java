package me.ezjamo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.ezjamo.Lonewolves;
import me.ezjamo.managers.ModModeManager;

public class Modmode implements CommandExecutor
{
	
    public static ArrayList<String> modmode;
    public static HashMap<UUID, ItemStack[]> contents;
    public static HashMap<UUID, ItemStack[]> armorContents;
    
    static {
        Modmode.modmode = new ArrayList<String>();
        
    }
    
    public Modmode() {
        contents = new HashMap<UUID, ItemStack[]>();
        armorContents = new HashMap<UUID, ItemStack[]>();
    }
    
    public boolean previous(Player player) {
        return contents.containsKey(player.getUniqueId()) && armorContents.containsKey(player.getUniqueId());
    }
    
    public void saveInventory(Player player) {
        contents.put(player.getUniqueId(), player.getInventory().getContents());
        armorContents.put(player.getUniqueId(), player.getInventory().getArmorContents());
    }
    
    public static void loadInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.setContents((ItemStack[])contents.get(player.getUniqueId()));
        playerInventory.setArmorContents((ItemStack[])armorContents.get(player.getUniqueId()));
        contents.remove(player.getUniqueId());
        armorContents.remove(player.getUniqueId());
    }
    
    
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
    	Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("mod")) {
            if (!player.hasPermission("lw.mod")) {
                player.sendMessage(Lonewolves.NO_PERMS);
                return true;
            }
            if (!(player instanceof Player)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer only command."));
                return true;
            }
            if (arg3.length != 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: &7/mod"));
                return true;
            }
            if (Modmode.modmode.contains(player.getName())) {
                Modmode.modmode.remove(player.getName());
                ModModeManager.remove(Bukkit.getPlayer(player.getName()));
                loadInventory(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fStaff mode &cdisabled"));
                
                return true;
                }
            }
        	this.saveInventory(player);
            Modmode.modmode.add(player.getName());
            ModModeManager.put(Bukkit.getPlayer(player.getName()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&f&lLone&4&lWolves&8] &fStaff mode &aenabled"));
        return true;
    	}
	}