package me.ezjamo.managers;

import me.ezjamo.Lonewolves;
import me.ezjamo.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CustomCmdsManager implements Listener {
	
	@EventHandler
	public void onCustomCommand(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().split(" ");
		ConfigurationSection customcmds = Lonewolves.plugin.getConfig().getConfigurationSection("Custom Commands");
		for (String command : Lonewolves.plugin.getConfig().getConfigurationSection("Custom Commands").getKeys(false)) {
			List<String> message = Lonewolves.plugin.getConfig().getConfigurationSection("Custom Commands").getStringList(command + ".message");
			
			if (args[0].equalsIgnoreCase("/" + command) || args[0].equalsIgnoreCase("/" + customcmds.getString(command + ".alias"))) {
				event.setCancelled(true);
				if (event.getPlayer().hasPermission("lw.customcmd." + command)) {
					for (String text : message) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}
				if (!event.getPlayer().hasPermission("lw.customcmd." + command)) {
					event.getPlayer().sendMessage(Messages.prefix + Messages.noPermission);
				}
			}
		}
	}
}
