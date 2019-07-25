package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnManager implements Listener {
	
	@EventHandler
	public void setRespawnLocation(PlayerRespawnEvent event) {
		Location loc = new Location(Bukkit.getWorld("world"), 0.500, 80, 0.500, 180, (float) 4.5);
		event.setRespawnLocation(loc);
	}
			}


