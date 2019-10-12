package me.ezjamo.managers;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatManager extends Utils implements Listener {
	public static Set<UUID> staffChat;

	static {
		ChatManager.staffChat = new HashSet<>();
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
					staff.sendMessage(color("&9[StaffChat] &e" + player.getName() +  ": &c" + event.getMessage()));
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
			event.setJoinMessage(color(placeholders));
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
			event.setQuitMessage(color(placeholders));
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		Player player = event.getPlayer();
		FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
		for (String blockedwords : BlockedWordsManager.getManager().getConfig().getStringList("Blocked Words")) {
			if (event.getMessage().toLowerCase().contains(blockedwords)) {
				event.setCancelled(true);
				message(player, Messages.prefix + Messages.playerSaidBlockedWord);
				return;
			}
		}
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("chat-format.enabled");
		User user = ess.getUser(player);
		if (enabled) {
			String format = Lonewolves.plugin.getConfig().getString("chat-format.format");
			String setFormat = PlaceholderAPI.setPlaceholders(event.getPlayer(), format);
			if (!fPlayer.hasFaction()) {
				String faction = setFormat.replaceFirst("\\[", "").replaceFirst("]", "").replace("FACTION", "");
				if (!player.hasPermission("lw.color")) {
					if (event.getMessage().contains("&1")||
							event.getMessage().contains("&2")||
							event.getMessage().contains("&3")||
							event.getMessage().contains("&4")||
							event.getMessage().contains("&5")||
							event.getMessage().contains("&6")||
							event.getMessage().contains("&7")||
							event.getMessage().contains("&8")||
							event.getMessage().contains("&9")||
							event.getMessage().contains("&0")||
							event.getMessage().contains("&a")||
							event.getMessage().contains("&e")||
							event.getMessage().contains("&d")||
							event.getMessage().contains("&b")||
							event.getMessage().contains("&c")||
							event.getMessage().contains("&o")||
							event.getMessage().contains("&l")||
							event.getMessage().contains("&n")||
							event.getMessage().contains("&m")||
							event.getMessage().contains("&k")) {
						if (user._getNickname() != null) {
							event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
									.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage().replace("&1", "&f")
									.replace("&2", "&f")
									.replace("&3", "&f")
									.replace("&4", "&f")
									.replace("&5", "&f")
									.replace("&6", "&f")
									.replace("&7", "&f")
									.replace("&8", "&f")
									.replace("&9", "&f")
									.replace("&0", "&f")
									.replace("&a", "&f")
									.replace("&e", "&f")
									.replace("&d", "&f")
									.replace("&b", "&f")
									.replace("&c", "&f")
									.replace("&o", "&f")
									.replace("&l", "&f")
									.replace("&n", "&f")
									.replace("&m", "&f")
									.replace("&k", "&f")).replace("%", "%%"));
							return;
						}
						if (user._getNickname() == null) {
							event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
									.replace("%player_nickname%", player.getName()) + event.getMessage().replace("&1", "&f")
									.replace("&2", "&f")
									.replace("&3", "&f")
									.replace("&4", "&f")
									.replace("&5", "&f")
									.replace("&6", "&f")
									.replace("&7", "&f")
									.replace("&8", "&f")
									.replace("&9", "&f")
									.replace("&0", "&f")
									.replace("&a", "&f")
									.replace("&e", "&f")
									.replace("&d", "&f")
									.replace("&b", "&f")
									.replace("&c", "&f")
									.replace("&o", "&f")
									.replace("&l", "&f")
									.replace("&n", "&f")
									.replace("&m", "&f")
									.replace("&k", "&f")).replace("%", "%%"));
							return;
						}
					}
					else {
						if (user._getNickname() != null) {
							event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
									.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage()).replace("%", "%%"));
							return;
						}
						if (user._getNickname() == null) {
							event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
									.replace("%player_nickname%", player.getName()) + event.getMessage()).replace("%", "%%"));
							return;
						}
					}
				}
				if (user._getNickname() != null) {
					event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
							.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage()).replace("%", "%%"));
					return;
				}
				if (user._getNickname() == null) {
					event.setFormat(color(faction.replaceFirst("\\[", "").replaceFirst("] ", "")
							.replace("%player_nickname%", player.getName()) + event.getMessage()).replace("%", "%%"));
					return;
				}
			}
			if (fPlayer.hasFaction()) {
				if (!player.hasPermission("lw.color")) {
					if (event.getMessage().contains("&1")||
							event.getMessage().contains("&2")||
							event.getMessage().contains("&3")||
							event.getMessage().contains("&4")||
							event.getMessage().contains("&5")||
							event.getMessage().contains("&6")||
							event.getMessage().contains("&7")||
							event.getMessage().contains("&8")||
							event.getMessage().contains("&9")||
							event.getMessage().contains("&0")||
							event.getMessage().contains("&a")||
							event.getMessage().contains("&e")||
							event.getMessage().contains("&d")||
							event.getMessage().contains("&b")||
							event.getMessage().contains("&c")||
							event.getMessage().contains("&o")||
							event.getMessage().contains("&l")||
							event.getMessage().contains("&n")||
							event.getMessage().contains("&m")||
							event.getMessage().contains("&k")) {
						if (user._getNickname() != null) {
							event.setFormat(color(setFormat.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage().replace("&1", "&f")
									.replace("&2", "&f")
									.replace("&3", "&f")
									.replace("&4", "&f")
									.replace("&5", "&f")
									.replace("&6", "&f")
									.replace("&7", "&f")
									.replace("&8", "&f")
									.replace("&9", "&f")
									.replace("&0", "&f")
									.replace("&a", "&f")
									.replace("&e", "&f")
									.replace("&d", "&f")
									.replace("&b", "&f")
									.replace("&c", "&f")
									.replace("&o", "&f")
									.replace("&l", "&f")
									.replace("&n", "&f")
									.replace("&m", "&f")
									.replace("&k", "&f")).replace("%", "%%"));
							return;
						}
						if (user._getNickname() == null) {
							event.setFormat(color(setFormat.replace("%player_nickname%", player.getName()) + event.getMessage().replace("&1", "&f")
									.replace("&2", "&f")
									.replace("&3", "&f")
									.replace("&4", "&f")
									.replace("&5", "&f")
									.replace("&6", "&f")
									.replace("&7", "&f")
									.replace("&8", "&f")
									.replace("&9", "&f")
									.replace("&0", "&f")
									.replace("&a", "&f")
									.replace("&e", "&f")
									.replace("&d", "&f")
									.replace("&b", "&f")
									.replace("&c", "&f")
									.replace("&o", "&f")
									.replace("&l", "&f")
									.replace("&n", "&f")
									.replace("&m", "&f")
									.replace("&k", "&f")).replace("%", "%%"));
							return;
						}
					}
					else {
						if (user._getNickname() != null) {
							event.setFormat(color(setFormat.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage()).replace("%", "%%"));
							return;
						}
						if (user._getNickname() == null) {
							event.setFormat(color(setFormat.replace("%player_nickname%", player.getName()) + event.getMessage()).replace("%", "%%"));
							return;
						}
					}
				}
				if (user._getNickname() != null) {
					event.setFormat(color(setFormat.replace("%player_nickname%", "~" + user._getNickname()) + event.getMessage()).replace("%", "%%"));
					return;
				}
				if (user._getNickname() == null) {
					event.setFormat(color(setFormat.replace("%player_nickname%", player.getName()) + event.getMessage()).replace("%", "%%"));
				}
			}
		}
	}
}
