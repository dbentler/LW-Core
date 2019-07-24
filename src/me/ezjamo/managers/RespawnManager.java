package me.ezjamo.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnManager implements Listener {
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 80.0, 0.5));	
					}	
			}


