package me.ezjamo.managers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;

public class ChatManager implements Listener
{
    public static Set<UUID> staffChat;
    public static Set<UUID> adminChat;
    
    static {
        ChatManager.staffChat = new HashSet<UUID>();
    }
    
    public static Set<UUID> getStaffChat() {
        return ChatManager.staffChat;
    }
    
    @EventHandler
    public void onStaffChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (ChatManager.staffChat.contains(player.getUniqueId())) {
            event.setCancelled(true);
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (staff.hasPermission("lw.staffchat")) {
                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[StaffChat] &e" + player.getName() +  ": &c" + event.getMessage()));
                }
            }
        }
    }
    

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		
	}
	
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
    	Player p = (Player) event.getPlayer();
    	if (event.getMessage().equalsIgnoreCase("nigger")
    			|| event.getMessage().equalsIgnoreCase("n1gger") 
    			|| event.getMessage().equalsIgnoreCase("n1gg3r") 
    			|| event.getMessage().equalsIgnoreCase("nigg")
    			|| event.getMessage().equalsIgnoreCase("n1g")
    			|| event.getMessage().equalsIgnoreCase("ni99er")
    			|| event.getMessage().equalsIgnoreCase("n1igg3r")
    			|| event.getMessage().equalsIgnoreCase("nigler")
    			|| event.getMessage().equalsIgnoreCase("N!I!G!G!3!R")
    			|| event.getMessage().equalsIgnoreCase("n!gg3r")
    			|| event.getMessage().equalsIgnoreCase("rape")
    			|| event.getMessage().equalsIgnoreCase("n!gg3r")
    			|| event.getMessage().equalsIgnoreCase("n!gger")) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lLone&4&lWolves&8&l] &fYou have said a blacklisted word."));
    	}
    	else if
    		(event.getMessage().equalsIgnoreCase("nazi") || event.getMessage().contains("daddy")) {
    		event.setCancelled(true);
    		p.chat("father figure");
    	}	
    }
}