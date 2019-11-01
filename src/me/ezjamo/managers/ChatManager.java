package me.ezjamo.managers;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import me.ezjamo.utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
		User user = ess.getUser(player);
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("chat-format.enabled");
		for (String blockedWords : BlockedWordsManager.getManager().getConfig().getStringList("Blocked Words")) {
			if (event.getMessage().toLowerCase().contains(blockedWords)) {
				event.setCancelled(true);
				message(player, Messages.prefix + Messages.playerSaidBlockedWord);
				return;
			}
		}
		if (enabled) {
			event.setCancelled(true);
			String channelString = Lonewolves.plugin.getConfig().getString("chat-format.channel");
			String setChannel = PlaceholderAPI.setPlaceholders(player, channelString);

			String prefixString = Lonewolves.plugin.getConfig().getString("chat-format.prefix");
			String setPrefix = PlaceholderAPI.setPlaceholders(player, prefixString);

			String nameString = Lonewolves.plugin.getConfig().getString("chat-format.name");
			String setName = PlaceholderAPI.setPlaceholders(player, nameString);

			String suffixString = Lonewolves.plugin.getConfig().getString("chat-format.suffix");
			String setSuffix = PlaceholderAPI.setPlaceholders(player, suffixString);

			TextComponent base = new TextComponent("");
			TextComponent channel = new TextComponent(setChannel);
			TextComponent prefix = new TextComponent(color(setPrefix));
			TextComponent name = new TextComponent(setName);
			if (user._getNickname() != null) {
				name = new TextComponent(color(setName.replace("%player_nickname%", "~" + user._getNickname())));
			}
			if (user._getNickname() == null) {
				name = new TextComponent(color(setName.replace("%player_nickname%", player.getName())));
			}
			TextComponent suffix = new TextComponent(color(setSuffix));
			TextComponent message = new TextComponent("");
			if (player.hasPermission("lw.color")) {
				message = new TextComponent(color(event.getMessage()));
			}
			if (!player.hasPermission("lw.color")) {
				message = new TextComponent(event.getMessage());
			}

			// Channel
			for (String channelHover : Lonewolves.plugin.getConfig().getStringList("chat-format.channel-tooltip")) {
				String placeholders = PlaceholderAPI.setPlaceholders(player, channelHover);
				BaseComponent[] text = TextComponent.fromLegacyText(color(placeholders.replace("|", "\n")));
				channel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
			}
			String channelClick = Lonewolves.plugin.getConfig().getString("chat-format.channel-click-command");
			String channelPlaceholders = PlaceholderAPI.setPlaceholders(player, channelClick);
			channel.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, channelPlaceholders));
			// Channel

			// Prefix
			for (String prefixHover : Lonewolves.plugin.getConfig().getStringList("chat-format.prefix-tooltip")) {
				String placeholders = PlaceholderAPI.setPlaceholders(player, prefixHover);
				BaseComponent[] text = TextComponent.fromLegacyText(color(placeholders.replace("|", "\n")));
				prefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
			}
			String prefixClick = Lonewolves.plugin.getConfig().getString("chat-format.prefix-click-command");
			String prefixPlaceholders = PlaceholderAPI.setPlaceholders(player, prefixClick);
			prefix.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, prefixPlaceholders));
			// Prefix

			// Name
			for (String nameHover : Lonewolves.plugin.getConfig().getStringList("chat-format.name-tooltip")) {
				String placeholders = PlaceholderAPI.setPlaceholders(player, nameHover);
				BaseComponent[] text = TextComponent.fromLegacyText(color(placeholders.replace("|", "\n")));
				name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
			}
			String nameClick = Lonewolves.plugin.getConfig().getString("chat-format.name-click-command");
			String namePlaceholders = PlaceholderAPI.setPlaceholders(player, nameClick);
			name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, namePlaceholders));
			// Name

			// Suffix
			for (String suffixHover : Lonewolves.plugin.getConfig().getStringList("chat-format.suffix-tooltip")) {
				String placeholders = PlaceholderAPI.setPlaceholders(player, suffixHover);
				BaseComponent[] text = TextComponent.fromLegacyText(color(placeholders.replace("|", "\n")));
				suffix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
			}
			String suffixClick = Lonewolves.plugin.getConfig().getString("chat-format.suffix-click-command");
			String suffixPlaceholders = PlaceholderAPI.setPlaceholders(player, suffixClick);
			suffix.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suffixPlaceholders));
			// Suffix

			base.addExtra(prefix);
			base.addExtra(name);
			base.addExtra(suffix);
			base.addExtra(message);
			for (Player listeningPlayer : event.getRecipients()) {
				FPlayer you = FPlayers.getInstance().getByPlayer(listeningPlayer);
				channel = new TextComponent(color(setChannel.replace("[FACTION]", fPlayer.getChatTag(you))));
				if (!fPlayer.hasFaction()) {
					channel = new TextComponent(color(setChannel.replaceFirst("\\[", "").replace("FACTION", "")
							.replaceFirst("]", "").replaceFirst("\\[", "").replaceFirst("] ", "").replaceFirst("]", "")));
				}
				for (String channelHover : Lonewolves.plugin.getConfig().getStringList("chat-format.channel-tooltip")) {
					String placeholders = PlaceholderAPI.setPlaceholders(player, channelHover);
					BaseComponent[] text = TextComponent.fromLegacyText(color(placeholders.replace("|", "\n")));
					channel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text));
				}
				channel.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, channelPlaceholders));
				listeningPlayer.spigot().sendMessage(channel, base);
			}
			Bukkit.getConsoleSender().sendMessage(channel.toLegacyText() + base.toLegacyText());
		}
	}
}
