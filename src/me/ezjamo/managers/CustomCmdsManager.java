package me.ezjamo.managers;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.ezjamo.Lonewolves;
import net.md_5.bungee.api.ChatColor;

public class CustomCmdsManager implements Listener {
	
	@EventHandler
	public void onCustomCommand(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().split(" ");
		
		for (String command : Lonewolves.plugin.getConfig().getConfigurationSection("Custom Commands").getKeys(false)) {
			List<String> message = (List<String>)Lonewolves.plugin.getConfig().getConfigurationSection("Custom Commands").getStringList(command);
			
			if (args[0].equalsIgnoreCase("/" + command)) {
				event.setCancelled(true);
				if (event.getPlayer().hasPermission("lw.customcmd." + command)) {
					for (String text : message) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', text));
					}
				}
				if (!event.getPlayer().hasPermission("lw.customcmd." + command)) {
					event.getPlayer().sendMessage(Lonewolves.NO_PERMS);
				}
			}
		}
	}
}
