package me.ezjamo.managers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class DispenerArmorListener implements Listener{
	
	
    @EventHandler
    public void onDispenseItem(BlockDispenseEvent e) {
    	if (e.getItem().getType() == Material.DIAMOND_BOOTS) {
    		e.setCancelled(true);
    			}
          }
      }
