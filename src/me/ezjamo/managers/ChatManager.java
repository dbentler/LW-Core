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

import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
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
	public void onPlayerJoin(PlayerJoinEvent event) {
		String join = Lonewolves.plugin.getConfig().getString("join-message");
		String placeholders = PlaceholderAPI.setPlaceholders(event.getPlayer(), join);
		if (join.equalsIgnoreCase("none")) {
			event.setJoinMessage(null);
		}
		else {
			event.setJoinMessage(Utils.msg(placeholders));
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		String quit = Lonewolves.plugin.getConfig().getString("quit-message");
		String placeholders = PlaceholderAPI.setPlaceholders(event.getPlayer(), quit);
		if (quit.equalsIgnoreCase("none")) {
			event.setQuitMessage(null);
		}
		else {
			event.setQuitMessage(Utils.msg(placeholders));
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = (Player) event.getPlayer();
		for (String blockedwords : BlockedWordsManager.getManager().getConfig().getStringList("Blocked Words")) {
			if (event.getMessage().equalsIgnoreCase(blockedwords)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.prefix + "&fYou have said a blacklisted word."));
			}
		}
	}
}
