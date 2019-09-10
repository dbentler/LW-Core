package me.ezjamo.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import me.ezjamo.Lonewolves;

public class WeatherManager implements Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		boolean enabled = Lonewolves.plugin.getConfig().getBoolean("disable-weather");
		if (enabled) {
			event.getWorld().setWeatherDuration(0);
			event.setCancelled(true);
		}
		if (!enabled) {
			return;
		}
	}
}
